package com.rbservicios.api.infraestructure.exceptions;

public class ValidacionException extends RuntimeException {

    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
