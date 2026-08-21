package br.com.lactare.connect.repository;

import br.com.lactare.connect.entity.Campanha;
import br.com.lactare.connect.entity.CampanhaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampanhaRepository extends JpaRepository<Campanha, Long> {
    long countByStatus(CampanhaStatus status);
}
