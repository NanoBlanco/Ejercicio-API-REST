package com.rbservicios.api.controller;

import com.rbservicios.api.domain.usuario.DatosAutenticacion;
import com.rbservicios.api.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name="bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    public ResponseEntity registra(@RequestBody @Valid DatosAutenticacion datos)  {
        return ResponseEntity.ok().build();
    }

}
