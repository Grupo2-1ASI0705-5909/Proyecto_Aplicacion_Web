package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;

@Data
public class PermisoDTO {
    private Long permisoId;
    private String nombre;
    private String descripcion;

    public PermisoDTO() {}

    public PermisoDTO(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}