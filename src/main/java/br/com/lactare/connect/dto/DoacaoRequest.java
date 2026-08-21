package br.com.lactare.connect.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DoacaoRequest(
        @NotNull(message = "Nutriz é obrigatória")
        @Schema(example = "1")
        Long nutrizId,
        @NotNull(message = "BLH é obrigatório")
        @Schema(example = "1")
        Long blhId,
        Long agendamentoId,
        @NotNull(message = "Volume doado é obrigatório")
        @Positive(message = "Volume doado deve ser maior que zero")
        @Schema(example = "120")
        Integer volumeMl,
        @NotNull(message = "Data da doação é obrigatória")
        @PastOrPresent(message = "Data da doação não pode estar no futuro")
        @Schema(example = "2030-06-15")
        LocalDate dataDoacao,
        @PositiveOrZero(message = "Bebês beneficiados não pode ser negativo")
        Integer bebesBeneficiados,
        @Size(max = 300, message = "Observações devem ter no máximo 300 caracteres")
        String observacoes
) {
}
