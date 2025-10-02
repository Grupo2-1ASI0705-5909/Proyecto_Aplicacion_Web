package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;

@Data
public class MetodoPagoDTO {
    private Long metodoPagoId;
    private String nombre;
    private String descripcion;
    private Boolean estado = true;

    public MetodoPagoDTO() {}

    public MetodoPagoDTO(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}