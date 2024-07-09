package com.tech.challenge.zonaAzul.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoPagamento {
    DEBITO("DEBITO"),
    CREDITO("CREDITO"),
    PIX("PIX");

    private String value;

    TipoPagamento(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TipoPagamento fromString(String text) {
        if (text != null) {
            for (TipoPagamento tipo : TipoPagamento.values()) {
                if (text.equalsIgnoreCase(tipo.value)) {
                    return tipo;
                }
            }
        }
        throw new IllegalArgumentException("Tipo de pagamento inválido: " + text);
    }
}
