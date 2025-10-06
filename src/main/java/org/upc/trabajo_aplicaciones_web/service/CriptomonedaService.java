package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.CriptomonedaDTO;
import org.upc.trabajo_aplicaciones_web.model.Criptomoneda;
import org.upc.trabajo_aplicaciones_web.repository.CriptomonedaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CriptomonedaService {
    private final CriptomonedaRepository criptomonedaRepository;

    //David CriptoService

    public CriptomonedaDTO crear(CriptomonedaDTO criptomonedaDTO) {
        if (criptomonedaRepository.existsByCodigo(criptomonedaDTO.getCodigo())) {
            throw new RuntimeException("La criptomoneda ya existe");
        }

        Criptomoneda criptomoneda = new Criptomoneda();
        criptomoneda.setCodigo(criptomonedaDTO.getCodigo());
        criptomoneda.setNombre(criptomonedaDTO.getNombre());
        criptomoneda.setDecimales(criptomonedaDTO.getDecimales() != null ? criptomonedaDTO.getDecimales() : 8);
        criptomoneda.setActiva(criptomonedaDTO.getActiva() != null ? criptomonedaDTO.getActiva() : true);

        criptomoneda = criptomonedaRepository.save(criptomoneda);
        return convertirACriptomonedaDTO(criptomoneda);
    }

    public List<CriptomonedaDTO> obtenerTodos() {
        List<Criptomoneda> criptomonedas = criptomonedaRepository.findAll();
        List<CriptomonedaDTO> criptomonedaDTOs = new ArrayList<>();
        for (Criptomoneda cripto : criptomonedas) {
            criptomonedaDTOs.add(convertirACriptomonedaDTO(cripto));
        }
        return criptomonedaDTOs;
    }

    public CriptomonedaDTO obtenerPorId(Long id) {
        Criptomoneda criptomoneda = criptomonedaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));
        return convertirACriptomonedaDTO(criptomoneda);
    }

    public CriptomonedaDTO obtenerPorCodigo(String codigo) {
        Criptomoneda criptomoneda = criptomonedaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));
        return convertirACriptomonedaDTO(criptomoneda);
    }

    public CriptomonedaDTO actualizar(Long id, CriptomonedaDTO criptomonedaDTO) {
        Criptomoneda criptoExistente = criptomonedaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));

        criptoExistente.setNombre(criptomonedaDTO.getNombre());
        criptoExistente.setDecimales(criptomonedaDTO.getDecimales());
        criptoExistente.setActiva(criptomonedaDTO.getActiva());

        criptoExistente = criptomonedaRepository.save(criptoExistente);
        return convertirACriptomonedaDTO(criptoExistente);
    }

    public void eliminar(Long id) {
        if (!criptomonedaRepository.existsById(id)) {
            throw new RuntimeException("Criptomoneda no encontrada");
        }
        criptomonedaRepository.deleteById(id);
    }

    public List<CriptomonedaDTO> obtenerActivas() {
        List<Criptomoneda> criptomonedas = criptomonedaRepository.findByActivaTrue();
        List<CriptomonedaDTO> criptomonedaDTOs = new ArrayList<>();
        for (Criptomoneda cripto : criptomonedas) {
            criptomonedaDTOs.add(convertirACriptomonedaDTO(cripto));
        }
        return criptomonedaDTOs;
    }

    public List<CriptomonedaDTO> buscarPorNombre(String nombre) {
        List<Criptomoneda> criptomonedas = criptomonedaRepository.findByNombreContainingIgnoreCase(nombre);
        List<CriptomonedaDTO> criptomonedaDTOs = new ArrayList<>();
        for (Criptomoneda cripto : criptomonedas) {
            criptomonedaDTOs.add(convertirACriptomonedaDTO(cripto));
        }
        return criptomonedaDTOs;
    }

    public CriptomonedaDTO cambiarEstado(Long id, Boolean activa) {
        Criptomoneda criptomoneda = criptomonedaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));
        criptomoneda.setActiva(activa);
        criptomoneda = criptomonedaRepository.save(criptomoneda);
        return convertirACriptomonedaDTO(criptomoneda);
    }

    private CriptomonedaDTO convertirACriptomonedaDTO(Criptomoneda criptomoneda) {
        CriptomonedaDTO dto = new CriptomonedaDTO();
        dto.setCriptoId(criptomoneda.getCriptoId());
        dto.setCodigo(criptomoneda.getCodigo());
        dto.setNombre(criptomoneda.getNombre());
        dto.setDecimales(criptomoneda.getDecimales());
        dto.setActiva(criptomoneda.getActiva());
        return dto;
    }
}