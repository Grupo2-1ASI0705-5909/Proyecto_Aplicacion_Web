package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.PlanPago;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlanPagoRepository extends JpaRepository<PlanPago, Long> {

    // Buscar por transacción
    List<PlanPago> findByTransaccionTransaccionId(Long transaccionId);

    // Buscar planes que vencen en una fecha específica
    List<PlanPago> findByFechaFin(LocalDate fechaFin);

    // Buscar planes activos (no vencidos)
    @Query("SELECT p FROM PlanPago p WHERE p.fechaFin >= :fechaActual")
    List<PlanPago> findPlanesActivos(@Param("fechaActual") LocalDate fechaActual);

    // Buscar planes vencidos
    @Query("SELECT p FROM PlanPago p WHERE p.fechaFin < :fechaActual")
    List<PlanPago> findPlanesVencidos(@Param("fechaActual") LocalDate fechaActual);

    // Buscar planes por rango de fechas
    List<PlanPago> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Calcular interés total generado
    @Query("SELECT COALESCE(SUM(p.interes), 0) FROM PlanPago p")
    Double calcularInteresTotal();

    // Planes con cuotas pendientes
    @Query("SELECT DISTINCT p FROM PlanPago p JOIN p.cuotas c WHERE c.estado = 'PENDIENTE'")
    List<PlanPago> findPlanesConCuotasPendientes();

    // Planes por usuario
    @Query("SELECT p FROM PlanPago p WHERE p.transaccion.usuario.usuarioId = :usuarioId")
    List<PlanPago> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}