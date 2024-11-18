package com.example.Cuenta.service;


import com.example.Cuenta.DTO.CuentaDTO;
import com.example.Cuenta.dto.LogDTO;
import com.example.Cuenta.DTO.UsuarioDTO;
import com.example.Cuenta.model.Cuenta;
import com.example.Cuenta.repository.CuentaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.List;
import java.util.Objects;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    private final String SECRET = "Kb2N1Mg0RM1G9IAGOq6vs2sb4UU4ORFmGgqU+ewV+IPa6mVmKB2lPfGWlQAPi0ByYtYnWIQDiXb0Mz3Uf0HZTg";

    @Autowired
    private RestTemplate restTemplate;

    @Value("http://localhost:8025/logs")
    private String APILogs;

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
                Cuenta cuentanueva = cuentaRepository.save(cuenta);
               LogDTO log = new LogDTO("Se registro una nueva cuenta del Usuario " +cuenta.getUserId()+".");
               postLog(new HttpEntity<>(log, headers));
               return cuentanueva;

            }
        }
        return null;
    }

    public void postLog(HttpEntity<LogDTO> entity){
        restTemplate.exchange(
                this.APILogs,
                HttpMethod.POST,
                entity,
                LogDTO.class
        );
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

    public Cuenta setEstadoCuenta(Cuenta cuenta, jakarta.servlet.http.HttpServletRequest request, boolean estado) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {

                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody();


                String role = claims.get("role", String.class);


                if ("ADMIN".equals(role)) {
                    cuenta.setActiva(estado);
                    cuentaRepository.save(cuenta);
                    return cuenta;
                }
            } catch (Exception e) {
                /
                e.printStackTrace();
            }
        }

        return null;
    }

    public Cuenta put(Long id, Cuenta cuenta) {

            Cuenta cuentaEncontrada= cuentaRepository.obtenerPorID(id);
            if(cuentaEncontrada!=null){
                cuentaEncontrada.setMercadoPago(cuenta.getMercadoPago());
                cuentaEncontrada.setUserId(cuenta.getUserId());
               return cuentaRepository.save(cuentaEncontrada);
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
