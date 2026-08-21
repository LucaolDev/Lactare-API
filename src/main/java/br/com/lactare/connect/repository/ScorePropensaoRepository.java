package br.com.lactare.connect.repository;

import br.com.lactare.connect.entity.PrioridadeIa;
import br.com.lactare.connect.entity.ScorePropensao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScorePropensaoRepository extends JpaRepository<ScorePropensao, Long> {
    List<ScorePropensao> findAllByOrderByScoreDesc();
    List<ScorePropensao> findByPrioridadeOrderByScoreDesc(PrioridadeIa prioridade);
    Optional<ScorePropensao> findTopByNutrizIdOrderByAtualizadoEmDesc(Long nutrizId);
    long countByPrioridade(PrioridadeIa prioridade);
}
