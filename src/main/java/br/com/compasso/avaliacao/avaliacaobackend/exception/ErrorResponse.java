package br.com.compasso.avaliacao.avaliacaobackend.exception;

import java.util.Collection;

public class ErrorResponse {

    private String message;
    private Collection<String> details;

    public ErrorResponse(String message, Collection<String> details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<String> getDetails() {
        return details;
    }

    public void setDetails(Collection<String> details) {
        this.details = details;
    }
}
