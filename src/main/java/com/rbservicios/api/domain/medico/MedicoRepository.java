package com.rbservicios.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
	Page<Medico> findAllByActivoTrue(Pageable control);
	Optional<Medico> findByDocumento(String documento);
	
}
