package br.com.compasso.avaliacao.avaliacaobackend.dto;

public class SessaoEntradaDTO {

    private String pautaId;
    private int tempo;

    public String getPautaId() {
        return pautaId;
    }

    public void setPautaId(String pautaId) {
        this.pautaId = pautaId;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
}
