package br.com.lactare.connect.controller;

import br.com.lactare.connect.dto.QuizRespostaRequest;
import br.com.lactare.connect.dto.QuizRespostaResponse;
import br.com.lactare.connect.service.QuizRespostaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz-respostas")
@Tag(name = "Quiz de elegibilidade", description = "Respostas do quiz guiado de elegibilidade")
public class QuizRespostaController {

    private final QuizRespostaService service;

    public QuizRespostaController(QuizRespostaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<QuizRespostaResponse>> findAll(@RequestParam(required = false) Long nutrizId) {
        return ResponseEntity.ok(service.findAll(nutrizId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizRespostaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<QuizRespostaResponse> save(@Valid @RequestBody QuizRespostaRequest request) {
        QuizRespostaResponse response = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
