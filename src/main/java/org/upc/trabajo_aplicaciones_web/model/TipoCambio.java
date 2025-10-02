package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipocambio")
@Data
public class TipoCambio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipocambioid")
    private Long tipoCambioId;

    @Column(nullable = false, length = 10, name = "desdecodigo")
    private String desdeCodigo;

    @Column(nullable = false, length = 10, name = "hastacodigo")
    private String hastaCodigo;

    @Column(nullable = false, precision = 28, scale = 12)
    private BigDecimal tasa;

    @Column(nullable = false, name = "fechahora")
    private LocalDateTime fechaHora = LocalDateTime.now();

    @Column(length = 100)
    private String fuente;

    // RELACIONES
    @OneToMany(mappedBy = "tipoCambio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaccion> transacciones = new ArrayList<>();
}