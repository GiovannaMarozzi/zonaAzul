package com.tech.challenge.zonaAzul.condutor.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoForm {
    private String cep;
    private String cidade;
    private String logradouro;
    private String numero;
    private String bairro;
    private String complemento;
    private String estado;
}
