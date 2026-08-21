package br.com.lactare.connect.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ScorePropensaoRequest(
        @NotNull(message = "Nutriz é obrigatória")
        @Schema(example = "1")
        Long nutrizId,
        @NotNull(message = "Score é obrigatório")
        @Min(value = 0, message = "Score deve ser no mínimo 0")
        @Max(value = 100, message = "Score deve ser no máximo 100")
        @Schema(example = "92")
        Integer score,
        @NotBlank(message = "Fatores principais são obrigatórios")
        @Size(max = 500, message = "Fatores devem ter no máximo 500 caracteres")
        @Schema(example = "Região crítica; 5 interações no chatbot; pós-parto ideal")
        String fatoresPrincipais
) {
}
