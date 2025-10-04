package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.RolDTO;
import org.upc.trabajo_aplicaciones_web.service.RolService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class        RolController {

    private final RolService rolService;

    @PostMapping
    public ResponseEntity<RolDTO> crear(@RequestBody RolDTO rolDTO) {
        RolDTO nuevoRol = rolService.crear(rolDTO);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RolDTO>> obtenerTodos() {
        List<RolDTO> roles = rolService.obtenerTodos();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> obtenerPorId(@PathVariable Long id) {
        RolDTO rol = rolService.obtenerPorId(id);
        return ResponseEntity.ok(rol);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RolDTO> obtenerPorNombre(@PathVariable String nombre) {
        RolDTO rol = rolService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(rol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> actualizar(@PathVariable Long id, @RequestBody RolDTO rolDTO) {
        RolDTO rolActualizado = rolService.actualizar(id, rolDTO);
        return ResponseEntity.ok(rolActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RolDTO>> obtenerRolesPorUsuario(@PathVariable Long usuarioId) {
        List<RolDTO> roles = rolService.obtenerRolesPorUsuario(usuarioId);
        return ResponseEntity.ok(roles);
    }
}