package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.entity.NutrizStatus;

import java.time.LocalDateTime;

public record NutrizResponse(
        Long id,
        String nome,
        String telefone,
        String email,
        String cidade,
        String estado,
        Integer semanasPosParto,
        Boolean consentimentoLgpd,
        NutrizStatus status,
        LocalDateTime criadoEm
) {
    public NutrizResponse(Nutriz entity) {
        this(entity.getId(), entity.getNome(), entity.getTelefone(), entity.getEmail(),
                entity.getCidade(), entity.getEstado(), entity.getSemanasPosParto(),
                entity.getConsentimentoLgpd(), entity.getStatus(), entity.getCriadoEm());
    }
}
