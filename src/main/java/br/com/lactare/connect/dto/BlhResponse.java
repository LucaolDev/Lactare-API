package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.Blh;

public record BlhResponse(
        Long id,
        String nome,
        String endereco,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String horarioFuncionamento,
        Boolean aceitaColetaDomiciliar,
        Boolean ativo,
        Double latitude,
        Double longitude
) {
    public BlhResponse(Blh entity) {
        this(entity.getId(), entity.getNome(), entity.getEndereco(), entity.getBairro(),
                entity.getCidade(), entity.getEstado(), entity.getCep(),
                entity.getHorarioFuncionamento(), entity.getAceitaColetaDomiciliar(),
                entity.getAtivo(), entity.getLatitude(), entity.getLongitude());
    }
}
