package br.com.compasso.avaliacao.avaliacaobackend.dto;

/**
 * Classe DTO para entrada de dados com chave primaria ID, do valor VOTO da entidade Associado
 */
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
