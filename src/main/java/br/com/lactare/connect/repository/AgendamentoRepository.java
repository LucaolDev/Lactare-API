package br.com.lactare.connect.repository;

import br.com.lactare.connect.entity.Agendamento;
import br.com.lactare.connect.entity.AgendamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findAllByOrderByDataHoraAsc();
    List<Agendamento> findByNutrizIdOrderByDataHoraDesc(Long nutrizId);
    long countByStatus(AgendamentoStatus status);
}
