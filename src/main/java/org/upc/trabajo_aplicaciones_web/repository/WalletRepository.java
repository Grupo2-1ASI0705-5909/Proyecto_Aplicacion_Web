package org.upc.trabajo_aplicaciones_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upc.trabajo_aplicaciones_web.model.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // Buscar por usuario
    List<Wallet> findByUsuarioUsuarioId(Long usuarioId);

    // Buscar por criptomoneda
    List<Wallet> findByCriptomonedaCriptoId(Long criptoId);

    // Buscar wallets activos
    List<Wallet> findByEstadoTrue();

    // Buscar wallet específico de usuario y criptomoneda
    Optional<Wallet> findByUsuarioUsuarioIdAndCriptomonedaCriptoId(Long usuarioId, Long criptoId);

    // Buscar por dirección
    Optional<Wallet> findByDireccion(String direccion);

    // Calcular saldo total por usuario
    @Query("SELECT SUM(w.saldo) FROM Wallet w WHERE w.usuario.usuarioId = :usuarioId AND w.estado = true")
    BigDecimal calcularSaldoTotalPorUsuario(@Param("usuarioId") Long usuarioId);

    // Wallets con saldo mayor a
    @Query("SELECT w FROM Wallet w WHERE w.saldo > :saldoMinimo AND w.estado = true")
    List<Wallet> findWalletsConSaldoMayorA(@Param("saldoMinimo") BigDecimal saldoMinimo);

    // Actualizar saldo
    @Query("UPDATE Wallet w SET w.saldo = :nuevoSaldo, w.ultimaActualizacion = CURRENT_TIMESTAMP WHERE w.walletId = :walletId")
    void actualizarSaldo(@Param("walletId") Long walletId, @Param("nuevoSaldo") BigDecimal nuevoSaldo);
}