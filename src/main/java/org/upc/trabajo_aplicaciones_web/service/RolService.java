package org.upc.trabajo_aplicaciones_web.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.RolDTO;
import org.upc.trabajo_aplicaciones_web.model.Rol;
import org.upc.trabajo_aplicaciones_web.repository.RolRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    public RolDTO crear(RolDTO rolDTO) {
        if (rolRepository.existsByNombre(rolDTO.getNombre())) {
            throw new RuntimeException("El rol ya existe");
        }

        Rol rol = new Rol();
        rol.setNombre(rolDTO.getNombre());
        rol.setDescripcion(rolDTO.getDescripcion());

        rol = rolRepository.save(rol);
        return convertirARolDTO(rol);
    }

    public List<RolDTO> obtenerTodos() {
        List<Rol> roles = rolRepository.findAll();
        List<RolDTO> rolDTOs = new ArrayList<>();
        for (Rol rol : roles) {
            rolDTOs.add(convertirARolDTO(rol));
        }
        return rolDTOs;
    }

    public RolDTO obtenerPorId(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return convertirARolDTO(rol);
    }

    public RolDTO obtenerPorNombre(String nombre) {
        Rol rol = rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return convertirARolDTO(rol);
    }

    public RolDTO actualizar(Long id, RolDTO rolDTO) {
        Rol rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rolExistente.setNombre(rolDTO.getNombre());
        rolExistente.setDescripcion(rolDTO.getDescripcion());

        rolExistente = rolRepository.save(rolExistente);
        return convertirARolDTO(rolExistente);
    }

    public void eliminar(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado");
        }
        rolRepository.deleteById(id);
    }

    // ✅ MÉTODO DE CONVERSIÓN SIMPLIFICADO
    private RolDTO convertirARolDTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setRolId(rol.getRolId());
        dto.setNombre(rol.getNombre());
        dto.setDescripcion(rol.getDescripcion());
        return dto;
    }
}