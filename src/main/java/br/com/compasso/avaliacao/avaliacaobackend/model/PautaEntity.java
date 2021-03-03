package br.com.compasso.avaliacao.avaliacaobackend.model;

import org.springframework.data.annotation.Id;

import java.util.HashMap;

public class PautaEntity {

    @Id
    private String id;
    private String titulo;
    private String descricao;
    private HashMap<String, String> sessoes;// K = sessaoID, V = resultado da votacao

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

    public PautaEntity() {
        this.sessoes = new HashMap<>();
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
