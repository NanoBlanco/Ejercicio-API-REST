package com.rbservicios.api.domain.paciente;

import com.rbservicios.api.domain.direccion.DatosDireccion;
import jakarta.validation.constraints.NotNull;

public record DatosActualizaPaciente(
        @NotNull
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion
) {
}
