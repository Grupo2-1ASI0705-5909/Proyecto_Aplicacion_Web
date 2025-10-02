package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rolid")
    private Long rolId;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    // RELACIONES MANY TO MANY - SIN ENTIDAD INTERMEDIA
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rolpermiso",
            joinColumns = @JoinColumn(name = "rolid"),
            inverseJoinColumns = @JoinColumn(name = "permisoid")
    )
    private List<Permiso> permisos = new ArrayList<>();
}