package com.example.Cuenta;


import com.example.Cuenta.model.Cuenta;
import com.example.Cuenta.repository.CuentaRepository;
import com.example.Cuenta.service.CuentaService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CuentaServiceTest {

    @Mock

    private CuentaRepository cuentaRepository;


    @Mock

    private RestTemplate restTemplate;


    @Mock

    private HttpServletRequest request;


    @InjectMocks

    private CuentaService cuentaService;


    public CuentaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testSaveCuenta_ValidToken() {
        Cuenta cuenta = new Cuenta();
        cuenta.setUserId(1);
        String token = "tokenValido";
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);
        Cuenta result = cuentaService.save(cuenta, request);

        assertNotNull(result);
        assertEquals(cuenta.getUserId(), result.getUserId());
        verify(cuentaRepository).save(cuenta);
    }


    @Test
    public void testSaveCuenta_InvalidToken() {
        Cuenta cuenta = new Cuenta();

        cuenta.setUserId(1);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        Cuenta result = cuentaService.save(cuenta, request);
        assertNull(result);
        verify(cuentaRepository, never()).save(any());
    }


    @Test
    public void testDeleteCuenta_Exists() {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);

        when(cuentaRepository.obtenerPorID(1L)).thenReturn(cuenta);

        Cuenta result = cuentaService.delete(1L);

        assertNotNull(result);

        verify(cuentaRepository).delete(cuenta);
    }


    @Test
    public void testBorrarCuentaQueNoExiste() {
        when(cuentaRepository.obtenerPorID(1L)).thenReturn(null);
        Cuenta resultado = cuentaService.delete(1L);

        assertNull(resultado);

        verify(cuentaRepository, never()).delete((Long) any());
    }
}
