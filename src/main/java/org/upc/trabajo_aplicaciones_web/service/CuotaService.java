package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.CuotaDTO;
import org.upc.trabajo_aplicaciones_web.dto.PlanPagoDTO;
import org.upc.trabajo_aplicaciones_web.model.Cuota;
import org.upc.trabajo_aplicaciones_web.model.PlanPago;
import org.upc.trabajo_aplicaciones_web.repository.CuotaRepository;
import org.upc.trabajo_aplicaciones_web.repository.PlanPagoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuotaService {
    private final CuotaRepository cuotaRepository;
    private final PlanPagoRepository planPagoRepository;

    public CuotaDTO crear(CuotaDTO cuotaDTO) {
        PlanPago planPago = planPagoRepository.findById(cuotaDTO.getPlanPagoId())
                .orElseThrow(() -> new RuntimeException("Plan de pago no encontrado"));

        Cuota cuota = new Cuota();
        cuota.setPlanPago(planPago);
        cuota.setNumeroCuota(cuotaDTO.getNumeroCuota());
        cuota.setMonto(cuotaDTO.getMonto());
        cuota.setFechaVencimiento(cuotaDTO.getFechaVencimiento());
        cuota.setFechaPago(cuotaDTO.getFechaPago());
        cuota.setEstado(cuotaDTO.getEstado() != null ? cuotaDTO.getEstado() : "PENDIENTE");

        cuota = cuotaRepository.save(cuota);
        return convertirACuotaDTO(cuota);
    }

    public List<CuotaDTO> obtenerTodos() {
        List<Cuota> cuotas = cuotaRepository.findAll();
        List<CuotaDTO> cuotaDTOs = new ArrayList<>();
        for (Cuota cuota : cuotas) {
            cuotaDTOs.add(convertirACuotaDTO(cuota));
        }
        return cuotaDTOs;
    }

    public CuotaDTO obtenerPorId(Long id) {
        Cuota cuota = cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));
        return convertirACuotaDTO(cuota);
    }

    public CuotaDTO actualizar(Long id, CuotaDTO cuotaDTO) {
        Cuota cuotaExistente = cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));

        cuotaExistente.setMonto(cuotaDTO.getMonto());
        cuotaExistente.setFechaVencimiento(cuotaDTO.getFechaVencimiento());
        cuotaExistente.setFechaPago(cuotaDTO.getFechaPago());
        cuotaExistente.setEstado(cuotaDTO.getEstado());

        cuotaExistente = cuotaRepository.save(cuotaExistente);
        return convertirACuotaDTO(cuotaExistente);
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
        return convertirACuotaDTO(cuota);
    }

    public List<CuotaDTO> obtenerPorPlanPago(Long planPagoId) {
        List<Cuota> cuotas = cuotaRepository.findByPlanPagoPlanPagoId(planPagoId);
        List<CuotaDTO> cuotaDTOs = new ArrayList<>();
        for (Cuota cuota : cuotas) {
            cuotaDTOs.add(convertirACuotaDTO(cuota));
        }
        return cuotaDTOs;
    }

    public List<CuotaDTO> obtenerPorEstado(String estado) {
        List<Cuota> cuotas = cuotaRepository.findByEstado(estado);
        List<CuotaDTO> cuotaDTOs = new ArrayList<>();
        for (Cuota cuota : cuotas) {
            cuotaDTOs.add(convertirACuotaDTO(cuota));
        }
        return cuotaDTOs;
    }

    public List<CuotaDTO> obtenerCuotasVencidas() {
        List<Cuota> cuotas = cuotaRepository.findCuotasVencidas(LocalDate.now());
        List<CuotaDTO> cuotaDTOs = new ArrayList<>();
        for (Cuota cuota : cuotas) {
            cuotaDTOs.add(convertirACuotaDTO(cuota));
        }
        return cuotaDTOs;
    }

    public List<CuotaDTO> obtenerCuotasPorVencer() {
        LocalDate inicio = LocalDate.now();
        LocalDate fin = inicio.plusDays(7);
        List<Cuota> cuotas = cuotaRepository.findCuotasPorVencer(inicio, fin);
        List<CuotaDTO> cuotaDTOs = new ArrayList<>();
        for (Cuota cuota : cuotas) {
            cuotaDTOs.add(convertirACuotaDTO(cuota));
        }
        return cuotaDTOs;
    }

    public List<CuotaDTO> obtenerPorUsuario(Long usuarioId) {
        List<Cuota> cuotas = cuotaRepository.findByUsuarioId(usuarioId);
        List<CuotaDTO> cuotaDTOs = new ArrayList<>();
        for (Cuota cuota : cuotas) {
            cuotaDTOs.add(convertirACuotaDTO(cuota));
        }
        return cuotaDTOs;
    }

    public CuotaDTO obtenerProximaCuotaPorVencer(Long planPagoId) {
        Cuota cuota = cuotaRepository.findProximaCuotaPorVencer(planPagoId)
                .orElseThrow(() -> new RuntimeException("No hay cuotas pendientes"));
        return convertirACuotaDTO(cuota);
    }

    public Double calcularTotalPendientePorPlan(Long planPagoId) {
        return cuotaRepository.calcularTotalPendientePorPlan(planPagoId);
    }

    private CuotaDTO convertirACuotaDTO(Cuota cuota) {
        CuotaDTO dto = new CuotaDTO();
        dto.setCuotaId(cuota.getCuotaId());
        dto.setPlanPagoId(cuota.getPlanPago().getPlanPagoId());
        dto.setNumeroCuota(cuota.getNumeroCuota());
        dto.setMonto(cuota.getMonto());
        dto.setFechaVencimiento(cuota.getFechaVencimiento());
        dto.setFechaPago(cuota.getFechaPago());
        dto.setEstado(cuota.getEstado());
        dto.setVencida(cuota.estaVencida());

        PlanPagoDTO planPagoDTO = new PlanPagoDTO();
        planPagoDTO.setPlanPagoId(cuota.getPlanPago().getPlanPagoId());
        planPagoDTO.setTransaccionId(cuota.getPlanPago().getTransaccion().getTransaccionId());
        planPagoDTO.setNumeroCuotas(cuota.getPlanPago().getNumeroCuotas());
        planPagoDTO.setMontoPorCuota(cuota.getPlanPago().getMontoPorCuota());
        planPagoDTO.setInteres(cuota.getPlanPago().getInteres());
        dto.setPlanPago(planPagoDTO);

        return dto;
    }
}