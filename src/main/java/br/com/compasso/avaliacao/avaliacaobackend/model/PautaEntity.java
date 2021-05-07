package br.com.compasso.avaliacao.avaliacaobackend.model;

import org.springframework.data.annotation.Id;

import java.util.HashMap;

/**
 * Classe da entidade Pauta
 * Chave primaria {String id}
 */
public class PautaEntity {

    @Id
    private String id;
    private String titulo;
    private String descricao;
    private HashMap<String, String> sessoes;// K = sessaoID, V = resultado da votacao
    private int max_sessoes = 1;

    public PautaEntity() {
        this.sessoes = new HashMap<>();
    }

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

    public String getId() {
        return this.id;
    }

    public HashMap<String, String> getSessoes() {
        return this.sessoes;
    }

    public void setSessoes(HashMap<String, String> sessoes) {
        this.sessoes = sessoes;
    }
}
