package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cuotas")
@Data
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuotaid")
    private Long cuotaId;

    @ManyToOne
    @JoinColumn(name = "planpagoid", nullable = false)
    private PlanPago planPago;

    @Column(nullable = false, name = "numerocuota")
    private Integer numeroCuota;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false, name = "fechavencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "fechapago")
    private LocalDate fechaPago;

    @Column(nullable = false, length = 20)
    private String estado = "PENDIENTE";

    public boolean estaVencida() {
        return LocalDate.now().isAfter(fechaVencimiento) && !"PAGADA".equals(estado);
    }
}