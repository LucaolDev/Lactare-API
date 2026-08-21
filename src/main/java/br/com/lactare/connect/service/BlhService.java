package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.BlhRequest;
import br.com.lactare.connect.dto.BlhResponse;
import br.com.lactare.connect.entity.Blh;
import br.com.lactare.connect.exception.ResourceNotFoundException;
import br.com.lactare.connect.repository.BlhRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlhService {

    private final BlhRepository repository;

    public BlhService(BlhRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<BlhResponse> findAll(String cidade) {
        List<Blh> entities = cidade == null || cidade.isBlank()
                ? repository.findByAtivoTrueOrderByNome()
                : repository.findByAtivoTrueAndCidadeIgnoreCaseOrderByNome(cidade);
        return entities.stream().map(BlhResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public BlhResponse findById(Long id) {
        return new BlhResponse(getEntity(id));
    }

    @Transactional
    public BlhResponse save(BlhRequest request) {
        Blh entity = new Blh();
        copy(request, entity);
        return new BlhResponse(repository.save(entity));
    }

    @Transactional
    public BlhResponse update(Long id, BlhRequest request) {
        Blh entity = getEntity(id);
        copy(request, entity);
        return new BlhResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    public Blh getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BLH não encontrado. ID: " + id));
    }

    private void copy(BlhRequest request, Blh entity) {
        entity.setNome(request.nome());
        entity.setEndereco(request.endereco());
        entity.setBairro(request.bairro());
        entity.setCidade(request.cidade());
        entity.setEstado(request.estado().toUpperCase());
        entity.setCep(request.cep());
        entity.setHorarioFuncionamento(request.horarioFuncionamento());
        entity.setAceitaColetaDomiciliar(request.aceitaColetaDomiciliar());
        entity.setLatitude(request.latitude());
        entity.setLongitude(request.longitude());
        if (request.ativo() != null) {
            entity.setAtivo(request.ativo());
        }
    }
}
