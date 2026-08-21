package br.com.lactare.connect.repository;

import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.entity.NutrizStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NutrizRepository extends JpaRepository<Nutriz, Long> {
    Optional<Nutriz> findByTelefone(String telefone);
    List<Nutriz> findByStatusOrderByNome(NutrizStatus status);
    long countByStatus(NutrizStatus status);
}
