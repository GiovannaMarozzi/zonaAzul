package com.tech.challenge.zonaAzul.veiculo.model.repository;

import com.tech.challenge.zonaAzul.veiculo.model.entity.Veiculo;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

    Boolean existsByPlaca(String placa);

    Boolean existsByPlacaAndCnhCondutorPrincipal(String placa, String cnhCondutorPrincipal);
    Boolean existsByPlacaAndCnhCondutorSecundario(String placa, String cnhCondutorPrincipal);

    Veiculo findByPlaca(String placa);

    void deleteByPlaca(String placa);
}
