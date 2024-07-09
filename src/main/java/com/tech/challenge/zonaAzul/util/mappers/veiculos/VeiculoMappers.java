package com.tech.challenge.zonaAzul.util.mappers.veiculos;

import com.tech.challenge.zonaAzul.util.mappers.condutor.CondutorMappers;
import com.tech.challenge.zonaAzul.veiculo.model.dto.VeiculoRecord;
import com.tech.challenge.zonaAzul.veiculo.model.model.entity.Veiculo;
import com.tech.challenge.zonaAzul.veiculo.model.form.VeiculoForm;

import java.util.ArrayList;
import java.util.List;

public class VeiculoMappers {

    public static Veiculo paraVeiculo(VeiculoForm veiculoForm){
        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca(veiculoForm.getPlaca());
        veiculo.setModeloVeiculo(veiculoForm.getModeloVeiculo());
        veiculo.setMarca(veiculoForm.getMarca());
        veiculo.setCor(veiculoForm.getCor());

        return veiculo;
    }


    public  static VeiculoRecord paraVeiculoRecord(Veiculo veiculo){
        VeiculoRecord veiculoRecord = new VeiculoRecord(veiculo.getPlaca(),
                veiculo.getModeloVeiculo(),
                veiculo.getMarca(),
                veiculo.getCor(),
                CondutorMappers.condutorMapperDTO(veiculo.getCondutorPrincipal()));

        return veiculoRecord;
    }

    public static List<VeiculoRecord> paraVeiculo(List<Veiculo> veiculos){

        List<VeiculoRecord> veiculoRecordList = new ArrayList<VeiculoRecord>();

        veiculos.forEach(lista -> {
            VeiculoRecord veiculoRecord = paraVeiculoRecord(lista);
            veiculoRecordList.add(veiculoRecord);
        });

        return  veiculoRecordList;
    };
}
