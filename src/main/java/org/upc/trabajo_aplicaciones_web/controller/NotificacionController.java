package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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



    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> obtenerTodos() {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerTodos();
        return ResponseEntity.ok(notificaciones);
    }
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> obtenerPorId(@PathVariable Long id) {
        NotificacionDTO notificacion = notificacionService.obtenerPorId(id);
        return ResponseEntity.ok(notificacion);
    }
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionDTO> actualizar(@PathVariable Long id, @RequestBody NotificacionDTO notificacionDTO) {
        NotificacionDTO notificacionActualizada = notificacionService.actualizar(id, notificacionDTO);
        return ResponseEntity.ok(notificacionActualizada);
    }
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR')")
    @PatchMapping("/{id}/leer")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable Long id) {
        NotificacionDTO notificacion = notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok(notificacion);
    }
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(notificaciones);
    }
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR')")
    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionDTO>> obtenerNoLeidasPorUsuario(@PathVariable Long usuarioId) {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerNoLeidasPorUsuario(usuarioId);
        return ResponseEntity.ok(notificaciones);
    }
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/recientes")
    public ResponseEntity<List<NotificacionDTO>> obtenerRecientes() {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerRecientes();
        return ResponseEntity.ok(notificaciones);
    }
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR')")
    @PatchMapping("/usuario/{usuarioId}/marcar-todas-leidas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable Long usuarioId) {
        notificacionService.marcarTodasComoLeidas(usuarioId);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR')")
    @GetMapping("/usuario/{usuarioId}/contar-no-leidas")
    public ResponseEntity<Long> contarNoLeidasPorUsuario(@PathVariable Long usuarioId) {
        long cantidad = notificacionService.contarNoLeidasPorUsuario(usuarioId);
        return ResponseEntity.ok(cantidad);
    }
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<NotificacionDTO>> obtenerPorTipo(@PathVariable String tipo) {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerPorTipo(tipo);
        return ResponseEntity.ok(notificaciones);
    }
}