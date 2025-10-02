package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.PermisoDTO;
import org.upc.trabajo_aplicaciones_web.service.PermisoService;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoController {

    private final PermisoService permisoService;

    @PostMapping
    public ResponseEntity<PermisoDTO> crear(@RequestBody PermisoDTO permisoDTO) {
        PermisoDTO nuevoPermiso = permisoService.crear(permisoDTO);
        return new ResponseEntity<>(nuevoPermiso, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PermisoDTO>> obtenerTodos() {
        List<PermisoDTO> permisos = permisoService.obtenerTodos();
        return ResponseEntity.ok(permisos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermisoDTO> obtenerPorId(@PathVariable Long id) {
        PermisoDTO permiso = permisoService.obtenerPorId(id);
        return ResponseEntity.ok(permiso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermisoDTO> actualizar(@PathVariable Long id, @RequestBody PermisoDTO permisoDTO) {
        PermisoDTO permisoActualizado = permisoService.actualizar(id, permisoDTO);
        return ResponseEntity.ok(permisoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        permisoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PermisoDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<PermisoDTO> permisos = permisoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(permisos);
    }

    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<PermisoDTO>> obtenerPermisosPorRol(@PathVariable Long rolId) {
        List<PermisoDTO> permisos = permisoService.obtenerPermisosPorRol(rolId);
        return ResponseEntity.ok(permisos);
    }
}