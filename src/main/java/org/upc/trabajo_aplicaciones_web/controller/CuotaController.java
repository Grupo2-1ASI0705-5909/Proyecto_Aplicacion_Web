package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.CuotaDTO;
import org.upc.trabajo_aplicaciones_web.service.CuotaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
@RequiredArgsConstructor
public class CuotaController {

    private final CuotaService cuotaService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<CuotaDTO> crear(@RequestBody CuotaDTO cuotaDTO) {
        CuotaDTO nuevaCuota = cuotaService.crear(cuotaDTO);
        return new ResponseEntity<>(nuevaCuota, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/vencidas")
    public ResponseEntity<List<CuotaDTO>> obtenerCuotasVencidas() {
        List<CuotaDTO> cuotas = cuotaService.obtenerCuotasVencidas();
        return ResponseEntity.ok(cuotas);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/por-vencer")
    public ResponseEntity<List<CuotaDTO>> obtenerCuotasPorVencer() {
        List<CuotaDTO> cuotas = cuotaService.obtenerCuotasPorVencer();
        return ResponseEntity.ok(cuotas);
    }

    // ========== ADMINISTRADOR Y USUARIO ==========
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @PatchMapping("/{id}/pagar")
    public ResponseEntity<CuotaDTO> pagarCuota(@PathVariable Long id) {
        CuotaDTO cuotaPagada = cuotaService.pagarCuota(id, LocalDate.now());
        return ResponseEntity.ok(cuotaPagada);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CuotaDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<CuotaDTO> cuotas = cuotaService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(cuotas);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/plan-pago/{planPagoId}/proxima")
    public ResponseEntity<CuotaDTO> obtenerProximaCuotaPorVencer(@PathVariable Long planPagoId) {
        CuotaDTO cuota = cuotaService.obtenerProximaCuotaPorVencer(planPagoId);
        return ResponseEntity.ok(cuota);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/plan-pago/{planPagoId}/total-pendiente")
    public ResponseEntity<Double> calcularTotalPendiente(@PathVariable Long planPagoId) {
        Double total = cuotaService.calcularTotalPendientePorPlan(planPagoId);
        return ResponseEntity.ok(total);
    }

    // ========== ADMINISTRADOR Y COMERCIO ==========
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @PutMapping("/{id}")
    public ResponseEntity<CuotaDTO> actualizar(@PathVariable Long id, @RequestBody CuotaDTO cuotaDTO) {
        CuotaDTO cuotaActualizada = cuotaService.actualizar(id, cuotaDTO);
        return ResponseEntity.ok(cuotaActualizada);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @GetMapping("/plan-pago/{planPagoId}")
    public ResponseEntity<List<CuotaDTO>> obtenerPorPlanPago(@PathVariable Long planPagoId) {
        List<CuotaDTO> cuotas = cuotaService.obtenerPorPlanPago(planPagoId);
        return ResponseEntity.ok(cuotas);
    }

    // ========== TODOS LOS ROLES AUTENTICADOS ==========
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<CuotaDTO>> obtenerTodos() {
        List<CuotaDTO> cuotas = cuotaService.obtenerTodos();
        return ResponseEntity.ok(cuotas);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<CuotaDTO> obtenerPorId(@PathVariable Long id) {
        CuotaDTO cuota = cuotaService.obtenerPorId(id);
        return ResponseEntity.ok(cuota);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CuotaDTO>> obtenerPorEstado(@PathVariable String estado) {
        List<CuotaDTO> cuotas = cuotaService.obtenerPorEstado(estado);
        return ResponseEntity.ok(cuotas);
    }
}