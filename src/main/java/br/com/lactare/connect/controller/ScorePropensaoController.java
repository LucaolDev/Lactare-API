package br.com.lactare.connect.controller;

import br.com.lactare.connect.dto.ScorePropensaoRequest;
import br.com.lactare.connect.dto.ScorePropensaoResponse;
import br.com.lactare.connect.entity.PrioridadeIa;
import br.com.lactare.connect.service.ScorePropensaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ia-preditiva")
@Tag(name = "IA Preditiva", description = "Scores de propensão e priorização de nutrizes")
public class ScorePropensaoController {

    private final ScorePropensaoService service;

    public ScorePropensaoController(ScorePropensaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ScorePropensaoResponse>> findAll(@RequestParam(required = false) PrioridadeIa prioridade) {
        return ResponseEntity.ok(service.findAll(prioridade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScorePropensaoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ScorePropensaoResponse> save(@Valid @RequestBody ScorePropensaoRequest request) {
        ScorePropensaoResponse response = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScorePropensaoResponse> update(@PathVariable Long id,
                                                         @Valid @RequestBody ScorePropensaoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
