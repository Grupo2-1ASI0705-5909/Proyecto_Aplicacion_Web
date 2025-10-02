package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;

@Data
public class    ComercioDTO {
    private Long id;
    private Long usuarioId;
    private String nombreComercial;
    private String ruc;
    private String direccion;
    private String categoria;
    private Boolean estado = true;

    // Para respuestas
    private UsuarioDTO usuario;

    public ComercioDTO() {}

    public ComercioDTO(String nombreComercial, String ruc, String direccion, String categoria) {
        this.nombreComercial = nombreComercial;
        this.ruc = ruc;
        this.direccion = direccion;
        this.categoria = categoria;
    }
    //Notas
}