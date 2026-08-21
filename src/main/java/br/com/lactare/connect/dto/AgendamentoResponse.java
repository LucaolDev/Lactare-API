package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.Agendamento;
import br.com.lactare.connect.entity.AgendamentoStatus;
import br.com.lactare.connect.entity.TipoColeta;

import java.time.LocalDateTime;

public record AgendamentoResponse(
        Long id,
        Long nutrizId,
        String nutrizNome,
        Long blhId,
        String blhNome,
        LocalDateTime dataHora,
        TipoColeta tipoColeta,
        AgendamentoStatus status,
        String observacoes
) {
    public AgendamentoResponse(Agendamento entity) {
        this(entity.getId(), entity.getNutriz().getId(), entity.getNutriz().getNome(),
                entity.getBlh().getId(), entity.getBlh().getNome(), entity.getDataHora(),
                entity.getTipoColeta(), entity.getStatus(), entity.getObservacoes());
    }
}
