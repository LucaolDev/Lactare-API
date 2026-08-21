package br.com.lactare.connect.exception;

import br.com.lactare.connect.exception.dto.CustomErrorResponse;
import br.com.lactare.connect.exception.dto.FieldErrorResponse;
import br.com.lactare.connect.exception.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFound(ResourceNotFoundException ex,
                                                               HttpServletRequest request) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CustomErrorResponse> handleBusiness(BusinessException ex,
                                                               HttpServletRequest request) {
        return error(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                                    HttpServletRequest request) {
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldError)
                .toList();
        ValidationErrorResponse response = new ValidationErrorResponse(
                Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Dados inválidos",
                request.getRequestURI(), errors);
        return ResponseEntity.unprocessableEntity().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleConstraint(ConstraintViolationException ex,
                                                                 HttpServletRequest request) {
        return error(HttpStatus.UNPROCESSABLE_ENTITY, "Dados inválidos: " + ex.getMessage(), request);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<CustomErrorResponse> handleMalformedRequest(Exception ex,
                                                                       HttpServletRequest request) {
        return error(HttpStatus.BAD_REQUEST, "Requisição inválida ou formato de dado incorreto", request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDatabase(DataIntegrityViolationException ex,
                                                              HttpServletRequest request) {
        return error(HttpStatus.CONFLICT, "Não foi possível persistir os dados; verifique conflitos e relacionamentos",
                request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleUnexpected(Exception ex,
                                                                 HttpServletRequest request) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado", request);
    }

    private FieldErrorResponse toFieldError(FieldError error) {
        return new FieldErrorResponse(error.getField(), error.getDefaultMessage());
    }

    private ResponseEntity<CustomErrorResponse> error(HttpStatus status, String message,
                                                      HttpServletRequest request) {
        return ResponseEntity.status(status).body(new CustomErrorResponse(
                Instant.now(), status.value(), message, request.getRequestURI()));
    }
}
