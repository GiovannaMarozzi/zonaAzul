package com.tech.challenge.zonaAzul.ticket.controller;


import com.tech.challenge.zonaAzul.ticket.dto.TicketRecord;
import com.tech.challenge.zonaAzul.ticket.form.TicketForm;
import com.tech.challenge.zonaAzul.ticket.model.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {

    @Autowired
    TicketService service;

    @PostMapping("/novoTicket")
    public ResponseEntity<TicketRecord> novoTicket(@RequestBody TicketForm form){
        TicketRecord ticketRecord = service.novoTicket(form);
        return ResponseEntity.status(HttpStatus.OK).body(ticketRecord);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TicketRecord>> todosTickets(){
        List<TicketRecord> ticketRecordList = service.todosTickets();
        return ResponseEntity.status(HttpStatus.OK).body(ticketRecordList);
    }

    @GetMapping("/todos/placa={placa}")
    public ResponseEntity<List<TicketRecord>> todosTicketsPorData(@PathVariable String placa){
        List<TicketRecord> ticketRecordList = service.todosTicketsPorPlaca(placa);
        return ResponseEntity.status(HttpStatus.OK).body(ticketRecordList);
    }

    @PostMapping("/regularizar={id}")
    public ResponseEntity regularizarTicket(@PathVariable String id){
        service.regularizarTicket(id);
        return null;
    }

    @PostMapping("/multa={placa}")
    public ResponseEntity multarVeiculo(@PathVariable String placa){
        service.multarVeiculo(placa);
        return null;
    }

    //Criar as regras de negócio, para que possa:
//    Criar sistema de pagamento para que possa ser debitado da conta do próprio condutor
}
