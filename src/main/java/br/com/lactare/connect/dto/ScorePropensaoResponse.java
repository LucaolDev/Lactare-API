package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.PrioridadeIa;
import br.com.lactare.connect.entity.ScorePropensao;

import java.time.LocalDateTime;

public record ScorePropensaoResponse(
        Long id,
        Long nutrizId,
        String nutrizNome,
        String estado,
        Integer semanasPosParto,
        Integer score,
        PrioridadeIa prioridade,
        String fatoresPrincipais,
        LocalDateTime atualizadoEm
) {
    public ScorePropensaoResponse(ScorePropensao entity) {
        this(entity.getId(), entity.getNutriz().getId(), entity.getNutriz().getNome(),
                entity.getNutriz().getEstado(), entity.getNutriz().getSemanasPosParto(),
                entity.getScore(), entity.getPrioridade(), entity.getFatoresPrincipais(),
                entity.getAtualizadoEm());
    }
}
