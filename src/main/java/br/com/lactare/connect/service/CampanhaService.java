package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.CampanhaRequest;
import br.com.lactare.connect.dto.CampanhaResponse;
import br.com.lactare.connect.entity.Campanha;
import br.com.lactare.connect.entity.CampanhaStatus;
import br.com.lactare.connect.exception.ResourceNotFoundException;
import br.com.lactare.connect.repository.CampanhaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CampanhaService {

    private final CampanhaRepository repository;

    public CampanhaService(CampanhaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CampanhaResponse> findAll() {
        return repository.findAll().stream().map(CampanhaResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public CampanhaResponse findById(Long id) {
        return new CampanhaResponse(getEntity(id));
    }

    @Transactional
    public CampanhaResponse save(CampanhaRequest request) {
        Campanha entity = new Campanha();
        copy(request, entity);
        return new CampanhaResponse(repository.save(entity));
    }

    @Transactional
    public CampanhaResponse update(Long id, CampanhaRequest request) {
        Campanha entity = getEntity(id);
        copy(request, entity);
        return new CampanhaResponse(entity);
    }

    @Transactional
    public CampanhaResponse disparar(Long id) {
        Campanha entity = getEntity(id);
        entity.setStatus(CampanhaStatus.ATIVA);
        entity.setDisparadaEm(LocalDateTime.now());
        return new CampanhaResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    public Campanha getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada. ID: " + id));
    }

    private void copy(CampanhaRequest request, Campanha entity) {
        entity.setNome(request.nome());
        entity.setRegiao(request.regiao());
        entity.setCanal(request.canal().toUpperCase());
        entity.setMensagem(request.mensagem());
        if (request.status() != null) {
            entity.setStatus(request.status());
        }
    }
}
