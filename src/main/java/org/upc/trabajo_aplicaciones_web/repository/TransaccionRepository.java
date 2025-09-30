package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.Transaccion;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    // Buscar por usuario
    List<Transaccion> findByUsuarioId(Long usuarioId);

    // Buscar por comercio
    List<Transaccion> findByComercioId(Long comercioId);

    // Buscar por método de pago
    List<Transaccion> findByMetodoPagoId(Long metodoPagoId);

    // Buscar por estado
    List<Transaccion> findByEstado(String estado);

    // Buscar por rango de fechas
    List<Transaccion> findByFechaTransaccionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Buscar transacciones por usuario y estado
    List<Transaccion> findByUsuarioIdAndEstado(Long usuarioId, String estado);

    // Buscar transacciones recientes (últimos 30 días)
    @Query("SELECT t FROM Transaccion t WHERE t.fechaTransaccion >= :fecha")
    List<Transaccion> findTransaccionesRecientes(@Param("fecha") LocalDateTime fecha);

    // Calcular total de transacciones por usuario
    @Query("SELECT SUM(t.montoTotal) FROM Transaccion t WHERE t.usuario.id = :usuarioId")
    Double calcularTotalPorUsuario(@Param("usuarioId") Long usuarioId);

    // Contar transacciones por estado
    @Query("SELECT t.estado, COUNT(t) FROM Transaccion t GROUP BY t.estado")
    List<Object[]> countByEstado();

    // Top 5 comercios con más transacciones
    @Query("SELECT t.comercio, COUNT(t) as count FROM Transaccion t GROUP BY t.comercio ORDER BY count DESC")
    List<Object[]> findTopComercios(@Param("limit") int limit);
}