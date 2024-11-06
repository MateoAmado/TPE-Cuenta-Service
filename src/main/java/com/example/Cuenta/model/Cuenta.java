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

    private Long userId; // Identificador del usuario de tu sistema

    private String mercadoPagoAccessToken;
    private String mercadoPagoRefreshToken;
    private String mercadoPagoUserId;

    public Cuenta(){}

    public Cuenta(Long userId){
        this.userId = userId;
    }
}

