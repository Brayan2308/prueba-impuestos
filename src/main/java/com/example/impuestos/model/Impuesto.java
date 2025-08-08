package com.example.impuestos.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "impuestos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Impuesto {

    @Id
    private Long sticker;

    private LocalDate fechaMovimiento;
    private LocalDate fechaRecaudo;
    private String tipoHorario;
    private BigInteger nroId;
    private BigDecimal nroForm;
    private BigInteger valor;
}
