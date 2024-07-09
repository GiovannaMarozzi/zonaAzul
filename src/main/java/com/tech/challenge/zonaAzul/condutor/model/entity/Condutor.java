package com.tech.challenge.zonaAzul.condutor.model.entity;

import com.tech.challenge.zonaAzul.util.enums.TipoPagamento;
import com.tech.challenge.zonaAzul.veiculo.model.model.entity.Veiculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Document(collection = "condutor")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Veiculo veiculos;
}
