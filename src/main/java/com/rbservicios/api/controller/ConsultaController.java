package com.rbservicios.api.controller;

import com.rbservicios.api.domain.consulta.DatosCancelacionConsulta;
import com.rbservicios.api.domain.consulta.DatosDetallesConsulta;
import com.rbservicios.api.domain.consulta.DatosReservaConsulta;
import com.rbservicios.api.domain.consulta.ReservaConsultas;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ReservaConsultas reserva;

    @Transactional
    @PostMapping
    public ResponseEntity<DatosDetallesConsulta> reservar(
            @RequestBody
            @Valid
            DatosReservaConsulta datos) {

        var detalleConsulta = reserva.reservar(datos);


        return ResponseEntity.ok(detalleConsulta);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<String> cancelar(@RequestBody @Valid DatosCancelacionConsulta datos) {
        reserva.cancelar(datos);
        return ResponseEntity.noContent().build();
    }
}
