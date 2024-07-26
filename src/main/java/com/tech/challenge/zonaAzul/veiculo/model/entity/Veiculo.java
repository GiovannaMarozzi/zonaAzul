package com.tech.challenge.zonaAzul.veiculo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "veiculo")
public class Veiculo {

    @Id
    private String id = new ObjectId().toString();
    private String placa;
    private String modeloVeiculo;
    private String marca;
    private String cor;
    private String cnhCondutorPrincipal;
    private String cnhCondutorSecundario;
}
