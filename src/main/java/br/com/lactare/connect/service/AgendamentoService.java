package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.AgendamentoRequest;
import br.com.lactare.connect.dto.AgendamentoResponse;
import br.com.lactare.connect.entity.Agendamento;
import br.com.lactare.connect.entity.AgendamentoStatus;
import br.com.lactare.connect.entity.Blh;
import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.exception.BusinessException;
import br.com.lactare.connect.exception.ResourceNotFoundException;
import br.com.lactare.connect.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private static final List<AgendamentoStatus> ACTIVE_SLOT_STATUSES =
            List.of(AgendamentoStatus.SOLICITADO, AgendamentoStatus.CONFIRMADO);

    private final AgendamentoRepository repository;
    private final NutrizService nutrizService;
    private final BlhService blhService;

    public AgendamentoService(AgendamentoRepository repository, NutrizService nutrizService, BlhService blhService) {
        this.repository = repository;
        this.nutrizService = nutrizService;
        this.blhService = blhService;
    }

    @Transactional(readOnly = true)
    public List<AgendamentoResponse> findAll(Long nutrizId) {
        List<Agendamento> entities = nutrizId == null ? repository.findAllByOrderByDataHoraAsc() : repository.findByNutrizIdOrderByDataHoraDesc(nutrizId);
        return entities.stream().map(AgendamentoResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public AgendamentoResponse findById(Long id) {
        return new AgendamentoResponse(getEntity(id));
    }

    @Transactional
    public AgendamentoResponse save(AgendamentoRequest request) {
        Agendamento entity = new Agendamento();
        copy(request, entity);
        return new AgendamentoResponse(repository.save(entity));
    }

    @Transactional
    public AgendamentoResponse update(Long id, AgendamentoRequest request) {
        Agendamento entity = getEntity(id);
        copy(request, entity);
        return new AgendamentoResponse(entity);
    }

    @Transactional
    public AgendamentoResponse updateStatus(Long id, AgendamentoStatus status) {
        if (status == null) {
            throw new BusinessException("Status é obrigatório");
        }
        Agendamento entity = getEntity(id);
        validateStatusTransition(entity.getStatus(), status);
        validateSlot(entity.getNutriz().getId(), entity.getBlh().getId(), entity.getDataHora(), status, entity.getId());
        entity.setStatus(status);
        return new AgendamentoResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    public Agendamento getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado. ID: " + id));
    }

    private void copy(AgendamentoRequest request, Agendamento entity) {
        Nutriz nutriz = nutrizService.getActiveEntity(request.nutrizId());
        Blh blh = blhService.getActiveEntity(request.blhId());
        if (request.tipoColeta().name().equals("DOMICILIAR") && !Boolean.TRUE.equals(blh.getAceitaColetaDomiciliar())) {
            throw new BusinessException("O BLH selecionado não oferece coleta domiciliar");
        }

        AgendamentoStatus nextStatus = request.status();
        if (nextStatus == null) {
            nextStatus = entity.getStatus() == null ? AgendamentoStatus.SOLICITADO : entity.getStatus();
        }
        if (entity.getId() == null
                && nextStatus != AgendamentoStatus.SOLICITADO
                && nextStatus != AgendamentoStatus.CONFIRMADO) {
            throw new BusinessException("Um novo agendamento deve ser solicitado ou confirmado");
        }
        if (entity.getId() != null) {
            validateStatusTransition(entity.getStatus(), nextStatus);
        }
        validateSlot(nutriz.getId(), blh.getId(), request.dataHora(), nextStatus, entity.getId());

        entity.setNutriz(nutriz);
        entity.setBlh(blh);
        entity.setDataHora(request.dataHora());
        entity.setTipoColeta(request.tipoColeta());
        entity.setObservacoes(request.observacoes());
        entity.setStatus(nextStatus);
    }

    private void validateSlot(Long nutrizId, Long blhId, LocalDateTime dataHora,
                              AgendamentoStatus status, Long currentId) {
        if (!ACTIVE_SLOT_STATUSES.contains(status)) {
            return;
        }

        boolean blhConflict = currentId == null
                ? repository.existsByBlhIdAndDataHoraAndStatusIn(blhId, dataHora, ACTIVE_SLOT_STATUSES)
                : repository.existsByBlhIdAndDataHoraAndStatusInAndIdNot(
                        blhId, dataHora, ACTIVE_SLOT_STATUSES, currentId);
        if (blhConflict) {
            throw new BusinessException("Já existe um agendamento ativo para este BLH nesse horário");
        }

        boolean nutrizConflict = currentId == null
                ? repository.existsByNutrizIdAndDataHoraAndStatusIn(nutrizId, dataHora, ACTIVE_SLOT_STATUSES)
                : repository.existsByNutrizIdAndDataHoraAndStatusInAndIdNot(
                        nutrizId, dataHora, ACTIVE_SLOT_STATUSES, currentId);
        if (nutrizConflict) {
            throw new BusinessException("A nutriz já possui um agendamento ativo nesse horário");
        }
    }

    private void validateStatusTransition(AgendamentoStatus current, AgendamentoStatus next) {
        if (current == null || current == next) {
            return;
        }

        boolean allowed = switch (current) {
            case SOLICITADO -> next == AgendamentoStatus.CONFIRMADO
                    || next == AgendamentoStatus.CANCELADO;
            case CONFIRMADO -> next == AgendamentoStatus.CONCLUIDO
                    || next == AgendamentoStatus.CANCELADO;
            case CONCLUIDO, CANCELADO -> false;
        };
        if (!allowed) {
            throw new BusinessException("Transição de status de " + current + " para " + next + " não permitida");
        }
    }
}
