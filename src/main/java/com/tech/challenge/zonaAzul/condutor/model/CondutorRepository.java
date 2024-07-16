package com.tech.challenge.zonaAzul.condutor.model;

import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;
import com.tech.challenge.zonaAzul.veiculo.model.entity.Veiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CondutorRepository extends MongoRepository<Condutor, String> {
    Condutor findByCpf(String cpf);

    void deleteByCpf(String cpf);

    Boolean existsByVeiculos(Veiculo veiculo);
}
