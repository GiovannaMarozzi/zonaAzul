package com.tech.challenge.zonaAzul.veiculo.model.service;

import com.tech.challenge.util.exception.veiculo.VeiculoNoSurchExistsException;
import com.tech.challenge.zonaAzul.condutor.model.CondutorRepository;
import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;
import com.tech.challenge.util.exception.veiculo.VeiculoAlreadyExistsException;
import com.tech.challenge.util.exception.veiculo.VeiculoNoDriverExistsException;
import com.tech.challenge.util.mappers.veiculos.VeiculoMappers;
import com.tech.challenge.zonaAzul.veiculo.dto.VeiculoRecord;
import com.tech.challenge.zonaAzul.veiculo.form.VeiculoForm;
import com.tech.challenge.zonaAzul.veiculo.model.entity.Veiculo;
import com.tech.challenge.zonaAzul.veiculo.model.repository.VeiculoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        if (condutor.getCnh() != null) {
            return salvarVeiculo(condutor, veiculoExistenteCondutorPrincipal, veiculoExistenteCondutorSecundario, veiculoForm, veiculo, condutorPrincipal);
        } else {
            log.info("Veículo com a placa: " + veiculoForm.getPlaca() + " não pode ser cadastrado sem uma CNH");
            throw new VeiculoNoDriverExistsException("Veículo não pode ser cadastrado sem uma CNH");
        }
    }

    private VeiculoRecord salvarVeiculo(Condutor condutor, Boolean veiculoExistenteCondutorPrincipal, Boolean veiculoExistenteCondutorSecundario, VeiculoForm veiculoForm, Veiculo veiculo, Boolean condutorPrincipal) throws VeiculoAlreadyExistsException {

        List<Veiculo> veiculoList = new ArrayList<>();
        veiculoList.add(veiculo);

        condutor.setVeiculos(veiculoList);

        Veiculo veiculoExistente = repository.findByPlaca(veiculoForm.getPlaca());

        if (veiculoExistente != null) {
            if (condutorPrincipal && !veiculoExistenteCondutorPrincipal) {
                veiculoExistente.setCnhCondutorPrincipal(condutor.getCnh());
            } else if (!condutorPrincipal && !veiculoExistenteCondutorSecundario) {
                veiculoExistente.setCnhCondutorSecundario(condutor.getCnh());
            } else {
                throw new VeiculoAlreadyExistsException("Veiculo já existe no CPF: " + condutor.getCpf());
            }

            condutorRepository.save(condutor);
            repository.save(veiculoExistente);

        } else {
            if (condutorPrincipal) {
                veiculo.setCnhCondutorPrincipal(condutor.getCnh());
            } else {
                veiculo.setCnhCondutorSecundario(condutor.getCnh());
            }

            condutorRepository.save(condutor);
            repository.save(veiculo);
        }
        return VeiculoMappers.paraVeiculoRecord(veiculo);
    }

    public List<VeiculoRecord> veiculos() throws VeiculoNoSurchExistsException {
        List<Veiculo> veiculos = repository.findAll();

        if (!veiculos.isEmpty()){
            return VeiculoMappers.paraVeiculo(veiculos);
        }else{
            throw new VeiculoNoSurchExistsException("Nenhum veículo encontrado!");
        }

    }

    public VeiculoRecord editarVeiculo(VeiculoForm veiculoForm) throws VeiculoNoSurchExistsException {
        Veiculo veiculo = repository.findByPlaca(veiculoForm.getPlaca());

        if (veiculo != null) {
            veiculo.setModeloVeiculo(veiculoForm.getModeloVeiculo());
            veiculo.setMarca(veiculoForm.getMarca());
            veiculo.setCor(veiculoForm.getCor());

            VeiculoRecord veiculoRecord = VeiculoMappers.paraVeiculoRecord(veiculo);

            repository.save(veiculo);
            log.info("Informações do veículo com a placa: "+veiculo.getPlaca() + "editado!");

            return veiculoRecord;
        }else{
            throw new VeiculoNoSurchExistsException("Nenhum veículo encontrado!");
        }
    }

    public VeiculoRecord veiculoPorPlaca(String placa) throws VeiculoNoSurchExistsException {
        Veiculo veiculo = repository.findByPlaca(placa);

        if (veiculo != null){
            return VeiculoMappers.paraVeiculoRecord(veiculo);
        }else{
            throw new VeiculoNoSurchExistsException("Nenhum veículo encontrado!");
        }

    }

    public void deletarVeiculoPorPlaca(String placa) throws VeiculoNoSurchExistsException {

        Boolean veiculoExistente = repository.existsByPlaca(placa);

        if (veiculoExistente) {
            repository.deleteByPlaca(placa);
            log.info("Veículo deletado com sucesso!");
        }else{
            throw new VeiculoNoSurchExistsException("Nenhum veículo encontrado!");
        }
    }

    public Boolean verificarVeiculoCnh(String placa, String ultimaCnh) {
        Boolean condutorPrincipal =  repository.existsByPlacaAndCnhCondutorPrincipal(placa, ultimaCnh);
        Boolean condutorSecundario = repository.existsByPlacaAndCnhCondutorSecundario(placa, ultimaCnh);

        if (condutorPrincipal || condutorSecundario){
            return true;
        }
        return false;
    }
}
