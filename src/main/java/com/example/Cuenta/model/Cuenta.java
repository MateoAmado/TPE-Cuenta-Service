package com.example.Cuenta.model;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Data
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int userId; // Identificador del usuario de tu sistema

    private float mercadoPago;

    @Value(value = "true")
    private boolean activa;
    public Cuenta(){}

    public Cuenta(int userId, float mercadoPago){
        this.userId = userId;
        this.mercadoPago = mercadoPago;
        this.activa = true;
    }


}

