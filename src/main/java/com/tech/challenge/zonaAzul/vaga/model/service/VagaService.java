package com.tech.challenge.zonaAzul.vaga.model.service;


import com.tech.challenge.util.exception.condutor.NoSuchRecordException;
import com.tech.challenge.util.exception.vaga.VagaAlreadyExistsException;
import com.tech.challenge.util.exception.vaga.VagaNoSurchExistsException;
import com.tech.challenge.util.mappers.vaga.VagaMapper;
import com.tech.challenge.zonaAzul.vaga.dto.VagaRecord;
import com.tech.challenge.zonaAzul.vaga.form.VagaForm;
import com.tech.challenge.zonaAzul.vaga.model.entity.Vaga;
import com.tech.challenge.zonaAzul.vaga.model.repository.VagaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    private static final Logger log = LoggerFactory.getLogger(VagaService.class);
    @Autowired
    private VagaRepository repository;

    public VagaRecord cadastrar(VagaForm vagaForm) throws VagaAlreadyExistsException {
        Vaga novaVaga = VagaMapper.paraVaga(vagaForm);
        VagaRecord vaga = null;

        Boolean vagaExistente = repository.existsByLocal(novaVaga.getLocal());

        if (!vagaExistente){
            vaga = VagaMapper.paraVagaRecord(novaVaga);
            repository.save(novaVaga);
        }else {
            log.error("Vaga já existente!");
            throw new VagaAlreadyExistsException("Vaga já existente!");
        }
        return vaga;
    }

    public List<VagaRecord> vagas() throws VagaNoSurchExistsException {
        List<Vaga> vagaList = null;
        List<VagaRecord> vagaRecordList = null;

        vagaList = repository.findAll();
        vagaRecordList = VagaMapper.paraVaga(vagaList);

        if (vagaRecordList.isEmpty()){
            log.info("Não foram encontrado registros!");
            throw new VagaNoSurchExistsException("Não foram encontrado registros!");
        }

        return vagaRecordList;
    }

    public VagaRecord editarVaga(String id, VagaForm form) throws VagaNoSurchExistsException {
        Optional<Vaga> vagaExistente = repository.findById(id);
        VagaRecord vagaRecord;

        if (vagaExistente.isPresent()){
            vagaExistente.map(vaga -> {
                vaga.setLocal(form.getLocal());
                vaga.setQuantidadeVaga(form.getQuantidadeVaga());
                return vaga;
            });

            vagaRecord = VagaMapper.paraVagaRecord(vagaExistente.get());

        }else {
            log.info("Não foram encontrado registros!");
            throw new VagaNoSurchExistsException("Não foram encontrado registros!");
        }
        return vagaRecord;
    }

    public void deletar(String id) throws VagaNoSurchExistsException {
        Optional<Vaga> vagaExistente = repository.findById(id);

        if (vagaExistente.isPresent()){
            repository.deleteById(id);
        }else {
            log.info("Não foram encontrado registros!");
            throw new VagaNoSurchExistsException("Não foram encontrado registros!");
        }
    }

    public VagaRecord vagasPorLocal(String local) throws VagaNoSurchExistsException {
        Vaga vaga = repository.findByLocal(local);

        if (vaga == null){
            log.info("Não foram encontrado registros!");
            throw new VagaNoSurchExistsException("Não foram encontrado registros!");
        }

        VagaRecord vagaRecord = VagaMapper.paraVagaRecord(vaga);
        return vagaRecord;
    }
}
