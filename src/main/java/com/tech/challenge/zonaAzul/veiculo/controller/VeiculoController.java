package com.tech.challenge.zonaAzul.veiculo.controller;

import com.tech.challenge.zonaAzul.veiculo.dto.VeiculoRecord;
import com.tech.challenge.zonaAzul.veiculo.form.VeiculoForm;
import com.tech.challenge.zonaAzul.veiculo.model.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/veiculo")
public class VeiculoController {

    @Autowired
    VeiculoService service;


    @GetMapping("/todos")
    public ResponseEntity<List<VeiculoRecord>> todosVeiculos() {
        ResponseEntity response = null;

        List<VeiculoRecord> veiculos =  service.veiculos();
        response = ResponseEntity.status(HttpStatus.OK).body(veiculos);

        return response;
    }

    @GetMapping
    public ResponseEntity<VeiculoRecord> todosVeiculos(@RequestParam String placa) {
        ResponseEntity response = null;

        VeiculoRecord veiculos =  service.veiculoPorPlaca(placa);
        response = ResponseEntity.status(HttpStatus.OK).body(veiculos);

        return response;
    }

    @PutMapping("/editar")
    public ResponseEntity<VeiculoRecord> editarVeiculo(@RequestBody VeiculoForm veiculoForm){
        ResponseEntity response = null;

        VeiculoRecord veiculoEditado = service.editarVeiculo(veiculoForm);
        response = ResponseEntity.status(HttpStatus.OK).body(veiculoEditado);

        return response;
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<VeiculoRecord> editarVeiculo(@RequestParam String placa) {
        ResponseEntity response = null;

        service.deletarVeiculoPorPlaca(placa);
        response = ResponseEntity.status(HttpStatus.OK).body("Veiculo de placa "+placa+" deletado com sucesso");

        return response;
    }

}
