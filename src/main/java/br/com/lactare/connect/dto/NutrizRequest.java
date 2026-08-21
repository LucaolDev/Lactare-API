package br.com.lactare.connect.dto;

import br.com.lactare.connect.entity.NutrizStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NutrizRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 120, message = "Nome deve ter entre 3 e 120 caracteres")
        @Schema(example = "Ana Silva")
        String nome,
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^[0-9()+ -]{10,20}$", message = "Telefone deve possuir um formato válido")
        @Schema(example = "11999998888")
        String telefone,
        @Email(message = "E-mail deve possuir um formato válido")
        @Size(max = 160, message = "E-mail deve ter no máximo 160 caracteres")
        @Schema(example = "ana@email.com")
        String email,
        @NotBlank(message = "Cidade é obrigatória")
        @Size(max = 80, message = "Cidade deve ter no máximo 80 caracteres")
        @Schema(example = "São Paulo")
        String cidade,
        @NotBlank(message = "Estado é obrigatório")
        @Pattern(regexp = "^[A-Za-z]{2}$", message = "Estado deve conter a sigla com 2 letras")
        @Schema(example = "SP")
        String estado,
        @NotNull(message = "Semanas pós-parto é obrigatório")
        @Min(value = 0, message = "Semanas pós-parto não pode ser negativa")
        @Max(value = 42, message = "Semanas pós-parto deve ser no máximo 42")
        @Schema(example = "6")
        Integer semanasPosParto,
        @NotNull(message = "Consentimento LGPD é obrigatório")
        @AssertTrue(message = "É necessário aceitar o consentimento LGPD")
        @Schema(example = "true")
        Boolean consentimentoLgpd,
        NutrizStatus status
) {
}
