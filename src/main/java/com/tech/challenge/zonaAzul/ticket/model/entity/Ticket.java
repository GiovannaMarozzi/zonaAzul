package com.tech.challenge.zonaAzul.ticket.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tech.challenge.zonaAzul.vaga.model.entity.Vaga;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Document(collection = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    private String id = new ObjectId().toString();
    private Integer tempo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy' 'HH:mm:ss", timezone = "America/Sao_Paulo")
    private Date dataHoraEntrada = new Date();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy' 'HH:mm:ss", timezone = "America/Sao_Paulo")
    private Date dataHoraSaida;
    private String placa;
    private BigDecimal valorTicket = BigDecimal.valueOf(2.00);
    private Boolean regularizado = true;
    private String ultimaCnh;

    public void calcularDataHoraSaida() {
        if (this.tempo != null && this.dataHoraEntrada != null) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
            calendar.setTime(this.dataHoraEntrada);
            calendar.add(Calendar.HOUR_OF_DAY, this.tempo);
            this.dataHoraSaida = calendar.getTime();
        }
    }
}
