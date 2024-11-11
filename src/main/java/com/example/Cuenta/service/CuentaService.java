package com.example.Cuenta.service;


import com.example.Cuenta.DTO.CuentaDTO;
import com.example.Cuenta.dto.UsuarioDTO;
import com.example.Cuenta.model.Cuenta;
import com.example.Cuenta.repository.CuentaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("http://localhost:8090/auth")
    private String APIUsuario;

    public Cuenta obtener(Long id) {
        Cuenta cuenta =cuentaRepository.obtenerPorID(id);
        return cuenta;
    }

    public Cuenta save(Cuenta cuenta, jakarta.servlet.http.HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.print("Auth header "+authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            HttpHeaders headers = createHeaders(token);
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            ResponseEntity<UsuarioDTO> validationResponseC = validateReponse(APIUsuario +"/"+cuenta.getUserId(), entity);
           if (validationResponseC.getStatusCode() == HttpStatus.OK) {
                cuenta.setActiva(true);
                return cuentaRepository.save(cuenta);
            }
        }
        return null;
    }
    public ResponseEntity<UsuarioDTO> validateReponse(String url, HttpEntity<String> entity) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UsuarioDTO.class);

    }
    public Cuenta delete(Long id) {
        Cuenta cuenta= cuentaRepository.obtenerPorID(id);
        if(cuenta!=null){
            cuentaRepository.delete(cuenta);
        }
        return cuenta;
    }

    public Cuenta setEstadoCuenta(Cuenta cuenta, jakarta.servlet.http.HttpServletRequest request, boolean estado){

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.print("Auth header "+authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            HttpHeaders headers = createHeaders(token);
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            ResponseEntity<UsuarioDTO> validationResponseC = validateReponse(APIUsuario +"/"+cuenta.getUserId(), entity);
            if (validationResponseC.getStatusCode() == HttpStatus.OK && validationResponseC.getBody().getRol().equals("ADMIN")) {
                    cuenta.setActiva(estado);
                    cuentaRepository.save(cuenta);
                    return cuenta;
            }
        }
        return null;
    }

    public Cuenta put(Long id, Cuenta cuenta) {
        if(id.equals(cuenta.getId())){
            Cuenta cuentaEncontrada= cuentaRepository.obtenerPorID(id);
            if(cuentaEncontrada!=null){
                cuentaEncontrada.setMercadoPago(cuenta.getMercadoPago());
                cuentaEncontrada.setUserId(cuenta.getUserId());
                cuentaRepository.save(cuentaEncontrada);
            }
        return cuentaEncontrada;
        }
        return null;
       }

    public List<CuentaDTO> obtenerTodasLasCuentas() {
        return cuentaRepository.obtenerTodasLasCuentas();
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }


}
