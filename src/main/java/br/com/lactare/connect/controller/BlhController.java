package br.com.lactare.connect.controller;

import br.com.lactare.connect.dto.BlhRequest;
import br.com.lactare.connect.dto.BlhMatchingResponse;
import br.com.lactare.connect.dto.BlhResponse;
import br.com.lactare.connect.service.BlhService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/blhs")
@Tag(name = "BLHs", description = "Bancos de leite humano e matching geográfico")
public class BlhController {

    private final BlhService service;

    public BlhController(BlhService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista BLHs ativos, opcionalmente filtrados por cidade")
    public ResponseEntity<List<BlhResponse>> findAll(@RequestParam(required = false) String cidade) {
        return ResponseEntity.ok(service.findAll(cidade));
    }

    @GetMapping("/matching")
    @Operation(summary = "Encontra os BLHs ativos mais próximos de uma localização")
    public ResponseEntity<List<BlhMatchingResponse>> findNearest(
            @RequestParam @DecimalMin(value = "-90") @DecimalMax(value = "90") double latitude,
            @RequestParam @DecimalMin(value = "-180") @DecimalMax(value = "180") double longitude,
            @RequestParam(defaultValue = "3") @Min(1) @Max(20) int limite) {
        return ResponseEntity.ok(service.findNearest(latitude, longitude, limite));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlhResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BlhResponse> save(@Valid @RequestBody BlhRequest request) {
        BlhResponse response = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlhResponse> update(@PathVariable Long id,
                                              @Valid @RequestBody BlhRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
