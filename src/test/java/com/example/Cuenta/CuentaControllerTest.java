package com.example.Cuenta;

import com.example.Cuenta.DTO.CuentaDTO;
import com.example.Cuenta.controller.CuentaController;
import com.example.Cuenta.controller.NoEncontrado;
import com.example.Cuenta.model.Cuenta;
import com.example.Cuenta.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;


import java.util.ArrayList;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.*;


    public class CuentaControllerTest {


        @Mock

        private CuentaService cuentaService;


        @InjectMocks

        private CuentaController cuentaController;


        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }


       @Test
    public void testListarCuentas_SinCuentas() {

    List<CuentaDTO> cuentas = new ArrayList<>();

    when(cuentaService.obtenerTodasLasCuentas()).thenReturn(cuentas);

    NoEncontrado exception = assertThrows(NoEncontrado.class, () -> {
        cuentaController.listarCuentas();
    });
    assertEquals("No se encontraron cuentas.", exception.getMessage());
    verify(cuentaService).obtenerTodasLasCuentas();
}


    @Test
    public void testListarCuentas_ConCuentas() {
        Cuenta cuenta = new Cuenta();

        cuenta.setId(1L);


        CuentaDTO cuentaDTO = new CuentaDTO();

        cuentaDTO.setId(cuenta.getId());


        List<CuentaDTO> cuentas = new ArrayList<>();

        cuentas.add(cuentaDTO);

        when(cuentaService.obtenerTodasLasCuentas()).thenReturn(cuentas);


        ResponseEntity<List<CuentaDTO>> response = cuentaController.listarCuentas();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(cuentaDTO.getId(), response.getBody().get(0).getId());
        verify(cuentaService).obtenerTodasLasCuentas();
    }


        @Test

        public void testGetCuenta_Exists() {
            Cuenta cuenta = new Cuenta();

            cuenta.setId(1L);

            when(cuentaService.obtener(anyLong())).thenReturn(cuenta);


            ResponseEntity<Cuenta> response = cuentaController.getCuenta(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());

            assertEquals(cuenta, response.getBody());

            verify(cuentaService).obtener(1L);

        }


        @Test
        public void testGetCuenta_NotExists() {
            when(cuentaService.obtener(anyLong())).thenReturn(null);
            Exception exception = assertThrows(NoEncontrado.class, () -> {

                cuentaController.getCuenta(1L);

            });
            assertEquals("Cuenta no encontrada con ID: 1", exception.getMessage());
        }


        @Test
        public void testCrearCuenta() {
            Cuenta cuenta = new Cuenta();
            cuenta.setId(1L);
            when(cuentaService.save(any(Cuenta.class), any())).thenReturn(cuenta);
            ResponseEntity<Cuenta> response = cuentaController.crearCuenta(cuenta, null);

            assertEquals(HttpStatus.OK, response.getStatusCode());

            assertEquals(cuenta, response.getBody());

            verify(cuentaService).save(cuenta, null);
        }


        @Test
        public void testEliminarCuenta_Exists() {
            Cuenta cuenta = new Cuenta();

            cuenta.setId(1L);

            when(cuentaService.delete(anyLong())).thenReturn(cuenta);

            ResponseEntity<Cuenta> response = cuentaController.eliminarCuenta(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());

            assertEquals(cuenta, response.getBody());

            verify(cuentaService).delete(1L);
        }


        @Test
        public void testEliminarCuenta_NotExists() {
            when(cuentaService.delete(anyLong())).thenReturn(null);
            Exception exception = assertThrows(NoEncontrado.class, () -> {
                cuentaController.eliminarCuenta(1L);
            });
            assertEquals("Cuenta no encontrada con ID: 1", exception.getMessage());
        }
}
