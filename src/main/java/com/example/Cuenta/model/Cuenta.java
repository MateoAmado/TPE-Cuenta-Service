package com.example.Cuenta.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int userId; // Identificador del usuario de tu sistema

    private float mercadoPago;

    public Cuenta(){}

    public Cuenta(int userId, float mercadoPago){
        this.userId = userId;
        this.mercadoPago = mercadoPago;
    }
}

