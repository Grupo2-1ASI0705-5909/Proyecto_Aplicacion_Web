package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.NotificacionDTO;
import org.upc.trabajo_aplicaciones_web.service.NotificacionService;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(@RequestBody NotificacionDTO notificacionDTO) {
        NotificacionDTO nuevaNotificacion = notificacionService.crear(notificacionDTO);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> obtenerTodos() {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerTodos();
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> obtenerPorId(@PathVariable Long id) {
        NotificacionDTO notificacion = notificacionService.obtenerPorId(id);
        return ResponseEntity.ok(notificacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacionDTO> actualizar(@PathVariable Long id, @RequestBody NotificacionDTO notificacionDTO) {
        NotificacionDTO notificacionActualizada = notificacionService.actualizar(id, notificacionDTO);
        return ResponseEntity.ok(notificacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable Long id) {
        NotificacionDTO notificacion = notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok(notificacion);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionDTO>> obtenerNoLeidasPorUsuario(@PathVariable Long usuarioId) {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerNoLeidasPorUsuario(usuarioId);
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<NotificacionDTO>> obtenerRecientes() {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerRecientes();
        return ResponseEntity.ok(notificaciones);
    }

    @PatchMapping("/usuario/{usuarioId}/marcar-todas-leidas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable Long usuarioId) {
        notificacionService.marcarTodasComoLeidas(usuarioId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario/{usuarioId}/contar-no-leidas")
    public ResponseEntity<Long> contarNoLeidasPorUsuario(@PathVariable Long usuarioId) {
        long cantidad = notificacionService.contarNoLeidasPorUsuario(usuarioId);
        return ResponseEntity.ok(cantidad);
    }
}