package br.com.compasso.avaliacao.avaliacaobackend.threads;

import br.com.compasso.avaliacao.avaliacaobackend.business.SessaoBusiness;
import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.services.PautaService;
import br.com.compasso.avaliacao.avaliacaobackend.services.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

public class SessaoThread extends Thread {

    private SessaoEntity entity;
    private int tempo;
    private SessaoService service;
    private PautaService pautaService;
    //
    private SessaoBusiness business;

    public SessaoThread(SessaoEntity entity, int tempo, SessaoService service, PautaService pautaService) {
        super();
        this.entity = entity;
        this.tempo = tempo;
        business = new SessaoBusiness();
        this.service = service;
        this.pautaService = pautaService;
        this.start();
    }

    public void run() {
        try {
            this.sleep(1000 * 60 * tempo);
            entity = service.getSessaoPorId(entity.getId());
            entity.setAberta(false);
            if (entity.getVotos().size() > 0) {
                PautaEntity ent = entity.getPautaEmQuestao();
                HashMap<String, String> sessoes = ent.getSessoes();
                sessoes.put(entity.getId(), business.resultadoDaVotacao(entity));
                pautaService.editPauta(ent.getId(), ent);
            }
            service.editSessao(entity.getId(), entity);
            System.out.println("Sessão finalizada com sucesso!");
        } catch (InterruptedException e) {
            entity.setAberta(false);
            service.editSessao(entity.getId(), entity);
            System.out.println("Sessão finalizada misteriosamente!");
        }
    }
}
