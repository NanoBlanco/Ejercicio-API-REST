package com.rbservicios.api.domain.medico;

import com.rbservicios.api.domain.direccion.DatosDireccion;

import jakarta.validation.constraints.NotNull;

public record DatosActualizaMedico(
		@NotNull
		Long id,
		String nombre,
		String telefono,
		DatosDireccion direccion
		) {

}
