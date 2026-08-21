package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.AgendamentoStatus;
import br.com.lactare.connect.entity.TipoColeta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AgendamentoRequest(
        @NotNull(message = "Nutriz é obrigatória")
        @Schema(example = "1")
        Long nutrizId,
        @NotNull(message = "BLH é obrigatório")
        @Schema(example = "1")
        Long blhId,
        @NotNull(message = "Data e hora são obrigatórias")
        @Future(message = "Data e hora devem estar no futuro")
        @Schema(example = "2030-06-15T10:30:00")
        LocalDateTime dataHora,
        @NotNull(message = "Tipo de coleta é obrigatório")
        TipoColeta tipoColeta,
        @Size(max = 300, message = "Observações devem ter no máximo 300 caracteres")
        String observacoes,
        AgendamentoStatus status
) {
}
