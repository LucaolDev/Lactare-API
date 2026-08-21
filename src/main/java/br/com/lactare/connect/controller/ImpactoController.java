package br.com.lactare.connect.controller;

import br.com.lactare.connect.dto.ImpactoResponse;
import br.com.lactare.connect.service.ImpactoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/nutrizes")
@Tag(name = "Impacto", description = "Impacto individual das doações da nutriz")
public class ImpactoController {

    private final ImpactoService service;

    public ImpactoController(ImpactoService service) {
        this.service = service;
    }

    @GetMapping("/{id}/impacto")
    public ResponseEntity<ImpactoResponse> findByNutrizId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByNutrizId(id));
    }
}
