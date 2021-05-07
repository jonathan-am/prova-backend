package br.com.compasso.avaliacao.avaliacaobackend.controllers;

import br.com.compasso.avaliacao.avaliacaobackend.business.SessaoBusiness;
import br.com.compasso.avaliacao.avaliacaobackend.dto.SessaoEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.services.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Classe Controller da entidade Sessao
 */
@RestController
public class SessaoController {

    @Autowired
    private SessaoService service;
    @Autowired
    private SessaoBusiness business;


    @GetMapping("/sessao/{id}")
    @ResponseBody
    public Collection<SessaoEntity> buscarTodosPorPautaId(@PathVariable String id) {
        return business.buscarTodosPorPautaId(id);
    }

    @PostMapping(value = "/sessao", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> criar(@RequestBody SessaoEntradaDTO sessaoEntity) {
        SessaoEntity ent = business.startNewSessao(sessaoEntity);
        try {
            return ResponseEntity.ok(ent);
        } catch (NullPointerException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/sessao/{id}")
    public void deletar(@PathVariable String id) {
        business.deleteSessao(id);
    }

    @GetMapping("/sessao")
    @ResponseBody
    public List<SessaoEntity> buscarTodos() {
        return service.getAll();
    }

}
