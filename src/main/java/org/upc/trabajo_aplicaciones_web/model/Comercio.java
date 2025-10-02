package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comercios")
@Data
public class Comercio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comercioid")
    private Long comercioId;

    @ManyToOne
    @JoinColumn(name = "usuarioid", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 200, name = "nombrecomercial")
    private String nombreComercial;

    @Column(nullable = false, length = 20)
    private String ruc;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String categoria;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(nullable = false, name = "createdat")
    private LocalDateTime createdAt = LocalDateTime.now();

    // RELACIONES
    @OneToMany(mappedBy = "comercio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaccion> transacciones = new ArrayList<>();
}