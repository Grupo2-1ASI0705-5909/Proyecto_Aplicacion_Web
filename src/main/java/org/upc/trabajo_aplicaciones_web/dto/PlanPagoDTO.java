package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PlanPagoDTO {
    private Long planPagoId;
    private Long transaccionId;
    private Integer numeroCuotas;
    private BigDecimal montoPorCuota;
    private BigDecimal interes;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private TransaccionDTO transaccion;

    public PlanPagoDTO() {}

    public PlanPagoDTO(Integer numeroCuotas, BigDecimal montoPorCuota, BigDecimal interes) {
        this.numeroCuotas = numeroCuotas;
        this.montoPorCuota = montoPorCuota;
        this.interes = interes;
        this.fechaInicio = LocalDate.now();
    }
}