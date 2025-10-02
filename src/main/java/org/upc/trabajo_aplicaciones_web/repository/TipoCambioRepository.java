package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.TipoCambio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TipoCambioRepository extends JpaRepository<TipoCambio, Long> {

    // Buscar tasa más reciente por par de monedas
    @Query("SELECT tc FROM TipoCambio tc WHERE tc.desdeCodigo = :desde AND tc.hastaCodigo = :hasta " +
            "ORDER BY tc.fechaHora DESC LIMIT 1")
    Optional<TipoCambio> findTasaMasReciente(@Param("desde") String desde, @Param("hasta") String hasta);

    // Buscar tasas por rango de fechas
    List<TipoCambio> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Buscar tasas por par de monedas
    List<TipoCambio> findByDesdeCodigoAndHastaCodigo(String desdeCodigo, String hastaCodigo);

    // Tasas más recientes de todos los pares
    @Query("SELECT tc1 FROM TipoCambio tc1 WHERE tc1.fechaHora = " +
            "(SELECT MAX(tc2.fechaHora) FROM TipoCambio tc2 WHERE tc2.desdeCodigo = tc1.desdeCodigo AND tc2.hastaCodigo = tc1.hastaCodigo)")
    List<TipoCambio> findTasasMasRecientes();

    // Histórico de tasas para un par específico
    @Query("SELECT tc FROM TipoCambio tc WHERE tc.desdeCodigo = :desde AND tc.hastaCodigo = :hasta " +
            "ORDER BY tc.fechaHora DESC")
    List<TipoCambio> findHistorialTasas(@Param("desde") String desde, @Param("hasta") String hasta);

    // Calcular promedio de tasas en un período
    @Query("SELECT AVG(tc.tasa) FROM TipoCambio tc WHERE tc.desdeCodigo = :desde AND tc.hastaCodigo = :hasta " +
            "AND tc.fechaHora BETWEEN :fechaInicio AND :fechaFin")
    Double calcularPromedioTasas(@Param("desde") String desde, @Param("hasta") String hasta,
                                 @Param("fechaInicio") LocalDateTime fechaInicio,
                                 @Param("fechaFin") LocalDateTime fechaFin);
}