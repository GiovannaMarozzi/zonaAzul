package com.tech.challenge.zonaAzul.condutor.form;

import com.tech.challenge.zonaAzul.condutor.model.entity.Endereco;
import com.tech.challenge.zonaAzul.util.enums.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Date dataHoraCadastro = new Date();
}
