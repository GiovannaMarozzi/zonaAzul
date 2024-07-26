package com.tech.challenge.zonaAzul.ticket.controller;

import com.tech.challenge.util.exception.condutor.InsufficientFundsException;
import com.tech.challenge.util.exception.condutor.NoSuchRecordException;
import com.tech.challenge.util.exception.ticket.DriverAlreadyRegularizedException;
import com.tech.challenge.util.exception.ticket.TicketNotFoundException;
import com.tech.challenge.util.exception.ticket.UnregularizedTicketException;
import com.tech.challenge.util.exception.ticket.VehicleNotFoundException;
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
    public ResponseEntity<TicketRecord> novoTicket(@RequestBody TicketForm form) throws UnregularizedTicketException, VehicleNotFoundException, InsufficientFundsException, NoSuchRecordException {
        ResponseEntity response = null;

        try {
            TicketRecord ticketRecord = service.novoTicket(form);
            response = ResponseEntity.status(HttpStatus.OK).body(ticketRecord);
        } catch (UnregularizedTicketException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (VehicleNotFoundException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InsufficientFundsException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchRecordException e) {
            response =  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            response =  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TicketRecord>> todosTickets() {
        ResponseEntity response = null;
        try {
            List<TicketRecord> ticketRecordList = service.todosTickets();
            response = ResponseEntity.status(HttpStatus.OK).body(ticketRecordList);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @GetMapping("/todos/placa={placa}")
    public ResponseEntity<List<TicketRecord>> todosTicketsPorData(@PathVariable String placa){
        ResponseEntity response = null;

        try {
            List<TicketRecord> ticketRecordList = service.todosTicketsPorPlaca(placa);
            response =  ResponseEntity.status(HttpStatus.OK).body(ticketRecordList);
        } catch (Exception e) {
            response =  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return response;
    }

    @PostMapping("/regularizar={id}")
    public ResponseEntity<?> regularizarTicket(@PathVariable String id) {
        ResponseEntity response = null;

        try {
            service.regularizarTicket(id);
            response =  ResponseEntity.status(HttpStatus.OK).body("Ticket e condutor regularizado com sucesso!");
        } catch (TicketNotFoundException e) {
            response =  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NoSuchRecordException e) {
            response =  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DriverAlreadyRegularizedException e) {
            response =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            response =  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/multa={placa}")
    public ResponseEntity<?> multarVeiculo(@PathVariable String placa) {
        ResponseEntity response = null;

        try {
            service.multarVeiculo(placa);
            response = ResponseEntity.status(HttpStatus.OK).body("Multa aplicada com sucesso!");
        } catch (TicketNotFoundException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro inesperado: " + e.getMessage());
        }
        return response;
    }
}
