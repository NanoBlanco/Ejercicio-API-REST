package com.rbservicios.api.controller;

import com.rbservicios.api.domain.usuario.AutenticacionService;
import com.rbservicios.api.domain.usuario.AuthResponse;
import com.rbservicios.api.domain.usuario.DatosAutenticacion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    @Autowired
    AutenticacionService autenticacionService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> inicioSesion(@RequestBody @Valid DatosAutenticacion datos) {
        return new ResponseEntity<>(this.autenticacionService.loginUser(datos), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid DatosAutenticacion datos){
        return new ResponseEntity<>(this.autenticacionService.crearUsuario(datos), HttpStatus.CREATED);
    }
}
