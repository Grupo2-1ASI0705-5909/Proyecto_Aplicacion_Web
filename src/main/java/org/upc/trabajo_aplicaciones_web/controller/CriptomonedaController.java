package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.CriptomonedaDTO;
import org.upc.trabajo_aplicaciones_web.service.CriptomonedaService;

import java.util.List;

@RestController
@RequestMapping("/api/criptomonedas")
@RequiredArgsConstructor
public class CriptomonedaController {

    private final CriptomonedaService criptomonedaService;

    @PostMapping
    public ResponseEntity<CriptomonedaDTO> crear(@RequestBody CriptomonedaDTO criptomonedaDTO) {
        CriptomonedaDTO nuevaCripto = criptomonedaService.crear(criptomonedaDTO);
        return new ResponseEntity<>(nuevaCripto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CriptomonedaDTO>> obtenerTodos() {
        List<CriptomonedaDTO> criptos = criptomonedaService.obtenerTodos();
        return ResponseEntity.ok(criptos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriptomonedaDTO> obtenerPorId(@PathVariable Long id) {
        CriptomonedaDTO cripto = criptomonedaService.obtenerPorId(id);
        return ResponseEntity.ok(cripto);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CriptomonedaDTO> obtenerPorCodigo(@PathVariable String codigo) {
        CriptomonedaDTO cripto = criptomonedaService.obtenerPorCodigo(codigo);
        return ResponseEntity.ok(cripto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriptomonedaDTO> actualizar(@PathVariable Long id, @RequestBody CriptomonedaDTO criptomonedaDTO) {
        CriptomonedaDTO criptoActualizada = criptomonedaService.actualizar(id, criptomonedaDTO);
        return ResponseEntity.ok(criptoActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        criptomonedaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<CriptomonedaDTO> cambiarEstado(@PathVariable Long id, @RequestParam Boolean activa) {
        CriptomonedaDTO cripto = criptomonedaService.cambiarEstado(id, activa);
        return ResponseEntity.ok(cripto);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<CriptomonedaDTO>> obtenerActivas() {
        List<CriptomonedaDTO> criptos = criptomonedaService.obtenerActivas();
        return ResponseEntity.ok(criptos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CriptomonedaDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<CriptomonedaDTO> criptos = criptomonedaService.buscarPorNombre(nombre);
        return ResponseEntity.ok(criptos);
    }
}