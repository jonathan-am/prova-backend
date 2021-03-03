package br.com.compasso.avaliacao.avaliacaobackend.dto;

public class VotoEntradaPorIdDTO {

    private String id;
    private VotoDTO votoDto;

    public VotoDTO getVotoDto() {
        return votoDto;
    }

    public void setVotoDto(VotoDTO votoDto) {
        this.votoDto = votoDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
