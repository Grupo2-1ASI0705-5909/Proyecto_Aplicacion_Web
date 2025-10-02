package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.UsuarioDTO;
import org.upc.trabajo_aplicaciones_web.model.Rol;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.repository.RolRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setPasswordHash(usuarioDTO.getPasswordHash());
        usuario.setEstado(usuarioDTO.getEstado() != null ? usuarioDTO.getEstado() : true);

        if (usuarioDTO.getRolesIds() != null && !usuarioDTO.getRolesIds().isEmpty()) {
            List<Rol> roles = rolRepository.findAllById(usuarioDTO.getRolesIds());
            usuario.setRoles(roles);
        }

        usuario = usuarioRepository.save(usuario);
        return convertirAUsuarioDTO(usuario);
    }

    public List<UsuarioDTO> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(convertirAUsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirAUsuarioDTO(usuario);
    }

    public UsuarioDTO obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirAUsuarioDTO(usuario);
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setEstado(usuarioDTO.getEstado());

        if (usuarioDTO.getRolesIds() != null) {
            List<Rol> roles = rolRepository.findAllById(usuarioDTO.getRolesIds());
            usuarioExistente.setRoles(roles);
        }

        usuarioExistente = usuarioRepository.save(usuarioExistente);
        return convertirAUsuarioDTO(usuarioExistente);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO cambiarEstado(Long id, Boolean estado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(estado);
        usuario = usuarioRepository.save(usuario);
        return convertirAUsuarioDTO(usuario);
    }

    public List<UsuarioDTO> buscarPorNombre(String nombre) {
        List<Usuario> usuarios = usuarioRepository.buscarPorNombre(nombre);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(convertirAUsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    public List<UsuarioDTO> obtenerPorEstado(Boolean estado) {
        List<Usuario> usuarios = usuarioRepository.findByEstado(estado);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(convertirAUsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    public long contarUsuariosActivos() {
        return usuarioRepository.countByEstadoTrue();
    }

    public List<UsuarioDTO> obtenerPorRol(Long rolId) {
        List<Usuario> usuarios = usuarioRepository.findByRolId(rolId);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(convertirAUsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    private UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuarioId(usuario.getUsuarioId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setPasswordHash(usuario.getPasswordHash());
        dto.setEstado(usuario.getEstado());
        dto.setCreatedAt(usuario.getCreatedAt());

        // Convertir roles a IDs
        List<Long> rolesIds = new ArrayList<>();
        for (Rol rol : usuario.getRoles()) {
            rolesIds.add(rol.getRolId());
        }
        dto.setRolesIds(rolesIds);

        return dto;
    }
}