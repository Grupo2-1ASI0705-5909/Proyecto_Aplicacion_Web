package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.Comercio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComercioRepository extends JpaRepository<Comercio, Long> {

    // Buscar por RUC
    Optional<Comercio> findByRuc(String ruc);

    // Verificar si existe por RUC
    boolean existsByRuc(String ruc);

    // Buscar por usuario
    List<Comercio> findByUsuarioUsuarioId(Long usuarioId);

    // Buscar por estado
    List<Comercio> findByEstado(Boolean estado);

    // Buscar por categoría
    List<Comercio> findByCategoria(String categoria);

    // Buscar por nombre comercial (búsqueda parcial)
    List<Comercio> findByNombreComercialContainingIgnoreCase(String nombreComercial);

    // Buscar comercios activos por usuario
    List<Comercio> findByUsuarioUsuarioIdAndEstadoTrue(Long usuarioId);

    // Contar comercios por categoría
    @Query("SELECT c.categoria, COUNT(c) FROM Comercio c GROUP BY c.categoria")
    List<Object[]> countByCategoria();

    // Buscar comercios con transacciones recientes
    @Query("SELECT c FROM Comercio c WHERE c IN " +
            "(SELECT t.comercio FROM Transaccion t WHERE t.fechaTransaccion >= :fechaLimite)")
    List<Comercio> findConTransaccionesRecientes(LocalDateTime fechaLimite);
}