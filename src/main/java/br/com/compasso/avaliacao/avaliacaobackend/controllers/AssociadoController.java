package br.com.compasso.avaliacao.avaliacaobackend.controllers;

import br.com.compasso.avaliacao.avaliacaobackend.dto.*;
import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.services.AssociadoService;
import br.com.compasso.avaliacao.avaliacaobackend.services.SessaoService;
import br.com.compasso.avaliacao.avaliacaobackend.services.ValidaCpfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AssociadoController {

    @Autowired
    private AssociadoService service;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private ValidaCpfService validaService;

    @PostMapping("/criar/associado")
    @ResponseBody
    public AssociadoSaidaDTO criar(@RequestBody AssociadoEntradaDTO entity) {
        return service.createNewAssociado(entity);
    }

    @DeleteMapping("/remover/associado/{id}")
    public void deletar(@PathVariable String id) {
        service.deleteAssociado(id);
    }

    @GetMapping("/associados")
    @ResponseBody
    public List<AssociadoSaidaDTO> buscarTodos() {
        return service.getAssociados();
    }

    @PutMapping("/editar/associado/{id}")
    public AssociadoSaidaDTO editar(@PathVariable String id, @RequestBody AssociadoEntity associado) {
        return service.editAssociado(id, associado);
    }

    // Voto Por Cpf = v1
    @PutMapping("/votar/associado/v1/{id}")
    public ResponseEntity<?> votarNaSessaoPeloCpf(@PathVariable String id, @RequestBody VotoEntradaPorCpfDTO voto) {
        AssociadoEntity associado = service.buscaPorCpf(voto.getCpf());
        if (validaService.check(associado.getCpf()) != null) {
            CpfStatusDTO statusDTO = validaService.check(associado.getCpf());
            if (statusDTO.getStatus().equals("ABLE_TO_VOTE")) {
                SessaoEntity ent = sessaoService.computaVoto(id, voto.getVotoDto(), associado);
                if (ent == null) {
                    statusDTO.setStatus("UNABLE_TO_VOTE");
                }
            }
            return ResponseEntity.ok(statusDTO);
        }
        return ResponseEntity.notFound().build();
    }

    // Voto Por Id = v2
    @PutMapping("/votar/associado/v2/{id}")
    public ResponseEntity<?> votarNaSessaoPeloId(@PathVariable String id, @RequestBody VotoEntradaPorIdDTO voto) {
        AssociadoEntity associado = service.buscaPorId(voto.getId());
        if (validaService.check(associado.getCpf()) != null) {
            CpfStatusDTO statusDTO = validaService.check(associado.getCpf());
            if (statusDTO.getStatus().equals("ABLE_TO_VOTE")) {
                SessaoEntity ent = sessaoService.computaVoto(id, voto.getVotoDto(), associado);
                if (ent == null) {
                    statusDTO.setStatus("UNABLE_TO_VOTE");
                }
            }
            return ResponseEntity.ok(statusDTO);
        }
        return ResponseEntity.notFound().build();
    }
}
