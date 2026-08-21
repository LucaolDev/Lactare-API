package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.DashboardResponse;
import br.com.lactare.connect.entity.AgendamentoStatus;
import br.com.lactare.connect.entity.CampanhaStatus;
import br.com.lactare.connect.entity.NutrizStatus;
import br.com.lactare.connect.entity.PrioridadeIa;
import br.com.lactare.connect.repository.AgendamentoRepository;
import br.com.lactare.connect.repository.BlhRepository;
import br.com.lactare.connect.repository.CampanhaRepository;
import br.com.lactare.connect.repository.DoacaoRepository;
import br.com.lactare.connect.repository.NutrizRepository;
import br.com.lactare.connect.repository.ScorePropensaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final NutrizRepository nutrizRepository;
    private final BlhRepository blhRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final DoacaoRepository doacaoRepository;
    private final CampanhaRepository campanhaRepository;
    private final ScorePropensaoRepository scoreRepository;

    public DashboardService(NutrizRepository nutrizRepository, BlhRepository blhRepository,
                            AgendamentoRepository agendamentoRepository, DoacaoRepository doacaoRepository,
                            CampanhaRepository campanhaRepository, ScorePropensaoRepository scoreRepository) {
        this.nutrizRepository = nutrizRepository;
        this.blhRepository = blhRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.doacaoRepository = doacaoRepository;
        this.campanhaRepository = campanhaRepository;
        this.scoreRepository = scoreRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponse getOverview() {
        return new DashboardResponse(
                nutrizRepository.countByStatus(NutrizStatus.ATIVA),
                blhRepository.countByAtivoTrue(),
                agendamentoRepository.countByStatus(AgendamentoStatus.CONFIRMADO),
                doacaoRepository.count(),
                valueOrZero(doacaoRepository.sumVolumeMl()),
                valueOrZero(doacaoRepository.sumBebesBeneficiados()),
                scoreRepository.countByPrioridade(PrioridadeIa.ALTA),
                campanhaRepository.countByStatus(CampanhaStatus.ATIVA));
    }

    private long valueOrZero(Long value) {
        return value == null ? 0 : value;
    }
}
