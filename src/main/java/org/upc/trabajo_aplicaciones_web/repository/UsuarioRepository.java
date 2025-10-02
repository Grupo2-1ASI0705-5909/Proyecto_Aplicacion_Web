package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar por email
    Optional<Usuario> findByEmail(String email);

    // Verificar si existe por email
    boolean existsByEmail(String email);

    // Buscar por estado
    List<Usuario> findByEstado(Boolean estado);

    // Buscar por nombre o apellido
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre% OR u.apellido LIKE %:nombre%")
    List<Usuario> buscarPorNombre(@Param("nombre") String nombre);

    // Buscar usuarios por rol
    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.rolId = :rolId")
    List<Usuario> findByRolId(@Param("rolId") Long rolId);

    // Contar usuarios activos
    long countByEstadoTrue();

    // Buscar usuarios con comercios
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.comercios WHERE u.usuarioId = :usuarioId")
    Optional<Usuario> findWithComerciosById(@Param("usuarioId") Long usuarioId);
}