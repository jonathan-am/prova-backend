package br.com.compasso.avaliacao.avaliacaobackend.dto;

import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;

/**
 * Classe DTO para entrada de dados da entidade Associado
 */
public class AssociadoEntradaDTO {

    private String nome;
    private String cpf;

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

    public AssociadoEntity dtoToEntity() {
        AssociadoEntity ent = new AssociadoEntity();
        ent.setNome(nome);
        ent.setCpf(cpf);
        return ent;
    }
}
