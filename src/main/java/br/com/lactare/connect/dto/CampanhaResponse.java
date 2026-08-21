package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.Campanha;
import br.com.lactare.connect.entity.CampanhaStatus;

import java.time.LocalDateTime;

public record CampanhaResponse(
        Long id,
        String nome,
        String regiao,
        String canal,
        String mensagem,
        CampanhaStatus status,
        LocalDateTime criadaEm,
        LocalDateTime disparadaEm
) {
    public CampanhaResponse(Campanha entity) {
        this(entity.getId(), entity.getNome(), entity.getRegiao(), entity.getCanal(),
                entity.getMensagem(), entity.getStatus(), entity.getCriadaEm(), entity.getDisparadaEm());
    }
}
