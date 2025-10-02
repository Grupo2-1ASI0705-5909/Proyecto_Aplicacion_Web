package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.PlanPagoDTO;
import org.upc.trabajo_aplicaciones_web.model.PlanPago;
import org.upc.trabajo_aplicaciones_web.model.Transaccion;
import org.upc.trabajo_aplicaciones_web.repository.PlanPagoRepository;
import org.upc.trabajo_aplicaciones_web.repository.TransaccionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanPagoService {

    private final PlanPagoRepository planPagoRepository;
    private final TransaccionRepository transaccionRepository;
    private final ModelMapper modelMapper;

    public PlanPagoDTO crear(PlanPagoDTO planPagoDTO) {
        Transaccion transaccion = transaccionRepository.findById(planPagoDTO.getTransaccionId())
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        PlanPago planPago = modelMapper.map(planPagoDTO, PlanPago.class);
        planPago.setTransaccion(transaccion);

        // Calcular fecha fin automáticamente
        if (planPago.getFechaInicio() != null && planPago.getNumeroCuotas() != null) {
            planPago.setFechaFin(planPago.getFechaInicio().plusMonths(planPago.getNumeroCuotas()));
        }

        planPago = planPagoRepository.save(planPago);
        return modelMapper.map(planPago, PlanPagoDTO.class);
    }

    public List<PlanPagoDTO> obtenerTodos() {
        return planPagoRepository.findAll()
                .stream()
                .map(plan -> modelMapper.map(plan, PlanPagoDTO.class))
                .collect(Collectors.toList());
    }

    public PlanPagoDTO obtenerPorId(Long id) {
        PlanPago planPago = planPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de pago no encontrado"));
        return modelMapper.map(planPago, PlanPagoDTO.class);
    }

    public PlanPagoDTO actualizar(Long id, PlanPagoDTO planPagoDTO) {
        PlanPago planExistente = planPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de pago no encontrado"));

        planExistente.setNumeroCuotas(planPagoDTO.getNumeroCuotas());
        planExistente.setMontoPorCuota(planPagoDTO.getMontoPorCuota());
        planExistente.setInteres(planPagoDTO.getInteres());
        planExistente.setFechaInicio(planPagoDTO.getFechaInicio());
        planExistente.setFechaFin(planPagoDTO.getFechaFin());

        planExistente = planPagoRepository.save(planExistente);
        return modelMapper.map(planExistente, PlanPagoDTO.class);
    }

    public void eliminar(Long id) {
        if (!planPagoRepository.existsById(id)) {
            throw new RuntimeException("Plan de pago no encontrado");
        }
        planPagoRepository.deleteById(id);
    }

    public List<PlanPagoDTO> obtenerPorTransaccion(Long transaccionId) {
        return planPagoRepository.findByTransaccionTransaccionId(transaccionId)
                .stream()
                .map(plan -> modelMapper.map(plan, PlanPagoDTO.class))
                .collect(Collectors.toList());
    }

    public List<PlanPagoDTO> obtenerPlanesActivos() {
        return planPagoRepository.findPlanesActivos(LocalDate.now())
                .stream()
                .map(plan -> modelMapper.map(plan, PlanPagoDTO.class))
                .collect(Collectors.toList());
    }

    public List<PlanPagoDTO> obtenerPlanesVencidos() {
        return planPagoRepository.findPlanesVencidos(LocalDate.now())
                .stream()
                .map(plan -> modelMapper.map(plan, PlanPagoDTO.class))
                .collect(Collectors.toList());
    }

    public List<PlanPagoDTO> obtenerPorUsuario(Long usuarioId) {
        return planPagoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(plan -> modelMapper.map(plan, PlanPagoDTO.class))
                .collect(Collectors.toList());
    }

    public List<PlanPagoDTO> obtenerPlanesConCuotasPendientes() {
        return planPagoRepository.findPlanesConCuotasPendientes()
                .stream()
                .map(plan -> modelMapper.map(plan, PlanPagoDTO.class))
                .collect(Collectors.toList());
    }

    public Double calcularInteresTotal() {
        return planPagoRepository.calcularInteresTotal();
    }
}