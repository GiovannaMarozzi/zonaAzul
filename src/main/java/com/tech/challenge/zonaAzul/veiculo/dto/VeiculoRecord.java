package com.tech.challenge.zonaAzul.veiculo.dto;

import com.tech.challenge.zonaAzul.condutor.dto.CondutorRecod;

public record VeiculoRecord(String placa,
                            String modelo,
                            String marca,
                            String cor,
                            String condutorPrincipal,
                            String condutorSecundario) {
}
