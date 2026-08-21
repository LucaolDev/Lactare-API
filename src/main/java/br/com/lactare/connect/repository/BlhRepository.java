package br.com.lactare.connect.repository;

import br.com.lactare.connect.entity.Blh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlhRepository extends JpaRepository<Blh, Long> {
    List<Blh> findByAtivoTrueOrderByNome();
    List<Blh> findByAtivoTrueAndCidadeIgnoreCaseOrderByNome(String cidade);
    long countByAtivoTrue();
}
