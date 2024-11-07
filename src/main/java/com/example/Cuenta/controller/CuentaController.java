package com.example.Cuenta.controller;

import com.example.Cuenta.DTO.CuentaDTO;
import com.example.Cuenta.model.Cuenta;
import com.example.Cuenta.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cuenta")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarCuentas(){
        List<CuentaDTO> cuentas=cuentaService.obtenerTodasLasCuentas();
        if(!cuentas.isEmpty()){
            return ResponseEntity.ok(cuentas);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuenta(@PathVariable Long id) {
        Cuenta cuentadto=cuentaService.obtener(id);
        if(cuentadto!=null){
            return ResponseEntity.ok(cuentadto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> cambiarDatos(@PathVariable Long id, @RequestBody Cuenta cuenta){
      Cuenta cuentaDatos=cuentaService.put(id, cuenta);
      if(cuentaDatos!=null){
          return ResponseEntity.ok(cuentaDatos);
      }
      return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta, jakarta.servlet.http.HttpServletRequest request) {
        Cuenta cuentaRespuesta=cuentaService.save(cuenta, request);
        if(cuentaRespuesta!=null){
            return ResponseEntity.ok(cuenta);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cuenta> eliminarCuenta(@PathVariable Long id) {
        Cuenta cuenta=cuentaService.delete(id);
        if(cuenta!=null){
            return ResponseEntity.ok(cuenta);
        }
        return ResponseEntity.notFound().build();
    }

}
