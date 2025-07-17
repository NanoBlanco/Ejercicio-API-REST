package com.rbservicios.api.domain.paciente;

import com.rbservicios.api.domain.medico.Medico;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDocumento(String documento);
    Page<Paciente> findAllByActivoTrue(Pageable control);

    @Query("SELECT p.activo FROM Paciente p WHERE p.id = :id")
   boolean findActivoById(@NotNull Long id);
}
