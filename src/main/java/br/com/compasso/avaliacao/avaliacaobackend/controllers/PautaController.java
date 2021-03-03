package br.com.compasso.avaliacao.avaliacaobackend.controllers;

import br.com.compasso.avaliacao.avaliacaobackend.dto.PautaEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import br.com.compasso.avaliacao.avaliacaobackend.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PautaController {

    @Autowired
    private PautaService service;

    @PostMapping(value = "/criar/pauta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> criar(@RequestBody PautaEntradaDTO entity) {
        return ResponseEntity.ok(service.createNewPauta(entity));
    }

    @DeleteMapping("/remover/pauta/{id}")
    public void deletar(@PathVariable String id) {
        service.deletePauta(id);
    }

    @GetMapping("/pautas")
    @ResponseBody
    public List<PautaEntity> buscarTodos() {
        return service.getPautas();
    }

    @PutMapping("/editar/pauta/{id}")
    public PautaEntity editar(@PathVariable String id, @RequestBody PautaEntity pauta) {
        return service.editPauta(id, pauta);
    }
}
