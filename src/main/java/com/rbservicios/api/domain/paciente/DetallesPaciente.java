package com.rbservicios.api.domain.paciente;

import com.rbservicios.api.domain.direccion.Direccion;
import com.rbservicios.api.domain.medico.Especialidad;

public record DetallesPaciente(
        Long id,
        String nombre,
        String telefono,
        String email,
        String documento,
        Direccion direccion
) {
    public DetallesPaciente(Paciente paciente){
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getTelefono(),
                paciente.getEmail(),
                paciente.getDocumento(),
                paciente.getDireccion()
        );
    }
}
