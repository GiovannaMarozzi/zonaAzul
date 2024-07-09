package com.tech.challenge.zonaAzul.veiculo.model.model.repository;

import com.tech.challenge.zonaAzul.veiculo.model.model.entity.Veiculo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

    Boolean existsByPlaca(String placa);
}
