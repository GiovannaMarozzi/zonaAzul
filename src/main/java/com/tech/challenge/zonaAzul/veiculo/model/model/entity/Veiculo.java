package com.tech.challenge.zonaAzul.veiculo.model.model.entity;

import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


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
    @DBRef
    private Condutor condutorPrincipal;
    private Condutor condutorSecundario;
}
