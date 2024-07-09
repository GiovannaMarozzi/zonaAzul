package com.tech.challenge.zonaAzul.condutor.model;

import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CondutorRepository extends MongoRepository<Condutor, String> {
    Condutor findByCpf(String cpf);

    void deleteByCpf(String cpf);
}
