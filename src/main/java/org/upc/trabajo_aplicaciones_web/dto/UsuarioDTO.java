package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UsuarioDTO {
    private Long usuarioId;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String passwordHash;
    private Boolean estado = true;
    private LocalDateTime createdAt;

    private Long rolId;

    private RolDTO rol;

    public UsuarioDTO() {}

    public UsuarioDTO(String nombre, String apellido, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }
}