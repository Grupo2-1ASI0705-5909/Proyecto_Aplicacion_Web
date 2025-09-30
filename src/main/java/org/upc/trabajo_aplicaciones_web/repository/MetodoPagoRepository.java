package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.MetodoPago;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {

    // Buscar por nombre
    Optional<MetodoPago> findByNombre(String nombre);

    // Buscar métodos de pago activos
    List<MetodoPago> findByEstadoTrue();

    // Buscar por estado
    List<MetodoPago> findByEstado(Boolean estado);

    // Verificar si existe por nombre
    boolean existsByNombre(String nombre);

    // Contar métodos de pago activos
    long countByEstadoTrue();
}