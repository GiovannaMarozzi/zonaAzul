package com.tech.challenge.zonaAzul.condutor.form;

import com.tech.challenge.util.enums.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CondutorForm {
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String cnh;
    private EnderecoForm endereco;
    private BigDecimal saldo;
    private TipoPagamento tipoPagamentoPrincipal;
}
