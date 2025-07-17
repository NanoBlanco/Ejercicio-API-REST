package com.rbservicios.api.controller;

import com.rbservicios.api.domain.consulta.DatosDetallesConsulta;
import com.rbservicios.api.domain.consulta.DatosReservaConsulta;
import com.rbservicios.api.domain.consulta.ReservaConsultas;
import com.rbservicios.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DatosReservaConsulta> reservaJson;

    @Autowired
    private JacksonTester<DatosDetallesConsulta> detalleJson;

    @MockBean
    private ReservaConsultas reservaConsultas;

    @Test
    @DisplayName("Prueba de reserva sin datos")
    @WithMockUser
    void reservar_escenario_1() throws Exception {
        var response = mockMvc.perform(post("/consultas")).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Prueba de reserva con datos")
    @WithMockUser
    void reservar_escenario_2() throws Exception {

        var fecha = LocalDateTime.now().plusHours(2);
        var datosDetalle = new DatosDetallesConsulta(1L, 1L, 2L, fecha);
        when(reservaConsultas.reservar(any())).thenReturn(datosDetalle);
        var response = mockMvc
                .perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaJson.write(
                                new DatosReservaConsulta(1L, 2L, fecha, Especialidad.DERMATOLOGIA)
                        ).getJson())
                )
                .andReturn()
                .getResponse();
        var esperado = detalleJson.write(datosDetalle).getJson();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), esperado);
    }
}