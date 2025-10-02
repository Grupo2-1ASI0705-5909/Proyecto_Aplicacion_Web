package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuarioid")
    private Long usuarioId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 255, name = "passwordhash")
    private String passwordHash;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(nullable = false, name = "createdat")
    private LocalDateTime createdAt = LocalDateTime.now();

    // RELACIONES MANY TO MANY - SIN ENTIDAD INTERMEDIA
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuariorol",
            joinColumns = @JoinColumn(name = "usuarioid"),
            inverseJoinColumns = @JoinColumn(name = "rolid")
    )
    private List<Rol> roles = new ArrayList<>();

    // RELACIONES ONE TO MANY
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comercio> comercios = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaccion> transacciones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> wallets = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacion> notificaciones = new ArrayList<>();
}