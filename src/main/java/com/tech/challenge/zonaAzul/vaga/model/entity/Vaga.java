package com.tech.challenge.zonaAzul.vaga.model.entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "vaga")
public class Vaga {

    @Id
    private String id = new ObjectId().toString();
    private String local;
}
