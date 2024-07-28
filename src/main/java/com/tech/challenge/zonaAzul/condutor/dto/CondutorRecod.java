package com.tech.challenge.zonaAzul.condutor.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public record CondutorRecod(
        String nome,
        String cpf,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy' 'HH:mm:ss", timezone = "America/Sao_Paulo")
        Date dataNascimento,
        String cnh,
        EnderecoRecord endereco,
        BigDecimal saldo,
        TipoPagamentoRecord tipoPagamentoPrincpal,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy' 'HH:mm:ss", timezone = "America/Sao_Paulo") Date dataHoraCadastro,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy' 'HH:mm:ss", timezone = "America/Sao_Paulo") Date dataHoraAtualicao,
        Boolean clienteAtivo
) {
}
