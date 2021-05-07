package br.com.compasso.avaliacao.avaliacaobackend.controllers;

import br.com.compasso.avaliacao.avaliacaobackend.business.AssociadoBusiness;
import br.com.compasso.avaliacao.avaliacaobackend.dto.*;
import br.com.compasso.avaliacao.avaliacaobackend.services.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe controller da entidade Associado
 */
@RestController
public class AssociadoController {

    @Autowired
    private AssociadoService service;
    @Autowired
    private AssociadoBusiness business;


    @PostMapping("/associado")
    @ResponseBody
    public AssociadoSaidaDTO criar(@RequestBody AssociadoEntradaDTO entity) {
        return service.createNewAssociado(entity.dtoToEntity());
    }

    @DeleteMapping("/associado/{id}")
    public void deletar(@PathVariable String id) {
        service.deleteAssociado(id);
    }

    @GetMapping("/associado")
    @ResponseBody
    public List<AssociadoSaidaDTO> buscarTodos() {
        return service.getAssociados();
    }

    @PutMapping("/associado/{id}")
    public AssociadoSaidaDTO editar(@PathVariable String id, @RequestBody AssociadoEntradaDTO associado) {
        return service.editAssociado(id, associado);
    }

    // Voto Por Cpf = v1
    @PutMapping("/associado/v1/{id}")
    public ResponseEntity<?> votarNaSessaoPeloCpf(@PathVariable String id, @RequestBody VotoEntradaPorCpfDTO voto) {
        return business.checkEComputaVoto(id, voto.getVotoDto(), service.getByCpf(voto.getCpf()));
    }

    // Voto Por Id = v2
    @PutMapping("/associado/v2/{id}")
    public ResponseEntity<?> votarNaSessaoPeloId(@PathVariable String id, @RequestBody VotoEntradaPorIdDTO voto) {
        return business.checkEComputaVoto(id, voto.getVotoDto(), service.getById(voto.getId()));
    }

}
