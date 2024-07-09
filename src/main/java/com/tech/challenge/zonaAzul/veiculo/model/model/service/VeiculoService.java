package com.tech.challenge.zonaAzul.veiculo.model.model.service;

import com.tech.challenge.zonaAzul.condutor.model.CondutorRepository;
import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;
import com.tech.challenge.zonaAzul.util.exception.veiculo.VeiculoAlreadyExistsException;
import com.tech.challenge.zonaAzul.util.mappers.veiculos.VeiculoMappers;
import com.tech.challenge.zonaAzul.veiculo.model.dto.VeiculoRecord;
import com.tech.challenge.zonaAzul.veiculo.model.model.entity.Veiculo;
import com.tech.challenge.zonaAzul.veiculo.model.form.VeiculoForm;
import com.tech.challenge.zonaAzul.veiculo.model.model.repository.VeiculoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    private static final Logger log = LoggerFactory.getLogger(VeiculoService.class);

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private CondutorRepository condutorRepository;

    public VeiculoRecord novoVeiculo(VeiculoForm veiculoForm, Condutor condutor, Boolean condutorPrincipal) throws VeiculoAlreadyExistsException {

        Boolean veiculoExistente = repository.existsByPlaca(veiculoForm.getPlaca());
        Boolean condutorPrincipalExiste = condutorRepository.existsById(condutor.getId());

        if (Boolean.FALSE.equals(veiculoExistente) && Boolean.TRUE.equals(condutorPrincipal)){
            Veiculo veiculo = VeiculoMappers.paraVeiculo(veiculoForm);
            veiculo.setCondutorPrincipal(condutor);

            Boolean veiculoExistenteCondutor = condutorRepository.existsByVeiculos_placa(veiculo.getPlaca());

            if (Boolean.TRUE.equals(condutorPrincipalExiste) && Boolean.FALSE.equals(veiculoExistenteCondutor)){
                condutor.setVeiculos(veiculo);
                condutorRepository.save(condutor);
                repository.save(veiculo);
            }
            else if (Boolean.TRUE.equals(condutorPrincipalExiste) && Boolean.TRUE.equals(veiculoExistenteCondutor)) {
                throw new VeiculoAlreadyExistsException("Veiculo já existe no CPF: "+condutor.getCpf());
            }
            return VeiculoMappers.paraVeiculoRecord(veiculo);

        }else if (Boolean.FALSE.equals(veiculoExistente) && Boolean.FALSE.equals(condutorPrincipal)){
            log.info("Veículo com a placa: "+veiculoForm.getPlaca()+" não pode ser cadastrado sem uma CNH principal");
            throw new VeiculoAlreadyExistsException("Veículo não pode ser cadastrado sem uma CNH principal");
        }else {
            log.info("Veículo com a placa: "+veiculoForm.getPlaca()+" já existente!");
            throw new VeiculoAlreadyExistsException("Veículo já cadastrado no sistema para este CPF");
        }
    }

}
