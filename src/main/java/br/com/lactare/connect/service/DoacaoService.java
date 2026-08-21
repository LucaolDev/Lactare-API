package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.DoacaoRequest;
import br.com.lactare.connect.dto.DoacaoResponse;
import br.com.lactare.connect.entity.Agendamento;
import br.com.lactare.connect.entity.AgendamentoStatus;
import br.com.lactare.connect.entity.Blh;
import br.com.lactare.connect.entity.Doacao;
import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.exception.BusinessException;
import br.com.lactare.connect.exception.ResourceNotFoundException;
import br.com.lactare.connect.repository.AgendamentoRepository;
import br.com.lactare.connect.repository.DoacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoacaoService {

    private final DoacaoRepository repository;
    private final AgendamentoRepository agendamentoRepository;
    private final NutrizService nutrizService;
    private final BlhService blhService;

    public DoacaoService(DoacaoRepository repository, AgendamentoRepository agendamentoRepository,
                         NutrizService nutrizService, BlhService blhService) {
        this.repository = repository;
        this.agendamentoRepository = agendamentoRepository;
        this.nutrizService = nutrizService;
        this.blhService = blhService;
    }

    @Transactional(readOnly = true)
    public List<DoacaoResponse> findAll(Long nutrizId) {
        List<Doacao> entities = nutrizId == null ? repository.findAllByOrderByDataDoacaoDesc() : repository.findByNutrizIdOrderByDataDoacaoDesc(nutrizId);
        return entities.stream().map(DoacaoResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public DoacaoResponse findById(Long id) {
        return new DoacaoResponse(getEntity(id));
    }

    @Transactional
    public DoacaoResponse save(DoacaoRequest request) {
        Nutriz nutriz = nutrizService.getEntity(request.nutrizId());
        Blh blh = blhService.getEntity(request.blhId());
        Agendamento agendamento = null;
        if (request.agendamentoId() != null) {
            agendamento = agendamentoRepository.findById(request.agendamentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado. ID: " + request.agendamentoId()));
            if (!agendamento.getNutriz().getId().equals(nutriz.getId()) || !agendamento.getBlh().getId().equals(blh.getId())) {
                throw new BusinessException("Agendamento não pertence à nutriz e ao BLH informados");
            }
            agendamento.setStatus(AgendamentoStatus.CONCLUIDO);
        }
        Doacao entity = new Doacao();
        entity.setNutriz(nutriz);
        entity.setBlh(blh);
        entity.setAgendamento(agendamento);
        entity.setVolumeMl(request.volumeMl());
        entity.setDataDoacao(request.dataDoacao());
        entity.setBebesBeneficiados(request.bebesBeneficiados() == null
                ? Math.max(1, request.volumeMl() / 100) : request.bebesBeneficiados());
        entity.setObservacoes(request.observacoes());
        return new DoacaoResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    public Doacao getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doação não encontrada. ID: " + id));
    }
}
