package com.tech.challenge.zonaAzul.condutor.model.service;

import com.tech.challenge.zonaAzul.condutor.dto.CondutorRecod;
import com.tech.challenge.zonaAzul.condutor.form.CondutorForm;
import com.tech.challenge.zonaAzul.condutor.model.CondutorRepository;
import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;
import com.tech.challenge.util.exception.condutor.ConductorAlreadyExistsException;
import com.tech.challenge.util.exception.condutor.NoSuchRecordException;
import com.tech.challenge.util.exception.veiculo.VeiculoAlreadyExistsException;
import com.tech.challenge.util.exception.veiculo.VeiculoNoDriverExistsException;
import com.tech.challenge.util.mappers.condutor.CondutorMappers;
import com.tech.challenge.zonaAzul.veiculo.form.VeiculoForm;
import com.tech.challenge.zonaAzul.veiculo.model.service.VeiculoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CondutorService {

    private static final Logger log = LoggerFactory.getLogger(CondutorService.class);

    @Autowired
    private CondutorRepository repository;

    @Autowired
    private VeiculoService veiculoService;


    public CondutorRecod salvar(CondutorForm condutorForm) throws ConductorAlreadyExistsException, NoSuchRecordException {
        return criarOuAtualizar(condutorForm, true);

    }

    private CondutorRecod criarOuAtualizar(CondutorForm condutorForm, boolean novo) throws ConductorAlreadyExistsException, NoSuchRecordException{
        CondutorRecod condutorRecod = null;

        Condutor condutor = CondutorMappers.condutorMapper(condutorForm);
        Condutor condutorExistente = verificaCondutor(condutorForm.getCpf());

        if (condutorExistente == null && Boolean.TRUE.equals(novo)){
            condutorRecod = persistirCondutor(condutor);
            log.info("Cadastro efetuado com sucesso!");
        }else if(condutorExistente != null && Boolean.FALSE.equals(novo)){
            condutor.setId(condutorExistente.getId());
            condutor.setDataHoraCadastro(condutorExistente.getDataHoraCadastro());
            condutor.setDataHoraAtualizacao(new Date());
            condutorRecod = persistirCondutor(condutor);
            log.info("Condutor atualizado com sucesso!");
        }else{
            if (Boolean.TRUE.equals(novo)){
                throw new ConductorAlreadyExistsException("Condutor com o CPF: "+condutorForm.getCpf()+" já existente no sistema!");
            }else{
                throw new NoSuchRecordException("Erro ao atualizar condutor com o CPF: "+condutor.getCpf()+"\b" +" Condutor não existe no sistema");
            }
        }
        return condutorRecod;
    }

    private CondutorRecod persistirCondutor(Condutor condutor) {
        Condutor novoCondutor = repository.save(condutor);
        CondutorRecod condutorRecod = CondutorMappers.condutorMapperDTO(novoCondutor);
        return condutorRecod;
    }

    public List<CondutorRecod> condutores() throws NoSuchRecordException {
        List<Condutor> condutorList = null;
        List<CondutorRecod> condutorRecodList = null;

        condutorList = repository.findAll();
        condutorRecodList = CondutorMappers.condutorMapper(condutorList);

        if (condutorRecodList.isEmpty()){
            log.info("Não foram encontrado registros!");
            throw new NoSuchRecordException("Não foram encontrado registros!");
        }

        return condutorRecodList;
    }

    public CondutorRecod condutor(String cpf) throws NoSuchRecordException {
        Condutor condutor = verificaCondutor(cpf);

        if (condutor != null){
            CondutorRecod condutorRecod = CondutorMappers.condutorMapperDTO(condutor);
            return condutorRecod;
        }else {
            throw new NoSuchRecordException("Condutor com o CPF: "+cpf+" não encontrado");
        }


    }

    public CondutorRecod editarDadosCondutor(CondutorForm condutorForm) throws ConductorAlreadyExistsException, NoSuchRecordException {
        return criarOuAtualizar(condutorForm, false);
    }

    public void deletarCondutor(String cpf) throws NoSuchRecordException{
        Condutor condutorExistente = verificaCondutor(cpf);

        if (condutorExistente != null){
            repository.deleteByCpf(cpf);
            log.info("Cadastro deletado com sucesso");
        }else {
            throw new NoSuchRecordException("Condutor com o CPF: "+cpf+" não encontrado!");
        }
    }

    private Condutor verificaCondutor(String cpf) {
        Condutor condutorExistente = repository.findByCpf(cpf);
        return condutorExistente;
    }

    public void adicionarNovoVeiculo(String cpf, Boolean condPrincipal, VeiculoForm veiculoForm) throws NoSuchRecordException, VeiculoAlreadyExistsException, VeiculoNoDriverExistsException {

        Condutor condutor = verificaCondutor(cpf);

        if (condutor != null){
            veiculoService.novoVeiculo(veiculoForm, condutor, condPrincipal);
        }else {
            log.info("Condutor com o CPF: "+cpf+" não cadastrado");
            throw new NoSuchRecordException("Condutor com o CPF: "+cpf+" não cadastrado");
        }

    }
}
