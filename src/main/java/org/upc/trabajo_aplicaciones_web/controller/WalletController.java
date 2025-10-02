package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.WalletDTO;
import org.upc.trabajo_aplicaciones_web.service.WalletService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletDTO> crear(@RequestBody WalletDTO walletDTO) {
        WalletDTO nuevoWallet = walletService.crear(walletDTO);
        return new ResponseEntity<>(nuevoWallet, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WalletDTO>> obtenerTodos() {
        List<WalletDTO> wallets = walletService.obtenerTodos();
        return ResponseEntity.ok(wallets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletDTO> obtenerPorId(@PathVariable Long id) {
        WalletDTO wallet = walletService.obtenerPorId(id);
        return ResponseEntity.ok(wallet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WalletDTO> actualizar(@PathVariable Long id, @RequestBody WalletDTO walletDTO) {
        WalletDTO walletActualizado = walletService.actualizar(id, walletDTO);
        return ResponseEntity.ok(walletActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        walletService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<WalletDTO> cambiarEstado(@PathVariable Long id, @RequestParam Boolean estado) {
        WalletDTO wallet = walletService.cambiarEstado(id, estado);
        return ResponseEntity.ok(wallet);
    }

    @PatchMapping("/{id}/saldo")
    public ResponseEntity<WalletDTO> actualizarSaldo(@PathVariable Long id, @RequestParam BigDecimal nuevoSaldo) {
        WalletDTO wallet = walletService.actualizarSaldo(id, nuevoSaldo);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<WalletDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<WalletDTO> wallets = walletService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(wallets);
    }

    @GetMapping("/usuario/{usuarioId}/cripto/{criptoId}")
    public ResponseEntity<WalletDTO> obtenerPorUsuarioYCripto(@PathVariable Long usuarioId, @PathVariable Long criptoId) {
        WalletDTO wallet = walletService.obtenerPorUsuarioYCripto(usuarioId, criptoId);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/usuario/{usuarioId}/saldo-total")
    public ResponseEntity<BigDecimal> obtenerSaldoTotalUsuario(@PathVariable Long usuarioId) {
        BigDecimal saldoTotal = walletService.obtenerSaldoTotalUsuario(usuarioId);
        return ResponseEntity.ok(saldoTotal);
    }

    @GetMapping("/saldo-mayor")
    public ResponseEntity<List<WalletDTO>> obtenerWalletsConSaldoMayorA(@RequestParam BigDecimal saldoMinimo) {
        List<WalletDTO> wallets = walletService.obtenerWalletsConSaldoMayorA(saldoMinimo);
        return ResponseEntity.ok(wallets);
    }
}