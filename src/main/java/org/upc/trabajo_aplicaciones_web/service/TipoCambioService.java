package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.TipoCambioDTO;
import org.upc.trabajo_aplicaciones_web.model.TipoCambio;
import org.upc.trabajo_aplicaciones_web.repository.TipoCambioRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//actualizado
public class TipoCambioService {
    private final TipoCambioRepository tipoCambioRepository;

    public TipoCambioDTO crear(TipoCambioDTO tipoCambioDTO) {
        TipoCambio tipoCambio = new TipoCambio();
        tipoCambio.setDesdeCodigo(tipoCambioDTO.getDesdeCodigo());
        tipoCambio.setHastaCodigo(tipoCambioDTO.getHastaCodigo());
        tipoCambio.setTasa(tipoCambioDTO.getTasa());
        tipoCambio.setFuente(tipoCambioDTO.getFuente());

        tipoCambio = tipoCambioRepository.save(tipoCambio);
        return convertirATipoCambioDTO(tipoCambio);
    }

    public List<TipoCambioDTO> obtenerTodos() {
        List<TipoCambio> tiposCambio = tipoCambioRepository.findAll();
        List<TipoCambioDTO> tipoCambioDTOs = new ArrayList<>();
        for (TipoCambio tipoCambio : tiposCambio) {
            tipoCambioDTOs.add(convertirATipoCambioDTO(tipoCambio));
        }
        return tipoCambioDTOs;
    }

    public TipoCambioDTO obtenerPorId(Long id) {
        TipoCambio tipoCambio = tipoCambioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de cambio no encontrado"));
        return convertirATipoCambioDTO(tipoCambio);
    }

    public TipoCambioDTO obtenerTasaMasReciente(String desde, String hasta) {
        TipoCambio tipoCambio = tipoCambioRepository.findTasaMasReciente(desde, hasta)
                .orElseThrow(() -> new RuntimeException("No se encontró tasa de cambio para el par especificado"));
        return convertirATipoCambioDTO(tipoCambio);
    }

    public List<TipoCambioDTO> obtenerTasasMasRecientes() {
        List<TipoCambio> tiposCambio = tipoCambioRepository.findTasasMasRecientes();
        List<TipoCambioDTO> tipoCambioDTOs = new ArrayList<>();
        for (TipoCambio tipoCambio : tiposCambio) {
            tipoCambioDTOs.add(convertirATipoCambioDTO(tipoCambio));
        }
        return tipoCambioDTOs;
    }

    public List<TipoCambioDTO> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<TipoCambio> tiposCambio = tipoCambioRepository.findByFechaHoraBetween(fechaInicio, fechaFin);
        List<TipoCambioDTO> tipoCambioDTOs = new ArrayList<>();
        for (TipoCambio tipoCambio : tiposCambio) {
            tipoCambioDTOs.add(convertirATipoCambioDTO(tipoCambio));
        }
        return tipoCambioDTOs;
    }

    public List<TipoCambioDTO> obtenerHistorialTasas(String desde, String hasta) {
        List<TipoCambio> tiposCambio = tipoCambioRepository.findHistorialTasas(desde, hasta);
        List<TipoCambioDTO> tipoCambioDTOs = new ArrayList<>();
        for (TipoCambio tipoCambio : tiposCambio) {
            tipoCambioDTOs.add(convertirATipoCambioDTO(tipoCambio));
        }
        return tipoCambioDTOs;
    }

    public Double calcularPromedioTasas(String desde, String hasta,
                                        LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Double promedio = tipoCambioRepository.calcularPromedioTasas(desde, hasta, fechaInicio, fechaFin);
        return promedio != null ? promedio : 0.0;
    }

    public void eliminar(Long id) {
        if (!tipoCambioRepository.existsById(id)) {
            throw new RuntimeException("Tipo de cambio no encontrado");
        }
        tipoCambioRepository.deleteById(id);
    }

    private TipoCambioDTO convertirATipoCambioDTO(TipoCambio tipoCambio) {
        TipoCambioDTO dto = new TipoCambioDTO();
        dto.setTipoCambioId(tipoCambio.getTipoCambioId());
        dto.setDesdeCodigo(tipoCambio.getDesdeCodigo());
        dto.setHastaCodigo(tipoCambio.getHastaCodigo());
        dto.setTasa(tipoCambio.getTasa());
        dto.setFechaHora(tipoCambio.getFechaHora());
        dto.setFuente(tipoCambio.getFuente());
        return dto;
    }
}