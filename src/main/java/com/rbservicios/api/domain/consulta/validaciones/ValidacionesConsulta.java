package com.rbservicios.api.domain.consulta.validaciones;

import com.rbservicios.api.domain.consulta.ConsultaRepository;
import com.rbservicios.api.domain.consulta.DatosReservaConsulta;
import com.rbservicios.api.domain.medico.MedicoRepository;
import com.rbservicios.api.domain.paciente.PacienteRepository;
import com.rbservicios.api.infraestructure.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionesConsulta {

    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    ConsultaRepository consultaRepository;

    public void validarHorario(DatosReservaConsulta datos) {
        var fecha = datos.fecha();
        var domingo = fecha.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var apertura = fecha.getHour() < 7;
        var cierre = fecha.getHour() > 18;
        if (domingo || apertura || cierre) {
            throw new ValidacionException("El horario seleccionado está fuera del horario de atención");
        }
    }

    public void validarAnticipacion(DatosReservaConsulta datos) {
        var fecha = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferencia = Duration.between(ahora, fecha).toMinutes();
        if (diferencia < 30) {
            throw new ValidacionException("Las consultas deben ser seleccionadas al menos con 30 minutos de anticipación");
        }
    }

    public void validaPaciente(DatosReservaConsulta datos) {
        var paciente = pacienteRepository.findActivoById(datos.idPaciente());
        if(!paciente) {
            throw new ValidacionException("El paciente no se encuentra activo en el sistema.");
        }
    }

    public void validaMedico(DatosReservaConsulta datos) {
        var medico = medicoRepository.findActivoById(datos.idMedico());
        if(!medico) {
            throw new ValidacionException("El médico no se encuentra activo en el sistema.");
        }
    }

    public void validaConsulta(DatosReservaConsulta datos) {
        var primeraHora = datos.fecha().withHour(7);
        var ultimaHora = datos.fecha().withHour(18);
        var consultas = consultaRepository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primeraHora, ultimaHora);
        if (consultas) {
            throw new ValidacionException("No se puede reservar más de una cita en el mismo día.");
        }
    }

    public void validaHorarioMedico(DatosReservaConsulta datos) {
        var ocupado = consultaRepository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());
        if (ocupado) {
            throw new ValidacionException("El médico ya tiene marcada consulta en el horario seleccionado");
        }
    }
}
