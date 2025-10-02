package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CuotaDTO {
    private Long cuotaId;
    private Long planPagoId;
    private Integer numeroCuota;
    private BigDecimal monto;
    private LocalDate fechaVencimiento;
    private LocalDate fechaPago;
    private String estado = "PENDIENTE";

    // Para respuestas
    private PlanPagoDTO planPago;

    // Campos calculados
    private Boolean vencida = false;

    public CuotaDTO() {}

    public CuotaDTO(Integer numeroCuota, BigDecimal monto, LocalDate fechaVencimiento) {
        this.numeroCuota = numeroCuota;
        this.monto = monto;
        this.fechaVencimiento = fechaVencimiento;
    }
}