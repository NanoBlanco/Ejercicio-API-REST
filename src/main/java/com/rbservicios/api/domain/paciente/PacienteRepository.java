package com.rbservicios.api.domain.paciente;

import com.rbservicios.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDocumento(String documento);
    Page<Paciente> findAllByActivoTrue(Pageable control);
}
