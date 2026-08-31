package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.entity.ScorePropensao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScorePropensaoResponseTest {

    @Test
    void deveRetornarEstadoDaNutrizNoCampoEstado() {
        Nutriz nutriz = new Nutriz();
        nutriz.setId(1L);
        nutriz.setNome("Ana Silva");
        nutriz.setEstado("SP");
        nutriz.setSemanasPosParto(6);

        ScorePropensao score = new ScorePropensao();
        score.setNutriz(nutriz);
        score.setScore(92);

        ScorePropensaoResponse response = new ScorePropensaoResponse(score);

        assertEquals("SP", response.estado());
    }
}
