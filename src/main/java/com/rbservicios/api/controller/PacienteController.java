package com.rbservicios.api.controller;

import com.rbservicios.api.domain.paciente.*;
import com.rbservicios.api.domain.paciente.PacienteRepository;
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
@RequestMapping("/pacientes")
public class PacienteController {
    
    @Autowired
    PacienteRepository pacienteRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<DetallesPaciente> registar(
            @RequestBody
            @Valid
            DatosRegistroPaciente datos,
            UriComponentsBuilder uriComponentsBuilder){
        var paciente = new Paciente(datos);
        pacienteRepository.save(paciente);
        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetallesPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaPaciente>> listar(@PageableDefault(size=10, sort={"nombre"}) Pageable control) {
        var page = pacienteRepository.findAllByActivoTrue(control).map(DatosListaPaciente::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallesPaciente> obtenerPaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetallesPaciente(paciente));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<DetallesPaciente> actualizar(@RequestBody @Valid DatosActualizaPaciente datos) {
        var paciente = pacienteRepository.getReferenceById(datos.id());
        paciente.actualizarInformacion(datos);
        return ResponseEntity.ok(new DetallesPaciente(paciente));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.eliminarPaciente(paciente);
        return ResponseEntity.noContent().build();
    }
}
