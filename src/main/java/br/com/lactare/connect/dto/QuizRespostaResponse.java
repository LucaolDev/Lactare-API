package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.QuizResposta;

import java.time.LocalDateTime;

public record QuizRespostaResponse(
        Long id,
        Long nutrizId,
        String nutrizNome,
        String pergunta,
        String resposta,
        Boolean elegivel,
        LocalDateTime respondidaEm
) {
    public QuizRespostaResponse(QuizResposta entity) {
        this(entity.getId(), entity.getNutriz().getId(), entity.getNutriz().getNome(),
                entity.getPergunta(), entity.getResposta(), entity.getElegivel(), entity.getRespondidaEm());
    }
}
