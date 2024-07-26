package com.tech.challenge.zonaAzul.ticket.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketForm {
    private Integer tempo;
    private String placa;
    private String cnh;

}
