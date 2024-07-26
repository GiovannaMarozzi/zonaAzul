package com.tech.challenge.zonaAzul.vaga.model.repository;

import com.tech.challenge.zonaAzul.vaga.model.entity.Vaga;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VagaRepository extends MongoRepository<Vaga, String> {
    Boolean existsByLocal(String local);

    Optional<Vaga> findById(String id);

    Vaga findByLocal(String local);
}
