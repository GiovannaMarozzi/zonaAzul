package com.tech.challenge.util.mappers.condutor;

import com.tech.challenge.util.enums.TipoPagamento;
import com.tech.challenge.zonaAzul.condutor.dto.TipoPagamentoRecord;

public class TipoPagamentoMappers {

    public static TipoPagamentoRecord mapper(TipoPagamento tipoPagamento){
        TipoPagamentoRecord tipoPagamentoRecord = new TipoPagamentoRecord(tipoPagamento);
        return tipoPagamentoRecord;
    }
}
