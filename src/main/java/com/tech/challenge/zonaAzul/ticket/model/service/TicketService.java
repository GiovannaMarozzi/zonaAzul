package com.tech.challenge.zonaAzul.ticket.model.service;

import com.tech.challenge.util.mappers.ticket.TicketMapper;
import com.tech.challenge.zonaAzul.condutor.model.CondutorRepository;
import com.tech.challenge.zonaAzul.condutor.model.entity.Condutor;
import com.tech.challenge.zonaAzul.condutor.model.service.CondutorService;
import com.tech.challenge.zonaAzul.ticket.dto.TicketRecord;
import com.tech.challenge.zonaAzul.ticket.form.TicketForm;
import com.tech.challenge.zonaAzul.ticket.model.entity.Ticket;
import com.tech.challenge.zonaAzul.ticket.model.repository.TicketRepository;
import com.tech.challenge.zonaAzul.veiculo.model.repository.VeiculoRepository;
import com.tech.challenge.zonaAzul.veiculo.model.service.VeiculoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    @Autowired
    private TicketRepository repository;

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private CondutorService condutorService;

    TicketMapper ticketMapper = new TicketMapper();

    public TicketRecord novoTicket(TicketForm ticketForm) {
        Ticket ticket = ticketMapper.paraTicket(ticketForm);
        Boolean verificarCnh = veiculoService.verificarVeiculoCnh(ticket.getPlaca(), ticket.getUltimaCnh());
        Condutor condutor = condutorRepository.findByCnh(ticket.getUltimaCnh());

        if (verificarCnh){
            if (condutor.getClienteAtivo()){
                BigDecimal valorFinalTicket = ticket.getValorTicket().multiply(BigDecimal.valueOf(ticket.getTempo()));
                ticket.setValorTicket(valorFinalTicket);

                condutorService.debitarSaldo(ticket.getUltimaCnh(), ticket.getValorTicket());

                repository.save(ticket);
                TicketRecord ticketRecord = ticketMapper.paraTicketRecord(ticket);
                return ticketRecord;
            }else{
                log.error("Cliente irregular, favor regularizar o ticket");
            }

        }else{
            log.error("Nenhum ticket irregular para a cnh");
        }
        return null;
    }

    public List<TicketRecord> todosTickets() {
        List<Ticket> ticketList = repository.findAll();
        List<TicketRecord> ticketRecordList = ticketMapper.paraTicketRecord(ticketList);

        return ticketRecordList;
    }

    public List<TicketRecord> todosTicketsPorPlaca(String placa) {
        List<Ticket> ticketList = repository.findByPlaca(placa);
        List<TicketRecord> ticketRecordList = ticketMapper.paraTicketRecord(ticketList);

        return ticketRecordList;
    }

    public void regularizarTicket(String id) {
        Optional<Ticket> ticketOpt = repository.findById(id);

        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();

            if (Boolean.FALSE.equals(ticket.getRegularizado())) {
                ticket.setRegularizado(true);
                repository.save(ticket);
            }
            String ultimaCnh = ticket.getUltimaCnh();
            regularizarCondutor(ultimaCnh);

        } else {
            log.error("Ticket não encontrado.");
        }


    }

    private void regularizarCondutor(String ultimaCnh) {
        Condutor condutor = condutorRepository.findByCnh(ultimaCnh);

        if (condutor != null && Boolean.FALSE.equals(condutor.getClienteAtivo())) {
            condutor.setClienteAtivo(true);
            condutorRepository.save(condutor);

            log.info("Condutor regularizado: " + condutor);
        } else {
            log.error("Condutor não encontrado.");
        }
    }


    public void multarVeiculo(String placa) {
        List<Ticket> ticketList = repository.findByPlaca(placa);

        if (!ticketList.isEmpty()) {
            Ticket ultimoTicket = ticketList.get(ticketList.size() - 1);
            bloquearCondutor(ultimoTicket);
        } else {
            log.error("Nenhum ticket encontrado para a placa fornecida.");
        }
    }

    private void bloquearCondutor(Ticket ultimoTicket) {
        Condutor condutor = condutorRepository.findByCnh(ultimoTicket.getUltimaCnh());
        condutor.setClienteAtivo(false);
        condutorRepository.save(condutor);

        log.info("Multa para a placa: "+ultimoTicket.getPlaca()+". Condutor bloqueado até a regularização!");
    }
}
