package com.example.impuestos.repository;

import com.example.impuestos.model.Impuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {

    List<Impuesto> findByFechaMovimiento(LocalDate fecha);

    @Query("SELECT i.tipoHorario, COUNT(i), SUM(i.valor) FROM Impuesto i WHERE i.tipoHorario = ?1 GROUP BY i.tipoHorario")
    List<Object[]> resumenPorHorario(String tipoHorario);
}
