package com.rbservicios.api.domain.medico;

import com.rbservicios.api.domain.direccion.Direccion;

public record DetallesMedico(
		Long id,
		String nombre,
		String telefono,
		String email,
		String documento,
		Especialidad especialidad,
		Direccion direccion
		) {
	public DetallesMedico(Medico medico) {
		this(
				medico.getId(),
				medico.getNombre(),
				medico.getTelefono(),
				medico.getEmail(),
				medico.getDocumento(),
				medico.getEspecialidad(),
				medico.getDireccion());
	}

}
