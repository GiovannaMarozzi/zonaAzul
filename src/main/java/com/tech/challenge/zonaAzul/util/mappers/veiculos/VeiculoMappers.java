package com.tech.challenge.zonaAzul.util.mappers.veiculos;

import com.tech.challenge.zonaAzul.util.mappers.condutor.CondutorMappers;
import com.tech.challenge.zonaAzul.veiculo.dto.VeiculoRecord;
import com.tech.challenge.zonaAzul.veiculo.model.entity.Veiculo;
import com.tech.challenge.zonaAzul.veiculo.form.VeiculoForm;

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


    public static VeiculoRecord paraVeiculoRecord(Veiculo veiculo){
        VeiculoRecord veiculoRecord = new VeiculoRecord(veiculo.getPlaca(),
                veiculo.getModeloVeiculo(),
                veiculo.getMarca(),
                veiculo.getCor(),
                veiculo.getCnhCondutorPrincipal(),
                veiculo.getCnhCondutorSecundario());

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
