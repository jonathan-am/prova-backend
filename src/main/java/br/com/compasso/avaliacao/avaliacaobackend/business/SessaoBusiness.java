package br.com.compasso.avaliacao.avaliacaobackend.business;

import br.com.compasso.avaliacao.avaliacaobackend.dto.SessaoEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.dto.VotoDTO;
import br.com.compasso.avaliacao.avaliacaobackend.exception.ComputarVotoException;
import br.com.compasso.avaliacao.avaliacaobackend.exception.IniciarSessaoException;
import br.com.compasso.avaliacao.avaliacaobackend.exception.LimiteSessoesException;
import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.services.PautaService;
import br.com.compasso.avaliacao.avaliacaobackend.services.SessaoService;
import br.com.compasso.avaliacao.avaliacaobackend.threads.SessaoThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe de Business da entidade Sessao
 * contendo metodos de negocio
 */
@Component
public class SessaoBusiness {

    private SessaoService service;
    private PautaService pautaService;
    @Autowired
    private AssociadoBusiness associadoBusiness;

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoService.class);

    private int sim;
    private int nao;

    public SessaoBusiness(SessaoService service, PautaService pautaService) {
        this.service = service;
        this.pautaService = pautaService;
    }

    /**
     * Metodo que calcula os votos, e devolve uma mensagem com o resultado da votação.
     *
     * @param entity Sessao em questão que sera, calculado os votos
     * @return Mensagem com o resultado da votação
     */
    public String resultadoDaVotacao(SessaoEntity entity) {
        sim = 0;
        nao = 0;
        if (entity.getVotos() != null) {
            entity.getVotos().values().forEach((voto) -> {
                if (voto.equalsIgnoreCase("Sim")) {
                    sim++;
                }
                if (voto.equalsIgnoreCase("Não")) {
                    nao++;
                }
            });
        }
        if (sim == nao) {
            return "Votação finalizada com " + sim + " votos Sim, e " + nao + " votos Não.";
        }
        return sim > nao ? "Votação Aprovada com " + sim + " votos" : "Votação Reprovada com " + nao + " votos";
    }

    @CacheEvict(value = "sessoes", allEntries = true)
    public SessaoEntity computaVoto(String id, VotoDTO voto, AssociadoEntity entidade) {
        if (service.existsById(id)) {
            if (voto.getVoto().equalsIgnoreCase("Sim") || voto.getVoto().equalsIgnoreCase("Não")) {
                SessaoEntity sessao = service.getSessaoPorId(id);
                if (!associadoBusiness.hasVoted(sessao, entidade)) {
                    if (sessao.isAberta()) {
                        HashMap<String, String> votos = sessao.getVotos();
                        votos.put(entidade.getId(), voto.getVoto());
                        sessao.setVotos(votos);
                        associadoBusiness.computaVotoParaOAssociado(entidade, voto.getVoto(), sessao);
                        return service.save(sessao);
                    }
                }
            }
        }
        throw new ComputarVotoException("Não foi possivel computar o voto na sessão.");
    }

    @CacheEvict(value = "sessoes", allEntries = true)
    public void deleteSessao(String id) {
        HashMap<String, String> sessoesOld = pautaService.getPautaPorId(id).getSessoes();
        sessoesOld.remove(id);
        //
        service.getSessaoPorId(id).getPautaEmQuestao().setSessoes(sessoesOld);
        pautaService.editPauta(service.getSessaoPorId(id).getPautaEmQuestao().getId(), service.getSessaoPorId(id).getPautaEmQuestao());
        service.deleteSessao(id);
    }

    public List<SessaoEntity> buscarTodosPorPautaId(String id) {
        ArrayList<SessaoEntity> lista = new ArrayList<>();
        service.getAll().forEach((value) -> {
            if (value.getPautaEmQuestao().getId().equals(id)) {
                lista.add(value);
            }
        });
        return lista;
    }

    @CacheEvict(value = "sessoes", allEntries = true)
    public SessaoEntity startNewSessao(SessaoEntradaDTO entity) {
        if (pautaService.existById(entity.getPautaId())) {
            PautaEntity pauta = pautaService.getPautaPorId(entity.getPautaId());
//           //Regra adicionada para limitar as sessoes por pauta - 05/03
            if (pauta.getMax_sessoes() > pauta.getSessoes().size()) {
                SessaoEntity novaEntidade = new SessaoEntity();
                novaEntidade.setTempo(entity.getTempo());
                novaEntidade.setPautaEmQuestao(pauta);
                SessaoEntity ent = service.save(novaEntidade);
                //
                pauta.getSessoes().put(ent.getId(), "Não finalizado");
                pautaService.editPauta(pauta.getId(), pauta);
                //
                //Inicia a Thread da sessao
                new SessaoThread(ent, entity.getTempo(), service, pautaService, associadoBusiness);
                LOGGER.info("Sessão iniciada, ID: " + ent.getId());
                return ent;
            } else {
                throw new LimiteSessoesException("Limite de sessões excedida nesta pauta.");
            }
        }
        throw new IniciarSessaoException("Não foi possivel iniciar a sessão.");
    }

}
