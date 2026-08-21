package br.com.lactare.connect.exception.dto;

import java.time.Instant;

public record CustomErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String path
) {
}
