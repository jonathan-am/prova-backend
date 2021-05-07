package br.com.compasso.avaliacao.avaliacaobackend.exception;

public class LimiteSessoesException extends RuntimeException {

    public LimiteSessoesException(String message) {
        super(message);
    }
}
