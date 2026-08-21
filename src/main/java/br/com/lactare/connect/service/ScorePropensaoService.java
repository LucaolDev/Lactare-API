package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.ScorePropensaoRequest;
import br.com.lactare.connect.dto.ScorePropensaoResponse;
import br.com.lactare.connect.entity.PrioridadeIa;
import br.com.lactare.connect.entity.ScorePropensao;
import br.com.lactare.connect.exception.ResourceNotFoundException;
import br.com.lactare.connect.repository.ScorePropensaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScorePropensaoService {

    private final ScorePropensaoRepository repository;
    private final NutrizService nutrizService;

    public ScorePropensaoService(ScorePropensaoRepository repository, NutrizService nutrizService) {
        this.repository = repository;
        this.nutrizService = nutrizService;
    }

    @Transactional(readOnly = true)
    public List<ScorePropensaoResponse> findAll(PrioridadeIa prioridade) {
        List<ScorePropensao> entities = prioridade == null
                ? repository.findAllByOrderByScoreDesc()
                : repository.findByPrioridadeOrderByScoreDesc(prioridade);
        return entities.stream().map(ScorePropensaoResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public ScorePropensaoResponse findById(Long id) {
        return new ScorePropensaoResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score de propensão não encontrado. ID: " + id)));
    }

    @Transactional
    public ScorePropensaoResponse save(ScorePropensaoRequest request) {
        ScorePropensao entity = new ScorePropensao();
        entity.setNutriz(nutrizService.getEntity(request.nutrizId()));
        entity.setScore(request.score());
        entity.setPrioridade(toPriority(request.score()));
        entity.setFatoresPrincipais(request.fatoresPrincipais());
        return new ScorePropensaoResponse(repository.save(entity));
    }

    @Transactional
    public ScorePropensaoResponse update(Long id, ScorePropensaoRequest request) {
        ScorePropensao entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score de propensão não encontrado. ID: " + id));
        entity.setNutriz(nutrizService.getEntity(request.nutrizId()));
        entity.setScore(request.score());
        entity.setPrioridade(toPriority(request.score()));
        entity.setFatoresPrincipais(request.fatoresPrincipais());
        return new ScorePropensaoResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score de propensão não encontrado. ID: " + id)));
    }

    private PrioridadeIa toPriority(int score) {
        if (score > 70) return PrioridadeIa.ALTA;
        if (score >= 40) return PrioridadeIa.MEDIA;
        return PrioridadeIa.BAIXA;
    }
}
