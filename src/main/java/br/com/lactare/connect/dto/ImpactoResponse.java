package br.com.lactare.connect.dto;

public record ImpactoResponse(
        Long nutrizId,
        String nutrizNome,
        long volumeTotalMl,
        long doacoesRealizadas,
        long bebesBeneficiados,
        int doacoesParaProximoNivel
) {
}
