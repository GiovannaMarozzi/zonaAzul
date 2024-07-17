package com.tech.challenge.util.mappers.vaga;

import com.tech.challenge.zonaAzul.vaga.dto.VagaRecord;
import com.tech.challenge.zonaAzul.vaga.form.VagaForm;
import com.tech.challenge.zonaAzul.vaga.model.entity.Vaga;
import com.tech.challenge.zonaAzul.veiculo.model.entity.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class VagaMapper {

    public static Vaga paraVaga(VagaForm form){
        Vaga vaga = new Vaga();
        vaga.setLocal(form.getLocal());
        vaga.setQuantidadeVaga(form.getQuantidadeVaga());

        return vaga;
    }

    public static VagaRecord paraVagaRecord(Vaga form){

        VagaRecord vagaRecord = new VagaRecord(form.getId(), form.getLocal(), form.getQuantidadeVaga());
        return vagaRecord;
    }

    public static List<VagaRecord> paraVaga(List<Vaga> vagaList){

        List<VagaRecord> vagas = new ArrayList<VagaRecord>();

        vagaList.forEach(lista -> {
            VagaRecord vagasRecord = paraVagaRecord(lista);
            vagas.add(vagasRecord);
        });
        return vagas;
    }


}
