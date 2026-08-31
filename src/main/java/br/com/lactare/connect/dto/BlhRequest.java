package br.com.lactare.connect.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public record BlhRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 120, message = "Nome deve ter entre 3 e 120 caracteres")
        @Schema(example = "BLH Santa Casa")
        String nome,
        @NotBlank(message = "Endereço é obrigatório")
        @Size(max = 180, message = "Endereço deve ter no máximo 180 caracteres")
        @Schema(example = "Rua Dr. Cesário Motta Jr., 61")
        String endereco,
        @NotBlank(message = "Bairro é obrigatório")
        @Size(max = 80, message = "Bairro deve ter no máximo 80 caracteres")
        @Schema(example = "Vila Buarque")
        String bairro,
        @NotBlank(message = "Cidade é obrigatória")
        @Size(max = 80, message = "Cidade deve ter no máximo 80 caracteres")
        @Schema(example = "São Paulo")
        String cidade,
        @NotBlank(message = "Estado é obrigatório")
        @Pattern(regexp = "^[A-Za-z]{2}$", message = "Estado deve conter a sigla com 2 letras")
        @Schema(example = "SP")
        String estado,
        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "^[0-9]{5}-?[0-9]{3}$", message = "CEP deve possuir um formato válido")
        @Schema(example = "01021-900")
        String cep,
        @NotBlank(message = "Horário de funcionamento é obrigatório")
        @Size(max = 100, message = "Horário deve ter no máximo 100 caracteres")
        @Schema(example = "Seg-Sex 07:00-17:00")
        String horarioFuncionamento,
        @NotNull(message = "Informe se o BLH aceita coleta domiciliar")
        Boolean aceitaColetaDomiciliar,
        @DecimalMin(value = "-90", message = "Latitude deve ser maior ou igual a -90")
        @DecimalMax(value = "90", message = "Latitude deve ser menor ou igual a 90")
        Double latitude,
        @DecimalMin(value = "-180", message = "Longitude deve ser maior ou igual a -180")
        @DecimalMax(value = "180", message = "Longitude deve ser menor ou igual a 180")
        Double longitude,
        Boolean ativo
) {
}
