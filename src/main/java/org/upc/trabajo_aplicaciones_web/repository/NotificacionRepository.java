package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.Notificacion;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Buscar por usuario
    List<Notificacion> findByUsuarioId(Long usuarioId);

    // Buscar por estado de lectura
    List<Notificacion> findByLeido(Boolean leido);

    // Buscar notificaciones no leídas por usuario
    List<Notificacion> findByUsuarioIdAndLeidoFalse(Long usuarioId);

    // Buscar notificaciones recientes (últimos 7 días)
    @Query("SELECT n FROM Notificacion n WHERE n.fechaEnvio >= :fecha")
    List<Notificacion> findNotificacionesRecientes(@Param("fecha") LocalDateTime fecha);

    // Buscar por rango de fechas
    List<Notificacion> findByFechaEnvioBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Contar notificaciones no leídas por usuario
    long countByUsuarioIdAndLeidoFalse(Long usuarioId);

    // Marcar notificaciones como leídas
    @Query("UPDATE Notificacion n SET n.leido = true WHERE n.usuario.id = :usuarioId AND n.leido = false")
    void marcarComoLeidas(@Param("usuarioId") Long usuarioId);
}