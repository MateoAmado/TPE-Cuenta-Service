package com.example.Cuenta.controller;

import com.example.Cuenta.DTO.CuentaDTO;
import com.example.Cuenta.model.Cuenta;
import com.example.Cuenta.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cuenta")
@Tag(name = "Cuenta", description = "API para gestionar cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @Operation(summary = "Listar todas las cuentas", description = "Obtiene una lista de todas las cuentas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuentas encontradas exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna cuenta"),
            })
    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarCuentas() {
        List<CuentaDTO> cuentas = cuentaService.obtenerTodasLasCuentas();
        if (cuentas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cuentas);
    }

    @Operation(summary = "Obtener una cuenta por ID", description = "Obtiene una cuenta específica mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encuentra la cuenta exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna cuenta"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuenta(@Parameter(description = "ID de la cuenta a obtener", required = true) @PathVariable Long id) {
        Cuenta cuenta = cuentaService.obtener(id);
        if (cuenta == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cuenta);
    }

    @Operation(summary = "Actualizar una cuenta", description = "Actualiza los datos de una cuenta existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modificó exitosamente la cuenta"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna cuenta"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> cambiarDatos(
            @Parameter(description = "ID de la cuenta a actualizar", required = true) @PathVariable Long id,
            @RequestBody Cuenta cuenta) {
        Cuenta cuentaDatos = cuentaService.obtener(id);
        if (cuentaDatos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cuentaService.put(id, cuenta);
        return ResponseEntity.ok(cuentaDatos);
    }

    @Operation(summary = "Deshabilita una cuenta", description = "Deshabilita una cuenta existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se deshabilitó exitosamente la cuenta"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna cuenta"),
    })
    @PutMapping("/{id}/deshabilitar")
    public ResponseEntity<Cuenta> deshabilitarCuenta(@PathVariable Long id, HttpServletRequest request){
        Cuenta cuenta = cuentaService.obtener(id);
        if(cuenta != null){
            cuentaService.setEstadoCuenta(cuenta, request, false);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Habilita una cuenta", description = "Habilita una cuenta existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se habilitó exitosamente la cuenta"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna cuenta"),
    })
    @PutMapping("/{id}/habilitar")
    public ResponseEntity<Cuenta> habilitarCuenta(@PathVariable Long id, HttpServletRequest request){
        Cuenta cuenta = cuentaService.obtener(id);
        if(cuenta != null){
            cuentaService.setEstadoCuenta(cuenta, request, true);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Crear una nueva cuenta", description = "Crea una nueva cuenta en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se crea una cuenta exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna cuenta")

    })
    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(
            @RequestBody Cuenta cuenta,
            HttpServletRequest request) {
        Cuenta cuentaRespuesta = cuentaService.save(cuenta, request);
        if(cuentaRespuesta == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cuentaRespuesta, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar una cuenta", description = "Elimina una cuenta específica mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la cuenta"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Cuenta> eliminarCuenta(
            @Parameter(description = "ID de la cuenta a eliminar", required = true) @PathVariable Long id) {
        Cuenta cuenta = cuentaService.delete(id);
        if (cuenta == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
