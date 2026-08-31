package br.com.lactare.connect.dto;

public record BlhMatchingResponse(
        BlhResponse blh,
        double distanciaKm
) {
}
