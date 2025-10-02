package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.Criptomoneda;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriptomonedaRepository extends JpaRepository<Criptomoneda, Long> {

    // Buscar por código
    Optional<Criptomoneda> findByCodigo(String codigo);

    // Buscar criptomonedas activas
    List<Criptomoneda> findByActivaTrue();

    // Verificar si existe por código
    boolean existsByCodigo(String codigo);

    // Buscar por nombre (búsqueda parcial)
    List<Criptomoneda> findByNombreContainingIgnoreCase(String nombre);

    // Criptomonedas más utilizadas en transacciones
    @Query("SELECT c.codigo, COUNT(t) FROM Criptomoneda c LEFT JOIN c.transacciones t GROUP BY c.codigo ORDER BY COUNT(t) DESC")
    List<Object[]> findCriptosMasUtilizadas();

    // Buscar criptomonedas con wallets activos
    @Query("SELECT DISTINCT c FROM Criptomoneda c JOIN c.wallets w WHERE w.estado = true")
    List<Criptomoneda> findConWalletsActivos();
}