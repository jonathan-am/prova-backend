package br.com.compasso.avaliacao.avaliacaobackend.dto;

import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;

import java.util.HashMap;

public class AssociadoSaidaDTO {

    private String id;
    private String nome;
    private HashMap<String, String> sessoesVotadas;

    public AssociadoSaidaDTO(String id, String nome, HashMap<String, String> sessoesVotadas) {
        this.id = id;
        this.sessoesVotadas = sessoesVotadas;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public HashMap<String, String> getSessoesVotadas() {
        return sessoesVotadas;
    }

}
