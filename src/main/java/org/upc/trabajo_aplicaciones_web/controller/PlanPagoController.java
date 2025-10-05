package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.PlanPagoDTO;
import org.upc.trabajo_aplicaciones_web.service.PlanPagoService;

import java.util.List;

@RestController
@RequestMapping("/api/planes-pago")
@RequiredArgsConstructor
public class PlanPagoController {

    private final PlanPagoService planPagoService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<PlanPagoDTO> crear(@RequestBody PlanPagoDTO planPagoDTO) {
        PlanPagoDTO nuevoPlan = planPagoService.crear(planPagoDTO);
        return new ResponseEntity<>(nuevoPlan, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planPagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/vencidos")
    public ResponseEntity<List<PlanPagoDTO>> obtenerPlanesVencidos() {
        List<PlanPagoDTO> planes = planPagoService.obtenerPlanesVencidos();
        return ResponseEntity.ok(planes);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/con-cuotas-pendientes")
    public ResponseEntity<List<PlanPagoDTO>> obtenerPlanesConCuotasPendientes() {
        List<PlanPagoDTO> planes = planPagoService.obtenerPlanesConCuotasPendientes();
        return ResponseEntity.ok(planes);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/interes-total")
    public ResponseEntity<Double> calcularInteresTotal() {
        Double interesTotal = planPagoService.calcularInteresTotal();
        return ResponseEntity.ok(interesTotal);
    }

    // ========== ADMINISTRADOR Y USUARIO ==========
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PlanPagoDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<PlanPagoDTO> planes = planPagoService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(planes);
    }

    // ========== ADMINISTRADOR Y COMERCIO ==========
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @PutMapping("/{id}")
    public ResponseEntity<PlanPagoDTO> actualizar(@PathVariable Long id, @RequestBody PlanPagoDTO planPagoDTO) {
        PlanPagoDTO planActualizado = planPagoService.actualizar(id, planPagoDTO);
        return ResponseEntity.ok(planActualizado);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @GetMapping("/transaccion/{transaccionId}")
    public ResponseEntity<List<PlanPagoDTO>> obtenerPorTransaccion(@PathVariable Long transaccionId) {
        List<PlanPagoDTO> planes = planPagoService.obtenerPorTransaccion(transaccionId);
        return ResponseEntity.ok(planes);
    }

    // ========== TODOS LOS ROLES AUTENTICADOS ==========
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<PlanPagoDTO>> obtenerTodos() {
        List<PlanPagoDTO> planes = planPagoService.obtenerTodos();
        return ResponseEntity.ok(planes);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<PlanPagoDTO> obtenerPorId(@PathVariable Long id) {
        PlanPagoDTO plan = planPagoService.obtenerPorId(id);
        return ResponseEntity.ok(plan);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/activos")
    public ResponseEntity<List<PlanPagoDTO>> obtenerPlanesActivos() {
        List<PlanPagoDTO> planes = planPagoService.obtenerPlanesActivos();
        return ResponseEntity.ok(planes);
    }
}