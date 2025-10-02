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
    List<Transaccion> findByUsuarioUsuarioId(Long usuarioId);

    // Buscar por comercio
    List<Transaccion> findByComercioComercioId(Long comercioId);

    // Buscar por método de pago
    List<Transaccion> findByMetodoPagoMetodoPagoId(Long metodoPagoId);

    // Buscar por criptomoneda
    List<Transaccion> findByCriptomonedaCriptoId(Long criptoId);

    // Buscar por estado
    List<Transaccion> findByEstado(String estado);

    // Buscar por rango de fechas
    List<Transaccion> findByFechaTransaccionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Buscar transacciones por usuario y estado
    List<Transaccion> findByUsuarioUsuarioIdAndEstado(Long usuarioId, String estado);

    // Buscar transacciones recientes (últimos 30 días)
    @Query("SELECT t FROM Transaccion t WHERE t.fechaTransaccion >= :fecha")
    List<Transaccion> findTransaccionesRecientes(@Param("fecha") LocalDateTime fecha);

    // Calcular total de transacciones por usuario
    @Query("SELECT COALESCE(SUM(t.montoTotalFiat), 0) FROM Transaccion t WHERE t.usuario.usuarioId = :usuarioId")
    Double calcularTotalFiatPorUsuario(@Param("usuarioId") Long usuarioId);

    // Calcular total de cripto por usuario
    @Query("SELECT COALESCE(SUM(t.montoTotalCripto), 0) FROM Transaccion t WHERE t.usuario.usuarioId = :usuarioId")
    Double calcularTotalCriptoPorUsuario(@Param("usuarioId") Long usuarioId);

    // Contar transacciones por estado
    @Query("SELECT t.estado, COUNT(t) FROM Transaccion t GROUP BY t.estado")
    List<Object[]> countByEstado();

    // Top comercios con más transacciones
    @Query("SELECT t.comercio.nombreComercial, COUNT(t) as count FROM Transaccion t GROUP BY t.comercio.nombreComercial ORDER BY count DESC")
    List<Object[]> findTopComercios(@Param("limit") int limit);

    // Transacciones con criptomonedas
    @Query("SELECT t FROM Transaccion t WHERE t.criptomoneda IS NOT NULL")
    List<Transaccion> findTransaccionesConCripto();

    // Volumen de transacciones por día
    @Query("SELECT DATE(t.fechaTransaccion), COUNT(t), COALESCE(SUM(t.montoTotalFiat), 0) " +
            "FROM Transaccion t GROUP BY DATE(t.fechaTransaccion) ORDER BY DATE(t.fechaTransaccion) DESC")
    List<Object[]> findVolumenDiario();
}