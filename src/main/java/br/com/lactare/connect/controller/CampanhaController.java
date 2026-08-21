package br.com.lactare.connect.controller;

import br.com.lactare.connect.dto.CampanhaRequest;
import br.com.lactare.connect.dto.CampanhaResponse;
import br.com.lactare.connect.service.CampanhaService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/campanhas")
@Tag(name = "Campanhas", description = "Campanhas de outreach e notificações")
public class CampanhaController {

    private final CampanhaService service;

    public CampanhaController(CampanhaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CampanhaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampanhaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CampanhaResponse> save(@Valid @RequestBody CampanhaRequest request) {
        CampanhaResponse response = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampanhaResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody CampanhaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PostMapping("/{id}/disparar")
    public ResponseEntity<CampanhaResponse> disparar(@PathVariable Long id) {
        return ResponseEntity.ok(service.disparar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
