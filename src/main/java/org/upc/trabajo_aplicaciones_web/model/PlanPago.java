package org.upc.trabajo_aplicaciones_web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planespago")
@Data
public class PlanPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planpagoid")
    private Long planPagoId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "transaccionid", nullable = false)
    private Transaccion transaccion;

    @Column(nullable = false, name = "numerocuotas")
    private Integer numeroCuotas;

    @Column(nullable = false, precision = 18, scale = 2, name = "montoporcuota")
    private BigDecimal montoPorCuota;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal interes;

    @Column(nullable = false, name = "fechainicio")
    private LocalDate fechaInicio;

    @Column(name = "fechafin")
    private LocalDate fechaFin;

    // RELACIONES
    @OneToMany(mappedBy = "planPago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cuota> cuotas = new ArrayList<>();

    // MÉTODO PARA CALCULAR FECHA FIN
    @PrePersist
    @PreUpdate
    public void calcularFechaFin() {
        if (fechaInicio != null && numeroCuotas != null) {
            this.fechaFin = fechaInicio.plusMonths(numeroCuotas);
        }
    }
}