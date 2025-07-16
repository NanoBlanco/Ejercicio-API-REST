package com.rbservicios.api.domain.paciente;

import com.rbservicios.api.domain.direccion.Direccion;

import com.rbservicios.api.domain.medico.DatosActualizaMedico;
import com.rbservicios.api.domain.medico.Medico;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Paciente {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String telefono;
    private String documento;
    private String email;
    private Boolean activo;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datos) {
        this.id = null;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.activo = true;
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarInformacion(@Valid DatosActualizaPaciente datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.telefono() != null) {
            this.telefono = datos.telefono();
        }
        if (datos.direccion() != null) {
            this.direccion.actualizarDireccion(datos.direccion());
        }
    }

    public void eliminarPaciente(Paciente paciente) {
        this.activo = false;

    }

}
