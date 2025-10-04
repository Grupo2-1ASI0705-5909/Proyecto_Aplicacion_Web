package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
@Data
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walletid")
    private Long walletId;

    @ManyToOne
    @JoinColumn(name = "usuarioid", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "criptoid", nullable = false)
    private Criptomoneda criptomoneda;

    @Column(length = 255)
    private String direccion;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(nullable = false, name = "ultimaactualizacion")
    private LocalDateTime ultimaActualizacion = LocalDateTime.now();
    //vercion 3/10

}