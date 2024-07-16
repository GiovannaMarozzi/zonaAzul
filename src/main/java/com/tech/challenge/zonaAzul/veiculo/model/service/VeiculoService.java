package com.tech.challenge.zonaAzul.veiculo.model.service;

import com.tech.challenge.zonaAzul.condutor.model.CondutorRepository;
import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;
import com.tech.challenge.zonaAzul.util.exception.veiculo.VeiculoAlreadyExistsException;
import com.tech.challenge.zonaAzul.util.exception.veiculo.VeiculoNoDriverExistsException;
import com.tech.challenge.zonaAzul.util.mappers.veiculos.VeiculoMappers;
import com.tech.challenge.zonaAzul.veiculo.dto.VeiculoRecord;
import com.tech.challenge.zonaAzul.veiculo.form.VeiculoForm;
import com.tech.challenge.zonaAzul.veiculo.model.entity.Veiculo;
import com.tech.challenge.zonaAzul.veiculo.model.repository.VeiculoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VeiculoService {

    private static final Logger log = LoggerFactory.getLogger(VeiculoService.class);

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private CondutorRepository condutorRepository;

    public VeiculoRecord novoVeiculo(VeiculoForm veiculoForm, Condutor condutor, Boolean condutorPrincipal) throws VeiculoAlreadyExistsException, VeiculoNoDriverExistsException {

        Veiculo veiculo = VeiculoMappers.paraVeiculo(veiculoForm);
        Boolean veiculoExistenteCondutorPrincipal = repository.existsByPlacaAndCnhCondutorPrincipal(veiculoForm.getPlaca(), condutor.getCnh());
        Boolean veiculoExistenteCondutorSecundario = repository.existsByPlacaAndCnhCondutorSecundario(veiculoForm.getPlaca(), condutor.getCnh());

        if (condutor.getCnh() !=null){
            return salvarVeiculo(condutor, veiculoExistenteCondutorPrincipal, veiculoExistenteCondutorSecundario, veiculo, condutorPrincipal);
        }else {
            log.info("Veículo com a placa: "+veiculoForm.getPlaca()+" não pode ser cadastrado sem uma CNH");
            throw new VeiculoNoDriverExistsException("Veículo não pode ser cadastrado sem uma CNH");
        }
    }

    private VeiculoRecord salvarVeiculo(Condutor condutor, Boolean veiculoExistenteCondutorPrincipal, Boolean veiculoExistenteCondutorSecundario, Veiculo veiculo, Boolean condutorPrincipal) throws VeiculoAlreadyExistsException {

            List<Veiculo> veiculoList = new ArrayList<>();
            veiculoList.add(veiculo);

            condutor.setVeiculos(veiculoList);

            if (condutorPrincipal && Boolean.FALSE.equals(veiculoExistenteCondutorPrincipal)){
                veiculo.setCnhCondutorPrincipal(condutor.getCnh());
            }else if (!condutorPrincipal && Boolean.FALSE.equals(veiculoExistenteCondutorSecundario)){
                veiculo.setCnhCondutorSecundario(condutor.getCnh());
            }else {
                throw new VeiculoAlreadyExistsException("Veiculo já existe no CPF: "+ condutor.getCpf());
            }

            condutorRepository.save(condutor);
            repository.save(veiculo);

            return VeiculoMappers.paraVeiculoRecord(veiculo);
    }

}
