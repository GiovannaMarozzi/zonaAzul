package com.tech.challenge.zonaAzul.vaga.controller;

import com.tech.challenge.util.exception.vaga.VagaAlreadyExistsException;
import com.tech.challenge.util.exception.vaga.VagaNoSurchExistsException;
import com.tech.challenge.zonaAzul.vaga.dto.VagaRecord;
import com.tech.challenge.zonaAzul.vaga.form.VagaForm;
import com.tech.challenge.zonaAzul.vaga.model.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vagas")
public class vagaController {

    @Autowired
    private VagaService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<VagaRecord> adicionarNovaVaga(@RequestBody VagaForm form) throws VagaAlreadyExistsException {
        ResponseEntity response = null;
        try {
            VagaRecord vagaRecord = service.cadastrar(form);
            response = ResponseEntity.status(HttpStatus.CREATED).body(vagaRecord);
        } catch (VagaAlreadyExistsException alreadyExists) {
            response = ResponseEntity.status(HttpStatus.CONFLICT).body(alreadyExists.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @GetMapping()
    public ResponseEntity<List<VagaRecord>> vagas() throws VagaNoSurchExistsException {
        ResponseEntity response = null;
        try {
            List<VagaRecord> vagaRecords = service.vagas();
            response = ResponseEntity.status(HttpStatus.CREATED).body(vagaRecords);
        } catch (VagaNoSurchExistsException noSurchExists) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSurchExists.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @GetMapping("/local={local}")
    public ResponseEntity<VagaRecord> vagaEspecifica(@PathVariable String local) throws VagaNoSurchExistsException {
        ResponseEntity response = null;

        try {
            VagaRecord vagaRecords = service.vagasPorLocal(local);
            response = ResponseEntity.status(HttpStatus.CREATED).body(vagaRecords);
        } catch (VagaNoSurchExistsException noSurchExists) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSurchExists.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @PutMapping("/editar={id}")
    public ResponseEntity<VagaRecord> editarVaga(@PathVariable String id, @RequestBody VagaForm vagaForm) throws VagaNoSurchExistsException {
        ResponseEntity response = null;

        try {
            VagaRecord vagaRecord = service.editarVaga(id, vagaForm);
            response = ResponseEntity.status(HttpStatus.CREATED).body(vagaRecord);
        } catch (VagaNoSurchExistsException noSurchExists) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSurchExists.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/deletar={id}")
    public ResponseEntity editarVaga(@PathVariable String id) throws VagaNoSurchExistsException {
        ResponseEntity response = null;

        try {
            service.deletar(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
        } catch (VagaNoSurchExistsException noSurchExists) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSurchExists.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

}
