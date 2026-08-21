package br.com.lactare.connect.dto;

public record DashboardResponse(
        long nutrizesAtivas,
        long blhsAtivos,
        long agendamentosConfirmados,
        long doacoesRealizadas,
        long volumeTotalMl,
        long bebesBeneficiados,
        long scoresAltaPrioridade,
        long campanhasAtivas
) {
}
