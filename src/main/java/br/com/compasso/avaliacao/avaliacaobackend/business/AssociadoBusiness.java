package br.com.compasso.avaliacao.avaliacaobackend.business;

import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;

public class AssociadoBusiness {

    public boolean hasVoted(SessaoEntity sessao, AssociadoEntity entity) {
        return entity.getSessoes().containsKey(sessao.getId());
    }

}
