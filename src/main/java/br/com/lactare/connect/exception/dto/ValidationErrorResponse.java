package br.com.lactare.connect.exception.dto;

import java.time.Instant;
import java.util.List;

public record ValidationErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String path,
        List<FieldErrorResponse> errors
) {
}
