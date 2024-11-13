package com.example.Cuenta.dto;


import java.time.LocalDate;

public class LogDTO {
    private String descripcion;

    public LogDTO() {
    }

    public LogDTO(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
