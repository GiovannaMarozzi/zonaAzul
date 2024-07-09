package com.tech.challenge.zonaAzul.condutor.dto;


import java.math.BigDecimal;
import java.util.Date;

public record CondutorRecod(
        String nome,
        String cpf,
        Date dataNascimento,
        String cnh,
        EnderecoRecord endereco,
        BigDecimal saldo,
        TipoPagamentoRecord tipoPagamentoPrincpal,
        Date dataHoraCadastro,
        Date dataHoraAtualicao,
        Boolean clienteAtivo
) {
}
