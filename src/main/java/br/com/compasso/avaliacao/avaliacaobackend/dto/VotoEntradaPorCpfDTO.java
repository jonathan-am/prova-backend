package br.com.compasso.avaliacao.avaliacaobackend.dto;

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
