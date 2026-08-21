package br.com.lactare.connect.repository;

import br.com.lactare.connect.entity.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
    List<Doacao> findAllByOrderByDataDoacaoDesc();
    List<Doacao> findByNutrizIdOrderByDataDoacaoDesc(Long nutrizId);
    long countByNutrizId(Long nutrizId);

    @Query("select coalesce(sum(d.volumeMl), 0) from Doacao d where d.nutriz.id = :nutrizId")
    Long sumVolumeMlByNutrizId(@Param("nutrizId") Long nutrizId);

    @Query("select coalesce(sum(d.bebesBeneficiados), 0) from Doacao d where d.nutriz.id = :nutrizId")
    Long sumBebesBeneficiadosByNutrizId(@Param("nutrizId") Long nutrizId);

    @Query("select coalesce(sum(d.volumeMl), 0) from Doacao d")
    Long sumVolumeMl();

    @Query("select coalesce(sum(d.bebesBeneficiados), 0) from Doacao d")
    Long sumBebesBeneficiados();
}
