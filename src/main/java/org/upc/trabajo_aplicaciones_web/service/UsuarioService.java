package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.UsuarioDTO;
import org.upc.trabajo_aplicaciones_web.model.Rol;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.repository.RolRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ModelMapper modelMapper;

    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

        // Asignar roles si se proporcionan
        if (usuarioDTO.getRolesIds() != null && !usuarioDTO.getRolesIds().isEmpty()) {
            List<Rol> roles = rolRepository.findAllById(usuarioDTO.getRolesIds());
            usuario.setRoles(roles);
        }

        usuario = usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public UsuarioDTO obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setEstado(usuarioDTO.getEstado());

        // Actualizar roles si se proporcionan
        if (usuarioDTO.getRolesIds() != null) {
            List<Rol> roles = rolRepository.findAllById(usuarioDTO.getRolesIds());
            usuarioExistente.setRoles(roles);
        }

        usuarioExistente = usuarioRepository.save(usuarioExistente);
        return modelMapper.map(usuarioExistente, UsuarioDTO.class);
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
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> buscarPorNombre(String nombre) {
        return usuarioRepository.buscarPorNombre(nombre)
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> obtenerPorEstado(Boolean estado) {
        return usuarioRepository.findByEstado(estado)
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public long contarUsuariosActivos() {
        return usuarioRepository.countByEstadoTrue();
    }

    public List<UsuarioDTO> obtenerPorRol(Long rolId) {
        return usuarioRepository.findByRolId(rolId)
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }
}