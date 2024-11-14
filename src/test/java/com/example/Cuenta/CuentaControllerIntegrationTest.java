package com.example.Cuenta;
import DTO_TEST.UsuarioRegistroDTO;
import DTO_TEST.UsuarioLoginDTO;
import com.example.Cuenta.model.Cuenta;
import com.example.Cuenta.service.CuentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Permite mantener estado compartido entre pruebas
public class CuentaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private String authToken;
    private int idUsuario;

    @BeforeAll
    public void setUp(@Autowired CuentaService service) throws Exception {
        registrarYAutenticarUsuario();
    }

    private void registrarYAutenticarUsuario() throws Exception {
        if (authToken == null) {
            UsuarioRegistroDTO nuevoUsuario = new UsuarioRegistroDTO("password", "valen634@gmail.com", "ADMIN", "valen", "Castillo", 1, 2L, 123456789L);
            ObjectMapper objectMapper = new ObjectMapper();
            String registroJson = objectMapper.writeValueAsString(nuevoUsuario);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(registroJson, headers);
            String registroUrl = "http://localhost:8090/auth/registro";
            com.example.Cuenta.DTO.UsuarioDTO u = restTemplate.postForObject(registroUrl, requestEntity, com.example.Cuenta.DTO.UsuarioDTO.class);
            idUsuario = u.getId();
            UsuarioLoginDTO loginUsuario = new UsuarioLoginDTO("valen634@gmail.com", "password");
            loginUsuario.setRol("ADMIN");
            String loginJson = objectMapper.writeValueAsString(loginUsuario);
            HttpHeaders loginHeaders = new HttpHeaders();
            loginHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> loginRequestEntity = new HttpEntity<>(loginJson, loginHeaders);
            String authUrl = "http://localhost:8090/auth/login";
            authToken = restTemplate.postForObject(authUrl, loginRequestEntity, String.class);
        }
    }

    @Test
    public void registrarCuentaTest() throws Exception {
        Cuenta cuenta = new Cuenta(idUsuario, 233);
        ObjectMapper objectMapper = new ObjectMapper();
        String cuentaJson = objectMapper.writeValueAsString(cuenta);

        mockMvc.perform(MockMvcRequestBuilders.post("/Cuenta")
                        .content(cuentaJson)
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void ListarCuentasTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Cuenta")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void ObtenerCuentaPorIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Cuenta/9")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void EliminarCuentaTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/Cuenta/6")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void DeshabilitarCuentaTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/Cuenta/3/deshabilitar")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void HabilitarCuentaTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/Cuenta/2/habilitar")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    }




