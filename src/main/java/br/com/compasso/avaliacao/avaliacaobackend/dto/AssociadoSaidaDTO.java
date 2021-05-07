package br.com.compasso.avaliacao.avaliacaobackend.dto;


import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;

import java.util.HashMap;

/**
 * Classe DTO para saida de dados, da entidade Associado
 */
public class AssociadoSaidaDTO {

    private String id;
    private String nome;
    private HashMap<String, String> sessoesVotadas;

    public AssociadoSaidaDTO(String id, String nome, HashMap<String, String> sessoesVotadas) {
        this.id = id;
        this.nome = nome;
        this.sessoesVotadas = sessoesVotadas;
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
