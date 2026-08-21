package br.com.lactare.connect.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuizRespostaRequest(
        @NotNull(message = "Nutriz é obrigatória")
        @Schema(example = "1")
        Long nutrizId,
        @NotBlank(message = "Pergunta é obrigatória")
        @Size(max = 120, message = "Pergunta deve ter no máximo 120 caracteres")
        @Schema(example = "Você está amamentando atualmente?")
        String pergunta,
        @NotBlank(message = "Resposta é obrigatória")
        @Size(max = 160, message = "Resposta deve ter no máximo 160 caracteres")
        @Schema(example = "Sim, estou amamentando")
        String resposta,
        @NotNull(message = "Informe se a resposta é elegível")
        @Schema(example = "true")
        Boolean elegivel
) {
}
