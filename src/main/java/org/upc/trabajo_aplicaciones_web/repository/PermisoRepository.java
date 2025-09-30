package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.Permiso;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    // Buscar por nombre
    Optional<Permiso> findByNombre(String nombre);

    // Verificar si existe por nombre
    boolean existsByNombre(String nombre);

    // Buscar permisos por rol
    @Query("SELECT p FROM Permiso p JOIN p.roles r WHERE r.id = :rolId")
    List<Permiso> findByRolId(@Param("rolId") Long rolId);

    // Buscar permisos por nombre (búsqueda parcial)
    List<Permiso> findByNombreContainingIgnoreCase(String nombre);
}