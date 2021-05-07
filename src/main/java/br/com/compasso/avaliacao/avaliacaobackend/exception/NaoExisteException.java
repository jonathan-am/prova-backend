package br.com.compasso.avaliacao.avaliacaobackend.exception;

public class NaoExisteException extends RuntimeException{

    public NaoExisteException(String message) {
        super(message);
    }
}
