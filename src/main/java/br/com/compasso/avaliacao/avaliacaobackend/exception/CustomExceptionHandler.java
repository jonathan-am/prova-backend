package br.com.compasso.avaliacao.avaliacaobackend.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String REQUIRED_REQUEST_BODY_IS_MISSING = "Required request body is missing";
    private static final String ERRO_DESCONHECIDO = "Erro desconhecido. Contate o administrador do sistema.";
    private static final String KEY_USER_ERROR_NOT_FOUND = "0000";
    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    private static final String BAD_REQUEST = "BAD_REQUEST";
    private static final String NOT_FOUND = "NOT_FOUND";
    private static final String NOT_ACCEPTABLE = "NOT_ACCEPTABLE";

    private ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(NaoExisteException.class)
    public final ResponseEntity<ErrorResponse> handleNaoExisteException(NaoExisteException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(NOT_FOUND, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LimiteSessoesException.class)
    public final ResponseEntity<ErrorResponse> handleLimiteSessoesException(LimiteSessoesException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(NOT_ACCEPTABLE, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ComputarVotoException.class)
    public final ResponseEntity<ErrorResponse> handleComputarVotoException(ComputarVotoException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssociadoExisteException.class)
    public final ResponseEntity<ErrorResponse> handleAssociadoExisteException(AssociadoExisteException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(NOT_ACCEPTABLE, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(IniciarSessaoException.class)
    public final ResponseEntity<ErrorResponse> handleIniciarSessaoException(IniciarSessaoException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(INTERNAL_SERVER_ERROR, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public final ResponseEntity<ErrorResponse> handleServerErrorException(HttpServerErrorException ex, WebRequest request) {
        return convertMessageToEspecialConditionInHttpServerErrorException(ex.getResponseBodyAsString(), ex);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<ErrorResponse> handleClientErrorException(HttpClientErrorException ex, WebRequest request) {
        return new ResponseEntity<>(convertErrorMessage(ex.getResponseBodyAsString()), ex.getStatusCode());
    }

    private ErrorResponse convertErrorMessage(String message) {
        ErrorResponse error;
        try {
            error = mapper.readValue(message, ErrorResponse.class);
        } catch (Exception e) {
            logger.error("Error mapping value from body in handling", e);
            error = new ErrorResponse(BAD_REQUEST, Collections.singletonList(ERRO_DESCONHECIDO));
        }
        return error;
    }

    private ResponseEntity<ErrorResponse> convertMessageToEspecialConditionInHttpServerErrorException(String message, HttpServerErrorException ex) {
        ErrorResponse error = convertErrorMessage(message);
        HttpStatus statusCode = ex.getStatusCode();
        if (error.getDetails().stream().anyMatch(erro -> erro.contains(KEY_USER_ERROR_NOT_FOUND))) {
            error = new ErrorResponse(BAD_REQUEST, Collections.singletonList("user not found."));
            statusCode = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(error, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, Collections.singletonList(ex.getMostSpecificCause().getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(BAD_REQUEST, ex.getBindingResult().getFieldErrors().stream()
                .map(field -> String.format("%s - %s", field.getField(), field.getDefaultMessage())).collect(Collectors.toList())), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String message = ex.getMostSpecificCause().getMessage();
        if (message.contains(REQUIRED_REQUEST_BODY_IS_MISSING)) {
            message = REQUIRED_REQUEST_BODY_IS_MISSING;
        } else if (message.contains("Unrecognized field")) {
            message = tratarMensagemAteParentese(message);
        } else {
            message = ERRO_DESCONHECIDO;
        }
        logger.error("handleHttpMessageNotReadable", ex);
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, Collections.singletonList(message));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private String tratarMensagemAteParentese(String message) {
        return message.substring(0, message.indexOf('('));
    }

}
