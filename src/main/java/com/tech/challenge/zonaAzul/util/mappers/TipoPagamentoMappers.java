package com.tech.challenge.zonaAzul.util.mappers;

import com.tech.challenge.zonaAzul.condutor.dto.TipoPagamentoRecord;
import com.tech.challenge.zonaAzul.util.enums.TipoPagamento;

public class TipoPagamentoMappers {

    public static TipoPagamentoRecord mapper(TipoPagamento tipoPagamento){
        TipoPagamentoRecord tipoPagamentoRecord = new TipoPagamentoRecord(tipoPagamento);
        return tipoPagamentoRecord;
    }
}
