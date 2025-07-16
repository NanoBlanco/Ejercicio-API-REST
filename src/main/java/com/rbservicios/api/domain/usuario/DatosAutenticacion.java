package com.rbservicios.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacion(
        @NotBlank
        String correo,
        @NotBlank
        String clave
) {
}
