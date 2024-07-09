package com.tech.challenge.zonaAzul.veiculo.model.form;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoForm {

    private String placa;
    private String modeloVeiculo;
    private String marca;
    private String cor;
}
