package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.business.AssociadoBusiness;
import br.com.compasso.avaliacao.avaliacaobackend.dto.SessaoEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.dto.VotoDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.SessaoRepository;
import br.com.compasso.avaliacao.avaliacaobackend.threads.SessaoThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class SessaoService {

    private SessaoRepository repository;
    private PautaService pautaService;
    private AssociadoService associadoService;
    private AssociadoBusiness business;

    public SessaoService(SessaoRepository repo, PautaService pautaService, AssociadoService associadoService) {
        repository = repo;
        business = new AssociadoBusiness();
        this.pautaService = pautaService;
        this.associadoService = associadoService;
    }

    public SessaoEntity startNewSessao(SessaoEntradaDTO entity) {
        if (pautaService.existById(entity.getPautaId())) {
            PautaEntity pauta = pautaService.getPautaPorId(entity.getPautaId());
            SessaoEntity novaEntidade = new SessaoEntity();
            novaEntidade.setTempo(entity.getTempo());
            novaEntidade.setPautaEmQuestao(pauta);
            SessaoEntity ent = repository.save(novaEntidade);
            //
            //Inicia a Thread da sessao
            SessaoThread sessaoThread = new SessaoThread(ent, entity.getTempo(), this, pautaService);
            return ent;
        }
        return null;
    }

    public void deleteSessao(String id) {
        HashMap<String, String> sessoesOld = pautaService.getPautaPorId(id).getSessoes();
        sessoesOld.remove(id);
        //
        getSessaoPorId(id).getPautaEmQuestao().setSessoes(sessoesOld);
        pautaService.editPauta(getSessaoPorId(id).getPautaEmQuestao().getId(), getSessaoPorId(id).getPautaEmQuestao());
        repository.deleteById(id);
    }

    public SessaoEntity getSessaoPorId(String id) {
        return repository.findById(id).get();
    }

    public SessaoEntity computaVoto(String id, VotoDTO voto, AssociadoEntity entidade) {
        if (existeSessaoPorId(id)) {
            if (voto.getVoto().equalsIgnoreCase("Sim") || voto.getVoto().equalsIgnoreCase("Não")) {
                SessaoEntity ent = getSessaoPorId(id);
                if (!business.hasVoted(ent, entidade)) {
                    if (ent.isAberta()) {
                        HashMap<String, String> votos = ent.getVotos();
                        votos.put(entidade.getId(), voto.getVoto());
                        ent.setVotos(votos);
                        associadoService.computaVotoParaOAssociado(entidade, voto.getVoto(), ent);
                        return repository.save(ent);
                    }
                }
            }
        }
        return null;
    }

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
        return repository.save(sessaoAntiga);
    }

    public List<SessaoEntity> buscarTodos() {
        return repository.findAll();
    }

    public List<SessaoEntity> buscarTodosPorPautaId(String id) {
        List<SessaoEntity> sessoes = new ArrayList<>();
        pautaService.getPautaPorId(id).getSessoes().keySet().forEach((sessao_id) -> {
            sessoes.add(getSessaoPorId(sessao_id));
        });
        return sessoes;
    }

    private boolean existeSessaoPorId(String id) {
        return repository.existsById(id);
    }

}
