package com.example.Cuenta.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CuentaDTO {

    private Long id;
    private int userId; // Identificador del usuario de tu sistema
    private float mercadoPago;

    public CuentaDTO(Long id, int userId, float mercadoPago) {
        this.id = id;
        this.userId = userId;
        this.mercadoPago = mercadoPago;
    }
}
