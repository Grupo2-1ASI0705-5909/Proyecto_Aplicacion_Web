package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.PermisoDTO;
import org.upc.trabajo_aplicaciones_web.model.Permiso;
import org.upc.trabajo_aplicaciones_web.repository.PermisoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermisoService {

    private final PermisoRepository permisoRepository;
    private final ModelMapper modelMapper;

    public PermisoDTO crear(PermisoDTO permisoDTO) {
        if (permisoRepository.existsByNombre(permisoDTO.getNombre())) {
            throw new RuntimeException("El permiso ya existe");
        }

        Permiso permiso = modelMapper.map(permisoDTO, Permiso.class);
        permiso = permisoRepository.save(permiso);
        return modelMapper.map(permiso, PermisoDTO.class);
    }

    public List<PermisoDTO> obtenerTodos() {
        return permisoRepository.findAll()
                .stream()
                .map(permiso -> modelMapper.map(permiso, PermisoDTO.class))
                .collect(Collectors.toList());
    }

    public PermisoDTO obtenerPorId(Long id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        return modelMapper.map(permiso, PermisoDTO.class);
    }

    public PermisoDTO actualizar(Long id, PermisoDTO permisoDTO) {
        Permiso permisoExistente = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));

        permisoExistente.setNombre(permisoDTO.getNombre());
        permisoExistente.setDescripcion(permisoDTO.getDescripcion());

        permisoExistente = permisoRepository.save(permisoExistente);
        return modelMapper.map(permisoExistente, PermisoDTO.class);
    }

    public void eliminar(Long id) {
        if (!permisoRepository.existsById(id)) {
            throw new RuntimeException("Permiso no encontrado");
        }
        permisoRepository.deleteById(id);
    }

    public List<PermisoDTO> buscarPorNombre(String nombre) {
        return permisoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(permiso -> modelMapper.map(permiso, PermisoDTO.class))
                .collect(Collectors.toList());
    }

    public List<PermisoDTO> obtenerPermisosPorRol(Long rolId) {
        return permisoRepository.findByRolId(rolId)
                .stream()
                .map(permiso -> modelMapper.map(permiso, PermisoDTO.class))
                .collect(Collectors.toList());
    }
}