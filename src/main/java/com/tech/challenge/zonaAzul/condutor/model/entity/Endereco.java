package com.tech.challenge.zonaAzul.condutor.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String cep;
    private String cidade;
    private String logradouro;
    private String numero;
    private String bairro;
    private String complemento;
    private String estado;
}
