package com.rbservicios.api.domain.consulta;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);
    boolean existsByPacienteIdAndFechaBetween(@NotNull Long aLong, LocalDateTime primeraHora, LocalDateTime ultimaHora);
}
