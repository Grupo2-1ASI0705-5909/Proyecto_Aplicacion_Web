package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.TransaccionDTO;
import org.upc.trabajo_aplicaciones_web.service.TransaccionService;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    @PostMapping
    public ResponseEntity<TransaccionDTO> crear(@RequestBody TransaccionDTO transaccionDTO) {
        TransaccionDTO nuevaTransaccion = transaccionService.crear(transaccionDTO);
        return new ResponseEntity<>(nuevaTransaccion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransaccionDTO>> obtenerTodos() {
        List<TransaccionDTO> transacciones = transaccionService.obtenerTodos();
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> obtenerPorId(@PathVariable Long id) {
        TransaccionDTO transaccion = transaccionService.obtenerPorId(id);
        return ResponseEntity.ok(transaccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionDTO> actualizar(@PathVariable Long id, @RequestBody TransaccionDTO transaccionDTO) {
        TransaccionDTO transaccionActualizada = transaccionService.actualizar(id, transaccionDTO);
        return ResponseEntity.ok(transaccionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        transaccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TransaccionDTO> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        TransaccionDTO transaccion = transaccionService.cambiarEstado(id, estado);
        return ResponseEntity.ok(transaccion);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<TransaccionDTO> transacciones = transaccionService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/comercio/{comercioId}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorComercio(@PathVariable Long comercioId) {
        List<TransaccionDTO> transacciones = transaccionService.obtenerPorComercio(comercioId);
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorEstado(@PathVariable String estado) {
        List<TransaccionDTO> transacciones = transaccionService.obtenerPorEstado(estado);
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/usuario/{usuarioId}/total")
    public ResponseEntity<Double> calcularTotalPorUsuario(@PathVariable Long usuarioId) {
        Double total = transaccionService.calcularTotalPorUsuario(usuarioId);
        return ResponseEntity.ok(total);
    }
}