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
    List<Cuota> findByPlanPagoId(Long planPagoId);

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
    Optional<Cuota> findByPlanPagoIdAndNumeroCuota(Long planPagoId, Integer numeroCuota);

    // Calcular total pendiente por plan
    @Query("SELECT SUM(c.monto) FROM Cuota c WHERE c.planPago.id = :planPagoId AND c.estado = 'PENDIENTE'")
    Double calcularTotalPendientePorPlan(@Param("planPagoId") Long planPagoId);

    // Contar cuotas por estado
    @Query("SELECT c.estado, COUNT(c) FROM Cuota c GROUP BY c.estado")
    List<Object[]> countByEstado();
}