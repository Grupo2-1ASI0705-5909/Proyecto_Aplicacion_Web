package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.ComercioDTO;
import org.upc.trabajo_aplicaciones_web.model.Comercio;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.repository.ComercioRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComercioService {
    private final ComercioRepository comercioRepository;
    private final UsuarioRepository usuarioRepository;

    public ComercioDTO crear(ComercioDTO comercioDTO) {
        if (comercioRepository.existsByRuc(comercioDTO.getRuc())) {
            throw new RuntimeException("El RUC ya está registrado");
        }

        Usuario usuario = usuarioRepository.findById(comercioDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comercio comercio = new Comercio();
        comercio.setUsuario(usuario);
        comercio.setNombreComercial(comercioDTO.getNombreComercial());
        comercio.setRuc(comercioDTO.getRuc());
        comercio.setDireccion(comercioDTO.getDireccion());
        comercio.setCategoria(comercioDTO.getCategoria());
        comercio.setEstado(comercioDTO.getEstado() != null ? comercioDTO.getEstado() : true);

        comercio = comercioRepository.save(comercio);
        return convertirAComercioDTO(comercio);
    }

    public List<ComercioDTO> obtenerTodos() {
        List<Comercio> comercios = comercioRepository.findAll();
        List<ComercioDTO> comercioDTOs = new ArrayList<>();
        for (Comercio comercio : comercios) {
            comercioDTOs.add(convertirAComercioDTO(comercio));
        }
        return comercioDTOs;
    }

    public ComercioDTO obtenerPorId(Long id) {
        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));
        return convertirAComercioDTO(comercio);
    }

    public ComercioDTO obtenerPorRuc(String ruc) {
        Comercio comercio = comercioRepository.findByRuc(ruc)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));
        return convertirAComercioDTO(comercio);
    }

    public ComercioDTO actualizar(Long id, ComercioDTO comercioDTO) {
        Comercio comercioExistente = comercioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        comercioExistente.setNombreComercial(comercioDTO.getNombreComercial());
        comercioExistente.setRuc(comercioDTO.getRuc());
        comercioExistente.setDireccion(comercioDTO.getDireccion());
        comercioExistente.setCategoria(comercioDTO.getCategoria());
        comercioExistente.setEstado(comercioDTO.getEstado());

        comercioExistente = comercioRepository.save(comercioExistente);
        return convertirAComercioDTO(comercioExistente);
    }

    public void eliminar(Long id) {
        if (!comercioRepository.existsById(id)) {
            throw new RuntimeException("Comercio no encontrado");
        }
        comercioRepository.deleteById(id);
    }

    public ComercioDTO cambiarEstado(Long id, Boolean estado) {
        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));
        comercio.setEstado(estado);
        comercio = comercioRepository.save(comercio);
        return convertirAComercioDTO(comercio);
    }

    public List<ComercioDTO> obtenerPorUsuario(Long usuarioId) {
        List<Comercio> comercios = comercioRepository.findByUsuarioUsuarioId(usuarioId);
        List<ComercioDTO> comercioDTOs = new ArrayList<>();
        for (Comercio comercio : comercios) {
            comercioDTOs.add(convertirAComercioDTO(comercio));
        }
        return comercioDTOs;
    }

    public List<ComercioDTO> obtenerPorCategoria(String categoria) {
        List<Comercio> comercios = comercioRepository.findByCategoria(categoria);
        List<ComercioDTO> comercioDTOs = new ArrayList<>();
        for (Comercio comercio : comercios) {
            comercioDTOs.add(convertirAComercioDTO(comercio));
        }
        return comercioDTOs;
    }

    public List<ComercioDTO> buscarPorNombre(String nombre) {
        List<Comercio> comercios = comercioRepository.findByNombreComercialContainingIgnoreCase(nombre);
        List<ComercioDTO> comercioDTOs = new ArrayList<>();
        for (Comercio comercio : comercios) {
            comercioDTOs.add(convertirAComercioDTO(comercio));
        }
        return comercioDTOs;
    }

    public List<ComercioDTO> obtenerActivos() {
        List<Comercio> comercios = comercioRepository.findByEstado(true);
        List<ComercioDTO> comercioDTOs = new ArrayList<>();
        for (Comercio comercio : comercios) {
            comercioDTOs.add(convertirAComercioDTO(comercio));
        }
        return comercioDTOs;
    }

    private ComercioDTO convertirAComercioDTO(Comercio comercio) {
        ComercioDTO dto = new ComercioDTO();
        dto.setComercioId(comercio.getComercioId());
        dto.setUsuarioId(comercio.getUsuario().getUsuarioId());
        dto.setNombreComercial(comercio.getNombreComercial());
        dto.setRuc(comercio.getRuc());
        dto.setDireccion(comercio.getDireccion());
        dto.setCategoria(comercio.getCategoria());
        dto.setEstado(comercio.getEstado());
        dto.setCreatedAt(comercio.getCreatedAt());
        return dto;
    }
}