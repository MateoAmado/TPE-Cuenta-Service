package com.example.Cuenta;
import com.example.Cuenta.DTO.CuentaDTO;
import com.example.Cuenta.controller.CuentaController;
import com.example.Cuenta.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest

@AutoConfigureMockMvc

public class CuentaControllerIntegrationTest {


        @Autowired

        private MockMvc mockMvc;


        @Mock

        private CuentaService cuentaService;


        @InjectMocks

        private CuentaController cuentaController;


        //No funciona porque falta autenticarlo, da 403 Sin permisos
        @Test
        public void testListarCuentas_ConCuentas() throws Exception {

            CuentaDTO cuentaDTO = new CuentaDTO();

            cuentaDTO.setId(1L);

            List<CuentaDTO> cuentas = new ArrayList<>();

            cuentas.add(cuentaDTO);

            when(cuentaService.obtenerTodasLasCuentas()).thenReturn(cuentas);

            mockMvc.perform(get("/Cuenta/"+cuentaDTO.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1L));


        }
    }


