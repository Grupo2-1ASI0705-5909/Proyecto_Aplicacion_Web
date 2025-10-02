package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.RolDTO;
import org.upc.trabajo_aplicaciones_web.model.Permiso;
import org.upc.trabajo_aplicaciones_web.model.Rol;
import org.upc.trabajo_aplicaciones_web.repository.PermisoRepository;
import org.upc.trabajo_aplicaciones_web.repository.RolRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final ModelMapper modelMapper;

    public RolDTO crear(RolDTO rolDTO) {
        if (rolRepository.existsByNombre(rolDTO.getNombre())) {
            throw new RuntimeException("El rol ya existe");
        }

        Rol rol = modelMapper.map(rolDTO, Rol.class);

        // Asignar permisos si se proporcionan
        if (rolDTO.getPermisosIds() != null && !rolDTO.getPermisosIds().isEmpty()) {
            List<Permiso> permisos = permisoRepository.findAllById(rolDTO.getPermisosIds());
            rol.setPermisos(permisos);
        }

        rol = rolRepository.save(rol);
        return modelMapper.map(rol, RolDTO.class);
    }

    public List<RolDTO> obtenerTodos() {
        return rolRepository.findAll()
                .stream()
                .map(rol -> modelMapper.map(rol, RolDTO.class))
                .collect(Collectors.toList());
    }

    public RolDTO obtenerPorId(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return modelMapper.map(rol, RolDTO.class);
    }

    public RolDTO obtenerPorNombre(String nombre) {
        Rol rol = rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return modelMapper.map(rol, RolDTO.class);
    }

    public RolDTO actualizar(Long id, RolDTO rolDTO) {
        Rol rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rolExistente.setNombre(rolDTO.getNombre());
        rolExistente.setDescripcion(rolDTO.getDescripcion());

        // Actualizar permisos si se proporcionan
        if (rolDTO.getPermisosIds() != null) {
            List<Permiso> permisos = permisoRepository.findAllById(rolDTO.getPermisosIds());
            rolExistente.setPermisos(permisos);
        }

        rolExistente = rolRepository.save(rolExistente);
        return modelMapper.map(rolExistente, RolDTO.class);
    }

    public void eliminar(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado");
        }
        rolRepository.deleteById(id);
    }

    public List<RolDTO> obtenerRolesPorUsuario(Long usuarioId) {
        return rolRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(rol -> modelMapper.map(rol, RolDTO.class))
                .collect(Collectors.toList());
    }
}