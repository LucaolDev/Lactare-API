package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.ImpactoResponse;
import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.repository.DoacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImpactoService {

    private final DoacaoRepository doacaoRepository;
    private final NutrizService nutrizService;

    public ImpactoService(DoacaoRepository doacaoRepository, NutrizService nutrizService) {
        this.doacaoRepository = doacaoRepository;
        this.nutrizService = nutrizService;
    }

    @Transactional(readOnly = true)
    public ImpactoResponse findByNutrizId(Long nutrizId) {
        Nutriz nutriz = nutrizService.getEntity(nutrizId);
        long doacoes = doacaoRepository.countByNutrizId(nutrizId);
        return new ImpactoResponse(
                nutriz.getId(),
                nutriz.getNome(),
                valueOrZero(doacaoRepository.sumVolumeMlByNutrizId(nutrizId)),
                doacoes,
                valueOrZero(doacaoRepository.sumBebesBeneficiadosByNutrizId(nutrizId)),
                Math.max(0, 10 - (int) doacoes));
    }

    private long valueOrZero(Long value) {
        return value == null ? 0 : value;
    }
}
