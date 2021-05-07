package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.business.SessaoBusiness;
import br.com.compasso.avaliacao.avaliacaobackend.dto.SessaoEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.SessaoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessaoService {

    private SessaoRepository repository;

    public SessaoService(SessaoRepository repo) {
        this.repository = repo;
    }

    @CacheEvict(value = "sessoes", allEntries = true)
    public void deleteSessao(String id) {
        repository.deleteById(id);
    }

    @Cacheable(value = "sessoes", key = "#id")
    public SessaoEntity getSessaoPorId(String id) {
        return repository.findById(id).get();
    }

    @CacheEvict(value = "sessoes", allEntries = true)
    public SessaoEntity editSessao(String id, SessaoEntity sessaoNova) {
        SessaoEntity sessaoAntiga = getSessaoPorId(id);
        if (sessaoNova.getVotos() != null) {
            sessaoAntiga.setVotos(sessaoNova.getVotos());
        }
        if (sessaoNova.isAberta() != sessaoAntiga.isAberta()) {
            sessaoAntiga.setAberta(sessaoNova.isAberta());
        }
        if (sessaoNova.getPautaEmQuestao() != null) {
            sessaoAntiga.setPautaEmQuestao(sessaoNova.getPautaEmQuestao());
        }
        return save(sessaoAntiga);
    }

    @Cacheable(value = "sessoes")
    public List<SessaoEntity> getAll() {
        return repository.findAll();
    }


    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    public SessaoEntity save(SessaoEntity entity) {
        return repository.save(entity);
    }

}
