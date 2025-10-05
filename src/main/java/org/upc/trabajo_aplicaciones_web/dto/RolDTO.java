package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RolDTO {
    private Long rolId;
    private String nombre;
    private String descripcion;

    public RolDTO() {}

    public RolDTO(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}