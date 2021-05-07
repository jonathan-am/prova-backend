package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.dto.PautaEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.exception.NaoExisteException;
import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.PautaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PautaService {

    private PautaRepository repository;

    public PautaService(PautaRepository repo) {
        repository = repo;
    }

    @CacheEvict(value = "pautas", allEntries = true)
    public PautaEntity createNewPauta(PautaEntity entity) {
        return repository.save(entity);
    }

    @CacheEvict(value = "pautas", allEntries = true)
    public void deletePauta(String id) {
        repository.deleteById(id);
    }

    @Cacheable(value = "pautas", key = "#id")
    public PautaEntity getPautaPorId(String id) {
        return repository.findById(id).orElseThrow(() -> new NaoExisteException("Não foi possivel encontrar a entidade."));
    }

    @Cacheable(value = "pautas")
    public List<PautaEntity> getPautas() {
        return repository.findAll();
    }

    @CacheEvict(value = "pautas", allEntries = true)
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
        if (pautaNova.getMax_sessoes() != 0) {
            pautaAntiga.setMax_sessoes(pautaNova.getMax_sessoes());
        }
        return repository.save(pautaAntiga);
    }

    public boolean existById(String id) {
        return repository.existsById(id);
    }


}
