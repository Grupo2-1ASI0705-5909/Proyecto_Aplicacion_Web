package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cuota")
@Data
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELACIONES
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_pago_id", nullable = false)
    private PlanPago planPago;

    // DATOS DE LA CUOTA
    @Column(nullable = false)
    private Integer numeroCuota;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false)
    private LocalDate fechaPago;

    @Column(nullable = false, length = 20)
    private String estado = "PENDIENTE"; // PENDIENTE, PAGADA, VENCIDA

    // MÉTODO PARA VALIDAR SI ESTÁ VENCIDA
    public boolean estaVencida() {
        return LocalDate.now().isAfter(fechaVencimiento) && !estado.equals("PAGADA");
    }
}