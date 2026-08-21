package br.com.lactare.connect.controller;

import br.com.lactare.connect.dto.NutrizRequest;
import br.com.lactare.connect.dto.NutrizResponse;
import br.com.lactare.connect.entity.NutrizStatus;
import br.com.lactare.connect.service.NutrizService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/nutrizes")
@Tag(name = "Nutrizes", description = "Cadastro e acompanhamento de nutrizes/doadoras")
public class NutrizController {

    private final NutrizService service;

    public NutrizController(NutrizService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista nutrizes")
    public ResponseEntity<List<NutrizResponse>> findAll(@RequestParam(required = false) NutrizStatus status) {
        return ResponseEntity.ok(service.findAll(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutrizResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<NutrizResponse> save(@Valid @RequestBody NutrizRequest request) {
        NutrizResponse response = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NutrizResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody NutrizRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
