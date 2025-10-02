package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.Cuota;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    // Buscar por plan de pago
    List<Cuota> findByPlanPagoPlanPagoId(Long planPagoId);

    // Buscar por estado
    List<Cuota> findByEstado(String estado);

    // Buscar por número de cuota
    List<Cuota> findByNumeroCuota(Integer numeroCuota);

    // Buscar cuotas vencidas
    @Query("SELECT c FROM Cuota c WHERE c.fechaVencimiento < :fechaActual AND c.estado != 'PAGADA'")
    List<Cuota> findCuotasVencidas(@Param("fechaActual") LocalDate fechaActual);

    // Buscar cuotas por vencer (próximos 7 días)
    @Query("SELECT c FROM Cuota c WHERE c.fechaVencimiento BETWEEN :fechaInicio AND :fechaFin AND c.estado = 'PENDIENTE'")
    List<Cuota> findCuotasPorVencer(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    // Buscar cuota específica por plan y número
    Optional<Cuota> findByPlanPagoPlanPagoIdAndNumeroCuota(Long planPagoId, Integer numeroCuota);

    // Calcular total pendiente por plan
    @Query("SELECT COALESCE(SUM(c.monto), 0) FROM Cuota c WHERE c.planPago.planPagoId = :planPagoId AND c.estado = 'PENDIENTE'")
    Double calcularTotalPendientePorPlan(@Param("planPagoId") Long planPagoId);

    // Contar cuotas por estado
    @Query("SELECT c.estado, COUNT(c) FROM Cuota c GROUP BY c.estado")
    List<Object[]> countByEstado();

    // Cuotas por usuario
    @Query("SELECT c FROM Cuota c WHERE c.planPago.transaccion.usuario.usuarioId = :usuarioId")
    List<Cuota> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Próxima cuota a vencer por plan
    @Query("SELECT c FROM Cuota c WHERE c.planPago.planPagoId = :planPagoId AND c.estado = 'PENDIENTE' " +
            "ORDER BY c.fechaVencimiento ASC LIMIT 1")
    Optional<Cuota> findProximaCuotaPorVencer(@Param("planPagoId") Long planPagoId);
}