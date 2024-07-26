package com.tech.challenge.zonaAzul.ticket.model.repository;

import com.tech.challenge.zonaAzul.ticket.model.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String>  {

    List<Ticket> findByPlaca(String placa);
}
