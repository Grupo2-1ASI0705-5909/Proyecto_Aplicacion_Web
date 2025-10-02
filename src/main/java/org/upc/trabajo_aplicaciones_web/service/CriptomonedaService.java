package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.CriptomonedaDTO;
import org.upc.trabajo_aplicaciones_web.model.Criptomoneda;
import org.upc.trabajo_aplicaciones_web.repository.CriptomonedaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CriptomonedaService {

    private final CriptomonedaRepository criptomonedaRepository;
    private final ModelMapper modelMapper;

    public CriptomonedaDTO crear(CriptomonedaDTO criptomonedaDTO) {
        if (criptomonedaRepository.existsByCodigo(criptomonedaDTO.getCodigo())) {
            throw new RuntimeException("La criptomoneda ya existe");
        }

        Criptomoneda criptomoneda = modelMapper.map(criptomonedaDTO, Criptomoneda.class);
        criptomoneda = criptomonedaRepository.save(criptomoneda);
        return modelMapper.map(criptomoneda, CriptomonedaDTO.class);
    }

    public List<CriptomonedaDTO> obtenerTodos() {
        return criptomonedaRepository.findAll()
                .stream()
                .map(cripto -> modelMapper.map(cripto, CriptomonedaDTO.class))
                .collect(Collectors.toList());
    }

    public CriptomonedaDTO obtenerPorId(Long id) {
        Criptomoneda criptomoneda = criptomonedaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));
        return modelMapper.map(criptomoneda, CriptomonedaDTO.class);
    }

    public CriptomonedaDTO obtenerPorCodigo(String codigo) {
        Criptomoneda criptomoneda = criptomonedaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));
        return modelMapper.map(criptomoneda, CriptomonedaDTO.class);
    }

    public CriptomonedaDTO actualizar(Long id, CriptomonedaDTO criptomonedaDTO) {
        Criptomoneda criptoExistente = criptomonedaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));

        criptoExistente.setNombre(criptomonedaDTO.getNombre());
        criptoExistente.setDecimales(criptomonedaDTO.getDecimales());
        criptoExistente.setActiva(criptomonedaDTO.getActiva());

        criptoExistente = criptomonedaRepository.save(criptoExistente);
        return modelMapper.map(criptoExistente, CriptomonedaDTO.class);
    }

    public void eliminar(Long id) {
        if (!criptomonedaRepository.existsById(id)) {
            throw new RuntimeException("Criptomoneda no encontrada");
        }
        criptomonedaRepository.deleteById(id);
    }

    public List<CriptomonedaDTO> obtenerActivas() {
        return criptomonedaRepository.findByActivaTrue()
                .stream()
                .map(cripto -> modelMapper.map(cripto, CriptomonedaDTO.class))
                .collect(Collectors.toList());
    }

    public List<CriptomonedaDTO> buscarPorNombre(String nombre) {
        return criptomonedaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(cripto -> modelMapper.map(cripto, CriptomonedaDTO.class))
                .collect(Collectors.toList());
    }

    public CriptomonedaDTO cambiarEstado(Long id, Boolean activa) {
        Criptomoneda criptomoneda = criptomonedaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));
        criptomoneda.setActiva(activa);
        criptomoneda = criptomonedaRepository.save(criptomoneda);
        return modelMapper.map(criptomoneda, CriptomonedaDTO.class);
    }
}