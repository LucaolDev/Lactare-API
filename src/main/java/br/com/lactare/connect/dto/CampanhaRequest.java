package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.CampanhaStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CampanhaRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 120, message = "Nome deve ter entre 3 e 120 caracteres")
        @Schema(example = "Doe leite, salve vidas")
        String nome,
        @NotBlank(message = "Região é obrigatória")
        @Size(max = 80, message = "Região deve ter no máximo 80 caracteres")
        @Schema(example = "Norte - SP")
        String regiao,
        @NotBlank(message = "Canal é obrigatório")
        @Size(max = 30, message = "Canal deve ter no máximo 30 caracteres")
        @Schema(example = "WHATSAPP")
        String canal,
        @Size(max = 500, message = "Mensagem deve ter no máximo 500 caracteres")
        String mensagem,
        CampanhaStatus status
) {
}
