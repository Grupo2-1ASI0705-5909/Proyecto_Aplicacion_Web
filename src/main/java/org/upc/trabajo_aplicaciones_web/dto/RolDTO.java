package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class RolDTO {
    private Long rolId;
    private String nombre;
    private String descripcion;

    // Para creación/actualización
    private List<Long> permisosIds = new ArrayList<>();

    // Para respuestas
    private List<PermisoDTO> permisos = new ArrayList<>();
    private List<UsuarioDTO> usuarios = new ArrayList<>();

    public RolDTO() {}

    public RolDTO(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}