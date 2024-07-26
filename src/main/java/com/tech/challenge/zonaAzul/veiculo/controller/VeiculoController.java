package com.tech.challenge.zonaAzul.veiculo.controller;

import com.tech.challenge.util.exception.veiculo.VeiculoNoSurchExistsException;
import com.tech.challenge.zonaAzul.veiculo.dto.VeiculoRecord;
import com.tech.challenge.zonaAzul.veiculo.form.VeiculoForm;
import com.tech.challenge.zonaAzul.veiculo.model.service.VeiculoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/veiculo")
public class VeiculoController {

    private static final Logger log = LoggerFactory.getLogger(VeiculoController.class);
    @Autowired
    VeiculoService service;


    @GetMapping("/todos")
    public ResponseEntity<List<VeiculoRecord>> todosVeiculos() throws VeiculoNoSurchExistsException {
        ResponseEntity response = null;

        try {
            List<VeiculoRecord> veiculos = service.veiculos();
            response = ResponseEntity.status(HttpStatus.OK).body(veiculos);
        } catch (VeiculoNoSurchExistsException noSurch) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(noSurch.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @GetMapping
    public ResponseEntity<VeiculoRecord> todosVeiculos(@RequestParam String placa) throws VeiculoNoSurchExistsException {
        ResponseEntity response = null;

        try {
            VeiculoRecord veiculos = service.veiculoPorPlaca(placa);
            response = ResponseEntity.status(HttpStatus.OK).body(veiculos);
        }catch (VeiculoNoSurchExistsException veiculonoSurch){
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body(veiculonoSurch.getMessage());
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @PutMapping("/editar")
    public ResponseEntity<VeiculoRecord> editarVeiculo(@RequestBody VeiculoForm veiculoForm) throws VeiculoNoSurchExistsException {
        ResponseEntity response = null;

        try {
            VeiculoRecord veiculoEditado = service.editarVeiculo(veiculoForm);
            response = ResponseEntity.status(HttpStatus.OK).body(veiculoEditado);

        }catch (VeiculoNoSurchExistsException veiculonoSurch){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(veiculonoSurch.getMessage());
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<VeiculoRecord> editarVeiculo(@RequestParam String placa) {
        ResponseEntity response = null;

        try {
            service.deletarVeiculoPorPlaca(placa);
            response = ResponseEntity.status(HttpStatus.OK).body("Veiculo de placa " + placa + " deletado com sucesso!");
        }catch (VeiculoNoSurchExistsException veiculonoSurch){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(veiculonoSurch.getMessage());
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return response;
    }

}
