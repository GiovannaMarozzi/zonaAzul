package com.tech.challenge.zonaAzul.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;

public record TicketRecord(
        String id,
        Integer tempo,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy' 'HH:mm:ss", timezone = "America/Sao_Paulo") Date dataHoraEntrada,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy' 'HH:mm:ss", timezone = "America/Sao_Paulo") Date dataHoraSaida,
        String placa,
        BigDecimal valorTicket,
        String ultimaCnh) {}
