package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan_pago")
@Data
public class PlanPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELACIONES
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaccion_id", nullable = false)
    private Transaccion transaccion;

    // DATOS DEL PLAN
    @Column(nullable = false)
    private Integer numeroCuotas;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPorCuota;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal interes;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    // RELACIONES HIJAS
    @OneToMany(mappedBy = "planPago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cuota> cuotas = new ArrayList<>();

    // MÉTODO PARA CALCULAR FECHA FIN AUTOMÁTICAMENTE
    @PrePersist
    public void calcularFechaFin() {
        if (fechaInicio != null && numeroCuotas != null) {
            this.fechaFin = fechaInicio.plusMonths(numeroCuotas);
        }
    }
}