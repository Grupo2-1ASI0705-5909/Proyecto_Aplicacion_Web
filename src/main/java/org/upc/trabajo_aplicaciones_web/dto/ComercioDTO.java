package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComercioDTO {
    private Long comercioId;
    private Long usuarioId;
    private String nombreComercial;
    private String ruc;
    private String direccion;
    private String categoria;
    private Boolean estado = true;
    private LocalDateTime createdAt;

    // Para respuestas

    public ComercioDTO() {}

    public ComercioDTO(String nombreComercial, String ruc, String direccion, String categoria) {
        this.nombreComercial = nombreComercial;
        this.ruc = ruc;
        this.direccion = direccion;
        this.categoria = categoria;
    }

    //Comentario
}