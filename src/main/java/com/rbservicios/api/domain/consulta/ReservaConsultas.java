package com.rbservicios.api.domain.consulta;

import com.rbservicios.api.domain.consulta.validaciones.ValidacionesConsulta;
import com.rbservicios.api.domain.medico.Medico;
import com.rbservicios.api.domain.medico.MedicoRepository;
import com.rbservicios.api.domain.paciente.PacienteRepository;
import com.rbservicios.api.infraestructure.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ValidacionesConsulta validacionesConsulta;

    public DatosDetallesConsulta reservar(DatosReservaConsulta datos) {
        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("No existe un médico con el Id: "+datos.idMedico());
        }

        if(datos.idPaciente() != null && !pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("No existe un paciente con el Id: "+datos.idPaciente());
        }

        var medico = elegirMedico(datos);
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        // validaciones
        validacionesConsulta.validarHorario(datos);
        validacionesConsulta.validarAnticipacion(datos);
        validacionesConsulta.validaConsulta(datos);
        validacionesConsulta.validaMedico(datos);
        validacionesConsulta.validaPaciente(datos);
        validacionesConsulta.validaHorarioMedico(datos);

        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);
        consultaRepository.save(consulta);
        return new DatosDetallesConsulta(consulta);
    }

    private Medico elegirMedico(DatosReservaConsulta datos) {
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad() == null){
            throw new ValidacionException("Es requerido la especialidad o el médico");
        }
        return medicoRepository.elegirMedicoAleatorio(datos.especialidad(), datos.fecha());
    }

    public void cancelar(DatosCancelacionConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionException("Id de la consulta informado no existe!");
        }
        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
