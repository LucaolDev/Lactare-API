package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.NutrizRequest;
import br.com.lactare.connect.dto.NutrizResponse;
import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.entity.NutrizStatus;
import br.com.lactare.connect.exception.BusinessException;
import br.com.lactare.connect.exception.ResourceNotFoundException;
import br.com.lactare.connect.repository.NutrizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NutrizService {

    private final NutrizRepository repository;

    public NutrizService(NutrizRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<NutrizResponse> findAll(NutrizStatus status) {
        List<Nutriz> entities = status == null ? repository.findAll() : repository.findByStatusOrderByNome(status);
        return entities.stream().map(NutrizResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public NutrizResponse findById(Long id) {
        return new NutrizResponse(getEntity(id));
    }

    @Transactional
    public NutrizResponse save(NutrizRequest request) {
        repository.findByTelefone(request.telefone()).ifPresent(existing -> {
            throw new BusinessException("Já existe uma nutriz cadastrada com este telefone");
        });
        Nutriz entity = new Nutriz();
        copy(request, entity);
        return new NutrizResponse(repository.save(entity));
    }

    @Transactional
    public NutrizResponse update(Long id, NutrizRequest request) {
        Nutriz entity = getEntity(id);
        repository.findByTelefone(request.telefone()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new BusinessException("Já existe outra nutriz cadastrada com este telefone");
            }
        });
        copy(request, entity);
        return new NutrizResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        Nutriz entity = getEntity(id);
        repository.delete(entity);
    }

    public Nutriz getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nutriz não encontrada. ID: " + id));
    }

    private void copy(NutrizRequest request, Nutriz entity) {
        entity.setNome(request.nome());
        entity.setTelefone(request.telefone());
        entity.setEmail(request.email());
        entity.setCidade(request.cidade());
        entity.setEstado(request.estado().toUpperCase());
        entity.setSemanasPosParto(request.semanasPosParto());
        entity.setConsentimentoLgpd(request.consentimentoLgpd());
        if (request.status() != null) {
            entity.setStatus(request.status());
        }
    }
}
