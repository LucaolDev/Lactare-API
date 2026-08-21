package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.Doacao;

import java.time.LocalDate;

public record DoacaoResponse(
        Long id,
        Long nutrizId,
        String nutrizNome,
        Long blhId,
        String blhNome,
        Long agendamentoId,
        Integer volumeMl,
        LocalDate dataDoacao,
        Integer bebesBeneficiados,
        String observacoes
) {
    public DoacaoResponse(Doacao entity) {
        this(entity.getId(), entity.getNutriz().getId(), entity.getNutriz().getNome(),
                entity.getBlh().getId(), entity.getBlh().getNome(),
                entity.getAgendamento() == null ? null : entity.getAgendamento().getId(),
                entity.getVolumeMl(), entity.getDataDoacao(), entity.getBebesBeneficiados(),
                entity.getObservacoes());
    }
}
