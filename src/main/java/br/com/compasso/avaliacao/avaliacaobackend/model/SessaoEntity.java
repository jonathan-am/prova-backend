package br.com.compasso.avaliacao.avaliacaobackend.model;

import org.springframework.data.annotation.Id;

import java.util.HashMap;

/**
 * Classe Sessao
 */
public class SessaoEntity {

    @Id
    private String id;
    private PautaEntity pautaEmQuestao;
    private HashMap<String, String> votos;// K = AssociadoID, V = voto
    private int tempo = 1;
    private boolean aberta = true;

    public SessaoEntity() {
        this.votos = new HashMap<>();
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public String getId() {
        return id;
    }

    public boolean isAberta() {
        return aberta;
    }

    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }

    public PautaEntity getPautaEmQuestao() {
        return pautaEmQuestao;
    }

    public void setPautaEmQuestao(PautaEntity pautaEmQuestao) {
        this.pautaEmQuestao = pautaEmQuestao;
    }

    public HashMap<String, String> getVotos() {
        return votos;
    }

    public void setVotos(HashMap<String, String> votos) {
        this.votos = votos;
    }
}
