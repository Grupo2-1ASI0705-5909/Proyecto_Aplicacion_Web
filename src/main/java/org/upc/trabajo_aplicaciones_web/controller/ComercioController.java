package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.ComercioDTO;
import org.upc.trabajo_aplicaciones_web.service.ComercioService;

import java.util.List;

@RestController
@RequestMapping("/api/comercios")
@RequiredArgsConstructor
public class ComercioController {

    private final ComercioService comercioService;

    @PostMapping
    public ResponseEntity<ComercioDTO> crear(@RequestBody ComercioDTO comercioDTO) {
        ComercioDTO nuevoComercio = comercioService.crear(comercioDTO);
        return new ResponseEntity<>(nuevoComercio, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ComercioDTO>> obtenerTodos() {
        List<ComercioDTO> comercios = comercioService.obtenerTodos();
        return ResponseEntity.ok(comercios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComercioDTO> obtenerPorId(@PathVariable Long id) {
        ComercioDTO comercio = comercioService.obtenerPorId(id);
        return ResponseEntity.ok(comercio);
    }

    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<ComercioDTO> obtenerPorRuc(@PathVariable String ruc) {
        ComercioDTO comercio = comercioService.obtenerPorRuc(ruc);
        return ResponseEntity.ok(comercio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComercioDTO> actualizar(@PathVariable Long id, @RequestBody ComercioDTO comercioDTO) {
        ComercioDTO comercioActualizado = comercioService.actualizar(id, comercioDTO);
        return ResponseEntity.ok(comercioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        comercioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ComercioDTO> cambiarEstado(@PathVariable Long id, @RequestParam Boolean estado) {
        ComercioDTO comercio = comercioService.cambiarEstado(id, estado);
        return ResponseEntity.ok(comercio);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ComercioDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<ComercioDTO> comercios = comercioService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(comercios);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ComercioDTO>> obtenerPorCategoria(@PathVariable String categoria) {
        List<ComercioDTO> comercios = comercioService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(comercios);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ComercioDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<ComercioDTO> comercios = comercioService.buscarPorNombre(nombre);
        return ResponseEntity.ok(comercios);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ComercioDTO>> obtenerActivos() {
        List<ComercioDTO> comercios = comercioService.obtenerActivos();
        return ResponseEntity.ok(comercios);
    }
}