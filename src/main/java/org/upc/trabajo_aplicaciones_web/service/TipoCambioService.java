package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.TipoCambioDTO;
import org.upc.trabajo_aplicaciones_web.model.TipoCambio;
import org.upc.trabajo_aplicaciones_web.repository.TipoCambioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoCambioService {

    private final TipoCambioRepository tipoCambioRepository;
    private final ModelMapper modelMapper;

    public TipoCambioDTO crear(TipoCambioDTO tipoCambioDTO) {
        TipoCambio tipoCambio = modelMapper.map(tipoCambioDTO, TipoCambio.class);
        tipoCambio = tipoCambioRepository.save(tipoCambio);
        return modelMapper.map(tipoCambio, TipoCambioDTO.class);
    }

    public List<TipoCambioDTO> obtenerTodos() {
        return tipoCambioRepository.findAll()
                .stream()
                .map(tc -> modelMapper.map(tc, TipoCambioDTO.class))
                .collect(Collectors.toList());
    }

    public TipoCambioDTO obtenerPorId(Long id) {
        TipoCambio tipoCambio = tipoCambioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de cambio no encontrado"));
        return modelMapper.map(tipoCambio, TipoCambioDTO.class);
    }

    public TipoCambioDTO obtenerTasaMasReciente(String desde, String hasta) {
        TipoCambio tipoCambio = tipoCambioRepository.findTasaMasReciente(desde, hasta)
                .orElseThrow(() -> new RuntimeException("No se encontró tasa de cambio para el par especificado"));
        return modelMapper.map(tipoCambio, TipoCambioDTO.class);
    }

    public List<TipoCambioDTO> obtenerTasasMasRecientes() {
        return tipoCambioRepository.findTasasMasRecientes()
                .stream()
                .map(tc -> modelMapper.map(tc, TipoCambioDTO.class))
                .collect(Collectors.toList());
    }

    public List<TipoCambioDTO> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return tipoCambioRepository.findByFechaHoraBetween(fechaInicio, fechaFin)
                .stream()
                .map(tc -> modelMapper.map(tc, TipoCambioDTO.class))
                .collect(Collectors.toList());
    }

    public List<TipoCambioDTO> obtenerHistorialTasas(String desde, String hasta) {
        return tipoCambioRepository.findHistorialTasas(desde, hasta)
                .stream()
                .map(tc -> modelMapper.map(tc, TipoCambioDTO.class))
                .collect(Collectors.toList());
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
}