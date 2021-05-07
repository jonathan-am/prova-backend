package br.com.compasso.avaliacao.avaliacaobackend.dto;

/**
 * Classe DTO para saida de dados para o kafka, da entidade Sessao
 */
public class SessaoSaidaKafkaDTO {

    private String id;
    private String resultado;

    public SessaoSaidaKafkaDTO(String id, String resultado) {
        this.id = id;
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "A sessao={'" + getId() + "'}, teve sua " + getResultado();
    }
}
