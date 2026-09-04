package br.com.lactare.connect.repository;

import br.com.lactare.connect.entity.Agendamento;
import br.com.lactare.connect.entity.AgendamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findAllByOrderByDataHoraAsc();
    List<Agendamento> findByNutrizIdOrderByDataHoraDesc(Long nutrizId);
    boolean existsByBlhIdAndDataHoraAndStatusIn(Long blhId, LocalDateTime dataHora,
                                                 Collection<AgendamentoStatus> status);
    boolean existsByBlhIdAndDataHoraAndStatusInAndIdNot(Long blhId, LocalDateTime dataHora,
                                                         Collection<AgendamentoStatus> status, Long id);
    boolean existsByNutrizIdAndDataHoraAndStatusIn(Long nutrizId, LocalDateTime dataHora,
                                                    Collection<AgendamentoStatus> status);
    boolean existsByNutrizIdAndDataHoraAndStatusInAndIdNot(Long nutrizId, LocalDateTime dataHora,
                                                            Collection<AgendamentoStatus> status, Long id);
    long countByStatus(AgendamentoStatus status);
}
