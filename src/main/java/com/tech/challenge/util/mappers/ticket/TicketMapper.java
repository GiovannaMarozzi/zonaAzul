package com.tech.challenge.util.mappers.ticket;

import com.tech.challenge.zonaAzul.ticket.dto.TicketRecord;
import com.tech.challenge.zonaAzul.ticket.form.TicketForm;
import com.tech.challenge.zonaAzul.ticket.model.entity.Ticket;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class TicketMapper {

    public Ticket paraTicket(TicketForm ticketForm){
        Ticket ticket = new Ticket();
        ticket.setTempo(ticketForm.getTempo());
        ticket.setPlaca(ticketForm.getPlaca());
        ticket.setUltimaCnh(ticketForm.getCnh());
        ticket.calcularDataHoraSaida();

        return ticket;
    }

    public TicketRecord paraTicketRecord(Ticket ticket){
        TicketRecord ticketRecord = new TicketRecord(ticket.getId(),
                                    ticket.getTempo(),
                                    ticket.getDataHoraEntrada(),
                                    ticket.getDataHoraSaida(),
                                    ticket.getPlaca(),
                                    ticket.getValorTicket(),
                                    ticket.getUltimaCnh());

        return ticketRecord;
    }

    public List<TicketRecord> paraTicketRecord(List<Ticket> ticketList){
        List<TicketRecord> ticketRecordList = new ArrayList<TicketRecord>();

        ticketList.forEach(lista -> {
            TicketRecord ticketRecord = paraTicketRecord(lista);
            ticketRecordList.add(ticketRecord);
        });

        return ticketRecordList;
    }
}
