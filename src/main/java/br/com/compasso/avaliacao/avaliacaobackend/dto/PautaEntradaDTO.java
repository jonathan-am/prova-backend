package br.com.compasso.avaliacao.avaliacaobackend.dto;

import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;

/**
 * Classe DTO para entrada de dados, da entidade Pauta
 */
public class PautaEntradaDTO {

    private String titulo;
    private String descricao;
    private int max_sessoes;

    public int getMax_sessoes() {
        return max_sessoes;
    }

    public void setMax_sessoes(int max_sessoes) {
        this.max_sessoes = max_sessoes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PautaEntity dtoToEntity() {
        PautaEntity novo = new PautaEntity();
        novo.setDescricao(getDescricao());
        novo.setTitulo(getTitulo());
        novo.setMax_sessoes(getMax_sessoes());
        return novo;
    }
}
