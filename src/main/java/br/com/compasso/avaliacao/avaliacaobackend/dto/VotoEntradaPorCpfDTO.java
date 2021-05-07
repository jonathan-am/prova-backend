package br.com.compasso.avaliacao.avaliacaobackend.dto;

/**
 * Classe DTO para entrada de dados com chave primaria CPF, do valor VOTO da entidade Associado
 */
public class VotoEntradaPorCpfDTO {

    private String cpf;
    private VotoDTO votoDto;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public VotoDTO getVotoDto() {
        return votoDto;
    }

    public void setVotoDto(VotoDTO votoDto) {
        this.votoDto = votoDto;
    }
}
