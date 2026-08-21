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

import java.util.List;

@Service
public class AgendamentoService {

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
        Nutriz nutriz = nutrizService.getEntity(request.nutrizId());
        Blh blh = blhService.getEntity(request.blhId());
        if (request.tipoColeta().name().equals("DOMICILIAR") && !Boolean.TRUE.equals(blh.getAceitaColetaDomiciliar())) {
            throw new BusinessException("O BLH selecionado não oferece coleta domiciliar");
        }
        entity.setNutriz(nutriz);
        entity.setBlh(blh);
        entity.setDataHora(request.dataHora());
        entity.setTipoColeta(request.tipoColeta());
        entity.setObservacoes(request.observacoes());
        if (request.status() != null) {
            entity.setStatus(request.status());
        } else if (entity.getStatus() == null) {
            entity.setStatus(AgendamentoStatus.SOLICITADO);
        }
    }
}
