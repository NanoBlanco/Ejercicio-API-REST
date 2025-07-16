package com.rbservicios.api.controller;

import com.rbservicios.api.domain.medico.DatosActualizaMedico;
import com.rbservicios.api.domain.medico.DatosListaMedico;
import com.rbservicios.api.domain.medico.DatosRegistroMedico;
import com.rbservicios.api.domain.medico.DetallesMedico;
import com.rbservicios.api.domain.medico.Medico;
import com.rbservicios.api.domain.medico.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<DetallesMedico> registar(@RequestBody @Valid DatosRegistroMedico datos, UriComponentsBuilder uriComponentsBuilder){
    	var medico = new Medico(datos);
        medicoRepository.save(medico);
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetallesMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaMedico>> listar(@PageableDefault(size=10, sort={"nombre"}) Pageable control) {
        var page = medicoRepository.findAllByActivoTrue(control).map(DatosListaMedico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallesMedico> obtenerMedico(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetallesMedico(medico));
    }
    
    @Transactional
    @PutMapping
    public ResponseEntity<DetallesMedico> actualizar(@RequestBody @Valid DatosActualizaMedico datos) {
    	var medico = medicoRepository.getReferenceById(datos.id());
    	medico.actualizarInformacion(datos);
    	return ResponseEntity.ok(new DetallesMedico(medico));
    }
    
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
    	var medico = medicoRepository.getReferenceById(id);
    	medico.eliminarMedico(medico);
    	return ResponseEntity.noContent().build();
    }
}
