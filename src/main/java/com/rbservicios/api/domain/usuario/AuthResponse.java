package com.rbservicios.api.domain.usuario;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username","Jwt","status"})
public record AuthResponse(
        String username,
        String Jwt,
        boolean status
) {
}
