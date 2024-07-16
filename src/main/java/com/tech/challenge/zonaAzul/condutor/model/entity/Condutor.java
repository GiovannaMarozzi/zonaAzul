package com.tech.challenge.zonaAzul.condutor.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tech.challenge.zonaAzul.util.enums.TipoPagamento;
import com.tech.challenge.zonaAzul.veiculo.model.entity.Veiculo;
import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "condutor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Condutor {

    @Id
    private String id = new ObjectId().toString();
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String cnh;
    private Endereco endereco;
    private BigDecimal saldo;
    private TipoPagamento tipoPagamentoPrincipal;
    private Date dataHoraCadastro = new Date();
    private Date dataHoraAtualizacao = new Date();
    private Boolean clienteAtivo = true;

    @JsonManagedReference
    @DBRef(lazy = true)
    @DocumentReference
    private List<Veiculo> veiculos = new ArrayList<>();
}
