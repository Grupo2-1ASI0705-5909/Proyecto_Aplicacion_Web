package org.upc.trabajo_aplicaciones_web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permisos")
@Data
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permisoid")
    private Long permisoId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    // RELACIONES MANY TO MANY - SIN ENTIDAD INTERMEDIA
    @ManyToMany(mappedBy = "permisos", fetch = FetchType.LAZY)
    private List<Rol> roles = new ArrayList<>();
}