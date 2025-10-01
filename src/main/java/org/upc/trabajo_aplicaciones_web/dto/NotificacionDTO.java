package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificacionDTO {
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private Boolean leido = false;

    // Para respuestas
    private UsuarioDTO usuario;

    public NotificacionDTO() {}

    public NotificacionDTO(String titulo, String mensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaEnvio = LocalDateTime.now();
    }
    // to bien

}