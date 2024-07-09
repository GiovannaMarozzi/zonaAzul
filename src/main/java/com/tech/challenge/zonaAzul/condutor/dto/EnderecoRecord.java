package com.tech.challenge.zonaAzul.condutor.dto;

public record EnderecoRecord(String cep,
        String cidade,
        String logradouro,
        String numero,
        String bairro,
        String complemento,
        String estado) {
}
