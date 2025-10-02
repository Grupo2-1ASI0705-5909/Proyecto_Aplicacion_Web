package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String passwordHash;
    private Boolean estado = true;
    private List<Long> rolesIds = new ArrayList<>();

    // Para respuestas con datos completos
    private List<RolDTO> roles = new ArrayList<>();
    private List<ComercioDTO> comercios = new ArrayList<>();

    // Constructor para creación básica
    public UsuarioDTO() {}

    public UsuarioDTO(String nombre, String apellido, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }
    //Nota de version 1 de octubre

    //Nota prueba David

    //Nota Prueba David 2.0
}