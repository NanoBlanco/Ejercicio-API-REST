package com.rbservicios.api.domain.medico;

import com.rbservicios.api.domain.consulta.Consulta;
import com.rbservicios.api.domain.direccion.DatosDireccion;
import com.rbservicios.api.domain.paciente.DatosRegistroPaciente;
import com.rbservicios.api.domain.paciente.Paciente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Verifica que médico de forma aleatoria esté ocupado")
    void elegirMedicoAleatorio() {
        var fecha = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = registrarMedico("Medico 1", "medico@correo.com","13245678", Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("Paciente 1", "paciente@correo.com", "13245687");
        registrarConsulta(medico, paciente, fecha);

        var medicoDisponible = repository.elegirMedicoAleatorio(Especialidad.CARDIOLOGIA, fecha);

        assertNull(medicoDisponible);
    }

    @Test
    @DisplayName("Verifica la elección de un médico de forma aleatoria")
    void elegirMedicoAleatorio2() {
        var fecha = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = registrarMedico("Medico 1", "medico@correo.com","13245678", Especialidad.CARDIOLOGIA);

        var medicoDisponible = repository.elegirMedicoAleatorio(Especialidad.CARDIOLOGIA, fecha);

        assertEquals(medicoDisponible, medico);
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad esp) {
        var nuevo = new Medico(datosMedico(nombre, email, documento, esp));
        em.persist(nuevo);
        return nuevo;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var pac = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(pac);
        return pac;
    }

    private void registrarConsulta(Medico med, Paciente pac, LocalDateTime fecha) {
        em.persist(new Consulta(null, med, pac, fecha, null));
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad esp) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "123456789",
                documento,
                esp,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "12345678",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "calle Y",
                "123",
                "",
                "Paso Hondo",
                "Quilpué",
                "2430000",
                "Valpo"
        );
    }
}