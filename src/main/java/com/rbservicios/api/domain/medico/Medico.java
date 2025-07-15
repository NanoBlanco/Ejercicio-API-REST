package com.rbservicios.api.domain.medico;

import com.rbservicios.api.domain.direccion.Direccion;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="medicos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String telefono;
    private String documento;
    private String email;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datos) {
        this.id = null;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.activo = true;
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.especialidad = datos.especialidad();
        this.direccion = new Direccion(datos.direccion());
    }

	public void actualizarInformacion(@Valid DatosActualizaMedico datos) {
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

	public void eliminarMedico(Medico medico) {
		this.activo = false;
		
	}
}
