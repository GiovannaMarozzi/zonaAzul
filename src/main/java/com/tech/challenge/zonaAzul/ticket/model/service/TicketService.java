package com.tech.challenge.zonaAzul.ticket.model.service;

import com.tech.challenge.util.exception.condutor.InsufficientFundsException;
import com.tech.challenge.util.exception.condutor.NoSuchRecordException;
import com.tech.challenge.util.exception.ticket.DriverAlreadyRegularizedException;
import com.tech.challenge.util.exception.ticket.TicketNotFoundException;
import com.tech.challenge.util.exception.ticket.UnregularizedTicketException;
import com.tech.challenge.util.exception.ticket.VehicleNotFoundException;
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

    public TicketRecord novoTicket(TicketForm ticketForm) throws UnregularizedTicketException, VehicleNotFoundException, InsufficientFundsException, NoSuchRecordException {
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
                throw new UnregularizedTicketException("Cliente irregular, favor regularizar o ticket");
            }
        }else{
            log.error("nenhum veiculo encontrado para a cnh: "+ticketForm.getCnh());
            throw new VehicleNotFoundException("Nenhum veiculo encontrado para esta cnh: "+ticketForm.getCnh());
        }
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

    public void regularizarTicket(String id) throws TicketNotFoundException, NoSuchRecordException, DriverAlreadyRegularizedException {
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
            throw new TicketNotFoundException("Ticket não encontrado!");
        }
    }

    private void regularizarCondutor(String ultimaCnh) throws NoSuchRecordException, DriverAlreadyRegularizedException {
        Condutor condutor = condutorRepository.findByCnh(ultimaCnh);

        if (condutor != null) {
            if (Boolean.FALSE.equals(condutor.getClienteAtivo())){
                condutor.setClienteAtivo(true);
                condutorRepository.save(condutor);

                log.info("Condutor regularizado: " + condutor);
            }else {
                log.error("Condutor já regularizado.");
                throw new DriverAlreadyRegularizedException("Condutor já regularizado!");
            }

        } else {
            log.error("Condutor não encontrado.");
            throw new NoSuchRecordException("Condutor não encontrado!");
        }
    }


    public void multarVeiculo(String placa) throws TicketNotFoundException {
        List<Ticket> ticketList = repository.findByPlaca(placa);

        if (!ticketList.isEmpty()) {
            Ticket ultimoTicket = ticketList.get(ticketList.size() - 1);
            condutorService.bloquearCondutor(ultimoTicket);
        } else {
            log.error("Nenhum ticket encontrado para a placa fornecida.");
            throw new TicketNotFoundException("Nenhum ticket encontrado para a placa fornecida.");
        }
    }
}
