package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.PermisoDTO;
import org.upc.trabajo_aplicaciones_web.model.Permiso;
import org.upc.trabajo_aplicaciones_web.repository.PermisoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermisoService {
    private final PermisoRepository permisoRepository;

    public PermisoDTO crear(PermisoDTO permisoDTO) {
        if (permisoRepository.existsByNombre(permisoDTO.getNombre())) {
            throw new RuntimeException("El permiso ya existe");
        }

        Permiso permiso = new Permiso();
        permiso.setNombre(permisoDTO.getNombre());
        permiso.setDescripcion(permisoDTO.getDescripcion());

        permiso = permisoRepository.save(permiso);
        return convertirAPermisoDTO(permiso);
    }

    public List<PermisoDTO> obtenerTodos() {
        List<Permiso> permisos = permisoRepository.findAll();
        List<PermisoDTO> permisoDTOs = new ArrayList<>();
        for (Permiso permiso : permisos) {
            permisoDTOs.add(convertirAPermisoDTO(permiso));
        }
        return permisoDTOs;
    }

    public PermisoDTO obtenerPorId(Long id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        return convertirAPermisoDTO(permiso);
    }

    public PermisoDTO actualizar(Long id, PermisoDTO permisoDTO) {
        Permiso permisoExistente = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));

        permisoExistente.setNombre(permisoDTO.getNombre());
        permisoExistente.setDescripcion(permisoDTO.getDescripcion());

        permisoExistente = permisoRepository.save(permisoExistente);
        return convertirAPermisoDTO(permisoExistente);
    }

    public void eliminar(Long id) {
        if (!permisoRepository.existsById(id)) {
            throw new RuntimeException("Permiso no encontrado");
        }
        permisoRepository.deleteById(id);
    }

    public List<PermisoDTO> buscarPorNombre(String nombre) {
        List<Permiso> permisos = permisoRepository.findByNombreContainingIgnoreCase(nombre);
        List<PermisoDTO> permisoDTOs = new ArrayList<>();
        for (Permiso permiso : permisos) {
            permisoDTOs.add(convertirAPermisoDTO(permiso));
        }
        return permisoDTOs;
    }

    public List<PermisoDTO> obtenerPermisosPorRol(Long rolId) {
        List<Permiso> permisos = permisoRepository.findByRolId(rolId);
        List<PermisoDTO> permisoDTOs = new ArrayList<>();
        for (Permiso permiso : permisos) {
            permisoDTOs.add(convertirAPermisoDTO(permiso));
        }
        return permisoDTOs;
    }

    private PermisoDTO convertirAPermisoDTO(Permiso permiso) {
        PermisoDTO dto = new PermisoDTO();
        dto.setPermisoId(permiso.getPermisoId());
        dto.setNombre(permiso.getNombre());
        dto.setDescripcion(permiso.getDescripcion());
        return dto;
    }
    //version 3/10/25

}