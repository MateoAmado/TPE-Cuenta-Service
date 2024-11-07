package com.example.Cuenta.repository;

import com.example.Cuenta.DTO.CuentaDTO;
import com.example.Cuenta.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("SELECT c FROM Cuenta c WHERE c.id=:id")
    public Cuenta obtenerPorID(@Param("id") Long id);

    @Query("SELECT new com.example.Cuenta.DTO.CuentaDTO(c.id, c.userId, c.mercadoPago) from Cuenta c")
    List<CuentaDTO> obtenerTodasLasCuentas();

    @Query("DELETE FROM Cuenta c WHERE c.id=:id")
    public Cuenta delete(@Param("id") Long id);
}
