package br.com.lactare.connect.repository;

import br.com.lactare.connect.entity.QuizResposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRespostaRepository extends JpaRepository<QuizResposta, Long> {
    List<QuizResposta> findByNutrizIdOrderByRespondidaEmDesc(Long nutrizId);
}
