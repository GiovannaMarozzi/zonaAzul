package com.tech.challenge.zonaAzul.util.mappers;

import com.tech.challenge.zonaAzul.condutor.dto.CondutorRecod;
import com.tech.challenge.zonaAzul.condutor.form.CondutorForm;
import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;

import java.util.ArrayList;
import java.util.List;

public class CondutorMappers {

    public static CondutorRecod condutorMapperDTO(Condutor condutor) {
        CondutorRecod condutorRecod = new CondutorRecod(condutor.getNome(),
                condutor.getCpf(),
                condutor.getDataNascimento(),
                condutor.getCnh(),
                EnderecoMappers.enderecoMapperDTO(condutor.getEndereco()),
                condutor.getSaldo(),
                TipoPagamentoMappers.mapper(condutor.getTipoPagamentoPrincipal()),
                condutor.getDataHoraCadastro(),
                condutor.getDataHoraAtualizacao(),
                true);

        return condutorRecod;
    }

    public static Condutor condutorMapper(CondutorForm condutorForm) {
        Condutor condutor = new Condutor();

        condutor.setNome(condutorForm.getNome());
        condutor.setCpf(condutorForm.getCpf());
        condutor.setDataNascimento(condutorForm.getDataNascimento());
        condutor.setCnh(condutorForm.getCnh());
        condutor.setEndereco(EnderecoMappers.enderecoMapper(condutorForm.getEndereco()));
        condutor.setSaldo(condutorForm.getSaldo());
        condutor.setTipoPagamentoPrincipal(condutorForm.getTipoPagamentoPrincipal());

        return condutor;
    }

    public static List<CondutorRecod> condutorMapper(List<Condutor> condutorList) {

        List<CondutorRecod> condutorRecodList = new ArrayList<CondutorRecod>();

        condutorList.forEach(lista ->{
            CondutorRecod condutorRecod = condutorMapperDTO(lista);
            condutorRecodList.add(condutorRecod);
        });

        return condutorRecodList;
    }
}
