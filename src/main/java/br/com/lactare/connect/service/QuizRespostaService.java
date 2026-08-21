package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.QuizRespostaRequest;
import br.com.lactare.connect.dto.QuizRespostaResponse;
import br.com.lactare.connect.entity.QuizResposta;
import br.com.lactare.connect.exception.ResourceNotFoundException;
import br.com.lactare.connect.repository.QuizRespostaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizRespostaService {

    private final QuizRespostaRepository repository;
    private final NutrizService nutrizService;

    public QuizRespostaService(QuizRespostaRepository repository, NutrizService nutrizService) {
        this.repository = repository;
        this.nutrizService = nutrizService;
    }

    @Transactional(readOnly = true)
    public List<QuizRespostaResponse> findAll(Long nutrizId) {
        List<QuizResposta> entities = nutrizId == null ? repository.findAll() : repository.findByNutrizIdOrderByRespondidaEmDesc(nutrizId);
        return entities.stream().map(QuizRespostaResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public QuizRespostaResponse findById(Long id) {
        return new QuizRespostaResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resposta do quiz não encontrada. ID: " + id)));
    }

    @Transactional
    public QuizRespostaResponse save(QuizRespostaRequest request) {
        QuizResposta entity = new QuizResposta();
        entity.setNutriz(nutrizService.getEntity(request.nutrizId()));
        entity.setPergunta(request.pergunta());
        entity.setResposta(request.resposta());
        entity.setElegivel(request.elegivel());
        return new QuizRespostaResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resposta do quiz não encontrada. ID: " + id)));
    }
}
