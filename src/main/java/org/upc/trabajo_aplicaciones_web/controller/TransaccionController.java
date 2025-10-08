package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.TransaccionDTO;
import org.upc.trabajo_aplicaciones_web.service.TransaccionService;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    //actualizacion
    private final TransaccionService transaccionService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<TransaccionDTO>> obtenerTodos() {
        List<TransaccionDTO> transacciones = transaccionService.obtenerTodos();
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        transaccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/cripto")
    public ResponseEntity<List<TransaccionDTO>> obtenerTransaccionesConCripto() {
        List<TransaccionDTO> transacciones = transaccionService.obtenerTransaccionesConCripto();
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/recientes")
    public ResponseEntity<List<TransaccionDTO>> obtenerRecientes() {
        List<TransaccionDTO> transacciones = transaccionService.obtenerRecientes();
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @PostMapping
    public ResponseEntity<TransaccionDTO> crear(@RequestBody TransaccionDTO transaccionDTO) {
        TransaccionDTO nuevaTransaccion = transaccionService.crear(transaccionDTO);
        return new ResponseEntity<>(nuevaTransaccion, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<TransaccionDTO> transacciones = transaccionService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/usuario/{usuarioId}/total-fiat")
    public ResponseEntity<Double> calcularTotalFiatPorUsuario(@PathVariable Long usuarioId) {
        Double total = transaccionService.calcularTotalFiatPorUsuario(usuarioId);
        return ResponseEntity.ok(total);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/usuario/{usuarioId}/total-cripto")
    public ResponseEntity<Double> calcularTotalCriptoPorUsuario(@PathVariable Long usuarioId) {
        Double total = transaccionService.calcularTotalCriptoPorUsuario(usuarioId);
        return ResponseEntity.ok(total);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @GetMapping("/comercio/{comercioId}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorComercio(@PathVariable Long comercioId) {
        List<TransaccionDTO> transacciones = transaccionService.obtenerPorComercio(comercioId);
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @PutMapping("/{id}")
    public ResponseEntity<TransaccionDTO> actualizar(@PathVariable Long id, @RequestBody TransaccionDTO transaccionDTO) {
        TransaccionDTO transaccionActualizada = transaccionService.actualizar(id, transaccionDTO);
        return ResponseEntity.ok(transaccionActualizada);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TransaccionDTO> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        TransaccionDTO transaccion = transaccionService.cambiarEstado(id, estado);
        return ResponseEntity.ok(transaccion);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> obtenerPorId(@PathVariable Long id) {
        TransaccionDTO transaccion = transaccionService.obtenerPorId(id);
        return ResponseEntity.ok(transaccion);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorEstado(@PathVariable String estado) {
        List<TransaccionDTO> transacciones = transaccionService.obtenerPorEstado(estado);
        return ResponseEntity.ok(transacciones);
    }

}
