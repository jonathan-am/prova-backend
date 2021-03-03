package br.com.compasso.avaliacao.avaliacaobackend.controllers;

import br.com.compasso.avaliacao.avaliacaobackend.dto.SessaoEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.services.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class SessaoController {

    @Autowired
    private SessaoService service;

    @GetMapping("/sessoes/{id}")
    @ResponseBody
    public Collection<SessaoEntity> buscarTodosPorPautaId(@PathVariable String id) {
        return service.buscarTodosPorPautaId(id);
    }

    @PostMapping(value = "/iniciar/sessao", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> criar(@RequestBody SessaoEntradaDTO sessaoEntity) {
        SessaoEntity ent = service.startNewSessao(sessaoEntity);
        try {
            return ResponseEntity.ok(ent);
        } catch (NullPointerException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/remover/sessao/{id}")
    public void deletar(@PathVariable String id) {
        service.deleteSessao(id);
    }

    @GetMapping("/sessoes")
    @ResponseBody
    public List<SessaoEntity> buscarTodos() {
        return service.buscarTodos();
    }

}
