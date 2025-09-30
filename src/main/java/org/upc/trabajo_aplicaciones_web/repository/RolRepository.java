package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.Rol;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    // Buscar por nombre
    Optional<Rol> findByNombre(String nombre);

    // Verificar si existe por nombre
    boolean existsByNombre(String nombre);

    // Buscar roles por usuario
    @Query("SELECT r FROM Rol r JOIN r.usuarios u WHERE u.id = :usuarioId")
    List<Rol> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Buscar roles con permisos específicos
    @Query("SELECT r FROM Rol r JOIN r.permisos p WHERE p.id = :permisoId")
    List<Rol> findByPermisoId(@Param("permisoId") Long permisoId);
}