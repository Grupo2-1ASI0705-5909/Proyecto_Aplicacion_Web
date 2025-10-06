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

    @Column(nullable = false, unique = true, length = 50)
    private String nombre; // Ejemplo: "ADMIN", "USER", "COMERCIANTE"

    @Column(nullable = false, length = 200)
    private String descripcion;

    // relacio de un rol a muchos usuarios, quitando la tabla de permisos
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();
}