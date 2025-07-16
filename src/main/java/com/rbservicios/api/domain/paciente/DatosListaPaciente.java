package com.rbservicios.api.domain.paciente;

public record DatosListaPaciente(
        Long id,
        String nombre,
        String telefono,
        String email,
        String documento
) {
    public DatosListaPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getTelefono(),
                paciente.getEmail(),
                paciente.getDocumento()
        );
    }
}
