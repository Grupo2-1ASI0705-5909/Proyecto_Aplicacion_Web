package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.ComercioDTO;
import org.upc.trabajo_aplicaciones_web.model.Comercio;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.repository.ComercioRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComercioService {

    private final ComercioRepository comercioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public ComercioDTO crear(ComercioDTO comercioDTO) {
        if (comercioRepository.existsByRuc(comercioDTO.getRuc())) {
            throw new RuntimeException("El RUC ya está registrado");
        }

        Usuario usuario = usuarioRepository.findById(comercioDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comercio comercio = modelMapper.map(comercioDTO, Comercio.class);
        comercio.setUsuario(usuario);

        comercio = comercioRepository.save(comercio);
        return modelMapper.map(comercio, ComercioDTO.class);
    }

    public List<ComercioDTO> obtenerTodos() {
        return comercioRepository.findAll()
                .stream()
                .map(comercio -> modelMapper.map(comercio, ComercioDTO.class))
                .collect(Collectors.toList());
    }

    public ComercioDTO obtenerPorId(Long id) {
        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));
        return modelMapper.map(comercio, ComercioDTO.class);
    }

    public ComercioDTO obtenerPorRuc(String ruc) {
        Comercio comercio = comercioRepository.findByRuc(ruc)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));
        return modelMapper.map(comercio, ComercioDTO.class);
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
        return modelMapper.map(comercioExistente, ComercioDTO.class);
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
        return modelMapper.map(comercio, ComercioDTO.class);
    }

    public List<ComercioDTO> obtenerPorUsuario(Long usuarioId) {
        return comercioRepository.findByUsuarioUsuarioId(usuarioId)
                .stream()
                .map(comercio -> modelMapper.map(comercio, ComercioDTO.class))
                .collect(Collectors.toList());
    }

    public List<ComercioDTO> obtenerPorCategoria(String categoria) {
        return comercioRepository.findByCategoria(categoria)
                .stream()
                .map(comercio -> modelMapper.map(comercio, ComercioDTO.class))
                .collect(Collectors.toList());
    }

    public List<ComercioDTO> buscarPorNombre(String nombre) {
        return comercioRepository.findByNombreComercialContainingIgnoreCase(nombre)
                .stream()
                .map(comercio -> modelMapper.map(comercio, ComercioDTO.class))
                .collect(Collectors.toList());
    }

    public List<ComercioDTO> obtenerActivos() {
        return comercioRepository.findByEstado(true)
                .stream()
                .map(comercio -> modelMapper.map(comercio, ComercioDTO.class))
                .collect(Collectors.toList());
    }
}