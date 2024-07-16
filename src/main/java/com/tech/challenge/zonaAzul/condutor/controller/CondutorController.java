package com.tech.challenge.zonaAzul.condutor.controller;

import com.tech.challenge.zonaAzul.condutor.dto.CondutorRecod;
import com.tech.challenge.zonaAzul.condutor.form.CondutorForm;
import com.tech.challenge.zonaAzul.condutor.model.service.CondutorService;
import com.tech.challenge.zonaAzul.util.exception.condutor.ConductorAlreadyExistsException;
import com.tech.challenge.zonaAzul.util.exception.condutor.NoSuchRecordException;
import com.tech.challenge.zonaAzul.util.exception.veiculo.VeiculoAlreadyExistsException;
import com.tech.challenge.zonaAzul.util.exception.veiculo.VeiculoNoDriverExistsException;
import com.tech.challenge.zonaAzul.veiculo.form.VeiculoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/condutor")
public class CondutorController {

    @Autowired
    private CondutorService service;


    @PostMapping("/salvar")
    public ResponseEntity<CondutorRecod> salvar(@RequestBody CondutorForm condutor){
        ResponseEntity response = null;

        try {
            CondutorRecod condutorRecod = service.salvar(condutor);
            response = ResponseEntity.status(HttpStatus.CREATED).body(condutorRecod);
        }catch (ConductorAlreadyExistsException alreadyExists){
            response = ResponseEntity.status(HttpStatus.CONFLICT).body(alreadyExists.getMessage());
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @GetMapping("/condutores")
    public ResponseEntity<List<CondutorRecod>> condutores(){
        ResponseEntity response = null;

        try {
            List<CondutorRecod> condutorRecod = service.condutores();
            response = ResponseEntity.status(HttpStatus.OK).body(condutorRecod);
        }catch (NoSuchRecordException noSuch){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSuch.getMessage());
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<CondutorRecod> condutor(@PathVariable String cpf) throws NoSuchRecordException{
        ResponseEntity response = null;

        try {
            CondutorRecod condutor = service.condutor(cpf);
            response = ResponseEntity.status(HttpStatus.OK).body(condutor);
        }catch (NoSuchRecordException noSuch){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSuch.getMessage());
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @PutMapping("/editar")
    public ResponseEntity<CondutorRecod> editarCondutor(@RequestBody CondutorForm condutorForm) throws ConductorAlreadyExistsException{
        ResponseEntity response = null;

        try {
            CondutorRecod condutor = service.editarDadosCondutor(condutorForm);
            response = ResponseEntity.status(HttpStatus.OK).body(condutor);
        }catch (NoSuchRecordException noSuch){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSuch.getMessage());
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @DeleteMapping("deletar={cpf}")
    public ResponseEntity deletarCondutor(@PathVariable String cpf) throws NoSuchRecordException {

        try{
            service.deletarCondutor(cpf);
            return ResponseEntity.ok("Cadastro deletado com sucesso!");
        }catch (NoSuchRecordException noSuch){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSuch.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/cpf={cpf}/adicionarVeiculo")
    public ResponseEntity adicionarNovoVeiculo(@PathVariable String cpf, @RequestBody VeiculoForm veiculoForm, @RequestParam Boolean condutorPrincipal) throws VeiculoAlreadyExistsException, VeiculoNoDriverExistsException {

        try {
            service.adicionarNovoVeiculo(cpf, condutorPrincipal, veiculoForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Veículo cadastrado com sucesso para o CPF: "+cpf);
        }catch (VeiculoAlreadyExistsException alreadyExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(alreadyExists.getMessage());
        }catch (VeiculoNoDriverExistsException noDriver){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(noDriver.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
