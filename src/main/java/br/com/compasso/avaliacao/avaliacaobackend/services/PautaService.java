package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.dto.PautaEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.PautaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {

    private PautaRepository repository;

    public PautaService(PautaRepository repo) {
        repository = repo;
    }

    public PautaEntity createNewPauta(PautaEntradaDTO entity) {
        PautaEntity novo = new PautaEntity();
        novo.setDescricao(entity.getDescricao());
        novo.setTitulo(entity.getTitulo());
        return repository.save(novo);
    }

    public void deletePauta(String id) {
        repository.deleteById(id);
    }

    public PautaEntity getPautaPorId(String id) {
        return repository.findById(id).get();
    }

    public List<PautaEntity> getPautas() {
        return repository.findAll();
    }

    public PautaEntity editPauta(String id, PautaEntity pautaNova) {
        PautaEntity pautaAntiga = getPautaPorId(id);
        if (pautaNova.getSessoes() != null) {
            pautaAntiga.setSessoes(pautaNova.getSessoes());
        }
        if (pautaNova.getTitulo() != null) {
            pautaAntiga.setTitulo(pautaNova.getTitulo());
        }
        if (pautaNova.getDescricao() != null) {
            pautaAntiga.setDescricao(pautaNova.getDescricao());
        }
        return repository.save(pautaAntiga);
    }

    public boolean existById(String id) {
        return repository.existsById(id);
    }


}
