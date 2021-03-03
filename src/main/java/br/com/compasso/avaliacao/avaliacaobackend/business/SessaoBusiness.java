package br.com.compasso.avaliacao.avaliacaobackend.business;

import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;

public class SessaoBusiness {

    private int sim;
    private int nao;

    public String resultadoDaVotacao(SessaoEntity entity) {
        sim = 0;
        nao = 0;
        entity.getVotos().values().forEach((voto) -> {
            if (voto.equalsIgnoreCase("Sim")) {
                sim++;
            }
            if (voto.equalsIgnoreCase("Não")) {
                nao++;
            }
        });
        return sim > nao ? "Votação Aprovada com " + sim + " votos" : "Votação Reprovada com " + nao + " votos";
    }

}
