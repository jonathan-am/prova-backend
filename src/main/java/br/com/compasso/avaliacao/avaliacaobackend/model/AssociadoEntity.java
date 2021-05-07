package br.com.compasso.avaliacao.avaliacaobackend.model;

import org.springframework.data.annotation.Id;

import java.util.HashMap;

/**
 * Classe da entidade Associado
 */
public class AssociadoEntity {

    @Id
    private String id;
    private String nome;
    private String cpf;
    private HashMap<String, String> sessoes;// K = sessaoID, V = voto

    public AssociadoEntity() {
        this.sessoes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public HashMap<String, String> getSessoes() {
        return sessoes;
    }

    public void setSessoes(HashMap<String, String> sessoes) {
        this.sessoes = sessoes;
    }
}
