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

    // ✅ Para creación/actualización: Solo el ID del rol
    private Long rolId;

    // ✅ Para respuestas: El objeto rol completo
    private RolDTO rol;

    // Relaciones (sin cambios)
    private List<ComercioDTO> comercios = new ArrayList<>();
    private List<WalletDTO> wallets = new ArrayList<>();

    public UsuarioDTO() {}

    public UsuarioDTO(String nombre, String apellido, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }
}