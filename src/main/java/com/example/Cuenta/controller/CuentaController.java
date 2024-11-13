package com.example.Cuenta.controller;

import com.example.Cuenta.DTO.CuentaDTO;
import com.example.Cuenta.model.Cuenta;
import com.example.Cuenta.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarCuentas() {
        List<CuentaDTO> cuentas = cuentaService.obtenerTodasLasCuentas();
        if (cuentas.isEmpty()) {
            throw new NoEncontrado("No se encontraron cuentas.");
        }
        return ResponseEntity.ok(cuentas);
    }

    @Operation(summary = "Obtener una cuenta por ID", description = "Obtiene una cuenta específica mediante su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuenta(
            @Parameter(description = "ID de la cuenta a obtener", required = true) @PathVariable Long id) {
        Cuenta cuenta = cuentaService.obtener(id);
        if (cuenta == null) {
            throw new NoEncontrado("Cuenta no encontrada con ID: " + id);
        }
        return ResponseEntity.ok(cuenta);
    }

    @Operation(summary = "Actualizar una cuenta", description = "Actualiza los datos de una cuenta existente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> cambiarDatos(
            @Parameter(description = "ID de la cuenta a actualizar", required = true) @PathVariable Long id,
            @RequestBody Cuenta cuenta) {
        Cuenta cuentaDatos = cuentaService.put(id, cuenta);
        if (cuentaDatos == null) {
            throw new NoEncontrado("Cuenta no encontrada con ID: " + id);
        }
        return ResponseEntity.ok(cuentaDatos);
    }

    @PutMapping("/{id}/deshabilitar")
    public ResponseEntity<Cuenta> deshabilitarCuenta(@PathVariable Long id, HttpServletRequest request){
        Cuenta cuenta = cuentaService.obtener(id);
        if(cuenta != null){
            cuentaService.setEstadoCuenta(cuenta, request, false);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

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
    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(
            @RequestBody Cuenta cuenta,
            HttpServletRequest request) {
        Cuenta cuentaRespuesta = cuentaService.save(cuenta, request);
        return ResponseEntity.ok(cuentaRespuesta);
    }

    @Operation(summary = "Eliminar una cuenta", description = "Elimina una cuenta específica mediante su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Cuenta> eliminarCuenta(
            @Parameter(description = "ID de la cuenta a eliminar", required = true) @PathVariable Long id) {
        Cuenta cuenta = cuentaService.delete(id);
        if (cuenta == null) {
            throw new NoEncontrado("Cuenta no encontrada con ID: " + id);
        }
        return ResponseEntity.ok(cuenta);
    }
}
