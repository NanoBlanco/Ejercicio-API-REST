package com.rbservicios.api.domain.medico;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
	Page<Medico> findAllByActivoTrue(Pageable control);
	Optional<Medico> findByDocumento(String documento);

	@Query("SELECT m FROM Medico m WHERE m.especialidad = :especialidad AND m.activo = TRUE and m.id not in (SELECT c.medico.id FROM Consulta c WHERE c.fecha = :fecha) order by rand() limit 1")
    Medico elegirMedicoAleatorio(Especialidad especialidad, LocalDateTime fecha);

	@Query("SELECT m.activo FROM Medico m WHERE m.id = :id")
    boolean findActivoById(Long id);
}
