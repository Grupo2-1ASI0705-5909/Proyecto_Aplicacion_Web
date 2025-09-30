package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.CuotaDTO;
import org.upc.trabajo_aplicaciones_web.model.Cuota;
import org.upc.trabajo_aplicaciones_web.model.PlanPago;
import org.upc.trabajo_aplicaciones_web.repository.CuotaRepository;
import org.upc.trabajo_aplicaciones_web.repository.PlanPagoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuotaService {

    private final CuotaRepository cuotaRepository;
    private final PlanPagoRepository planPagoRepository;
    private final ModelMapper modelMapper;

    public CuotaDTO crear(CuotaDTO cuotaDTO) {
        PlanPago planPago = planPagoRepository.findById(cuotaDTO.getPlanPagoId())
                .orElseThrow(() -> new RuntimeException("Plan de pago no encontrado"));

        Cuota cuota = modelMapper.map(cuotaDTO, Cuota.class);
        cuota.setPlanPago(planPago);

        cuota = cuotaRepository.save(cuota);

        // Calcular si está vencida
        CuotaDTO respuesta = modelMapper.map(cuota, CuotaDTO.class);
        respuesta.setVencida(cuota.estaVencida());

        return respuesta;
    }

    public List<CuotaDTO> obtenerTodos() {
        return cuotaRepository.findAll()
                .stream()
                .map(cuota -> {
                    CuotaDTO dto = modelMapper.map(cuota, CuotaDTO.class);
                    dto.setVencida(cuota.estaVencida());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public CuotaDTO obtenerPorId(Long id) {
        Cuota cuota = cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));

        CuotaDTO dto = modelMapper.map(cuota, CuotaDTO.class);
        dto.setVencida(cuota.estaVencida());

        return dto;
    }

    public CuotaDTO actualizar(Long id, CuotaDTO cuotaDTO) {
        Cuota cuotaExistente = cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));

        cuotaExistente.setMonto(cuotaDTO.getMonto());
        cuotaExistente.setFechaVencimiento(cuotaDTO.getFechaVencimiento());
        cuotaExistente.setFechaPago(cuotaDTO.getFechaPago());
        cuotaExistente.setEstado(cuotaDTO.getEstado());

        cuotaExistente = cuotaRepository.save(cuotaExistente);

        CuotaDTO respuesta = modelMapper.map(cuotaExistente, CuotaDTO.class);
        respuesta.setVencida(cuotaExistente.estaVencida());

        return respuesta;
    }

    public void eliminar(Long id) {
        if (!cuotaRepository.existsById(id)) {
            throw new RuntimeException("Cuota no encontrada");
        }
        cuotaRepository.deleteById(id);
    }

    public CuotaDTO pagarCuota(Long id, LocalDate fechaPago) {
        Cuota cuota = cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));

        cuota.setFechaPago(fechaPago);
        cuota.setEstado("PAGADA");

        cuota = cuotaRepository.save(cuota);

        CuotaDTO dto = modelMapper.map(cuota, CuotaDTO.class);
        dto.setVencida(false); // Ya está pagada, no está vencida

        return dto;
    }

    public List<CuotaDTO> obtenerPorPlanPago(Long planPagoId) {
        return cuotaRepository.findByPlanPagoId(planPagoId)
                .stream()
                .map(cuota -> {
                    CuotaDTO dto = modelMapper.map(cuota, CuotaDTO.class);
                    dto.setVencida(cuota.estaVencida());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<CuotaDTO> obtenerPorEstado(String estado) {
        return cuotaRepository.findByEstado(estado)
                .stream()
                .map(cuota -> {
                    CuotaDTO dto = modelMapper.map(cuota, CuotaDTO.class);
                    dto.setVencida(cuota.estaVencida());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<CuotaDTO> obtenerCuotasVencidas() {
        return cuotaRepository.findCuotasVencidas(LocalDate.now())
                .stream()
                .map(cuota -> {
                    CuotaDTO dto = modelMapper.map(cuota, CuotaDTO.class);
                    dto.setVencida(true); // Siempre true para cuotas vencidas
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<CuotaDTO> obtenerCuotasPorVencer() {
        LocalDate inicio = LocalDate.now();
        LocalDate fin = inicio.plusDays(7);

        return cuotaRepository.findCuotasPorVencer(inicio, fin)
                .stream()
                .map(cuota -> {
                    CuotaDTO dto = modelMapper.map(cuota, CuotaDTO.class);
                    dto.setVencida(cuota.estaVencida());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Double calcularTotalPendientePorPlan(Long planPagoId) {
        Double total = cuotaRepository.calcularTotalPendientePorPlan(planPagoId);
        return total != null ? total : 0.0;
    }
}