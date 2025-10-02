package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.WalletDTO;
import org.upc.trabajo_aplicaciones_web.model.Criptomoneda;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.model.Wallet;
import org.upc.trabajo_aplicaciones_web.repository.CriptomonedaRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;
import org.upc.trabajo_aplicaciones_web.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UsuarioRepository usuarioRepository;
    private final CriptomonedaRepository criptomonedaRepository;
    private final ModelMapper modelMapper;

    public WalletDTO crear(WalletDTO walletDTO) {
        Usuario usuario = usuarioRepository.findById(walletDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Criptomoneda criptomoneda = criptomonedaRepository.findById(walletDTO.getCriptoId())
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));

        // Verificar si ya existe un wallet para este usuario y criptomoneda
        if (walletRepository.findByUsuarioUsuarioIdAndCriptomonedaCriptoId(
                walletDTO.getUsuarioId(), walletDTO.getCriptoId()).isPresent()) {
            throw new RuntimeException("El usuario ya tiene un wallet para esta criptomoneda");
        }

        Wallet wallet = modelMapper.map(walletDTO, Wallet.class);
        wallet.setUsuario(usuario);
        wallet.setCriptomoneda(criptomoneda);

        wallet = walletRepository.save(wallet);
        return modelMapper.map(wallet, WalletDTO.class);
    }

    public List<WalletDTO> obtenerTodos() {
        return walletRepository.findAll()
                .stream()
                .map(wallet -> modelMapper.map(wallet, WalletDTO.class))
                .collect(Collectors.toList());
    }

    public WalletDTO obtenerPorId(Long id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        return modelMapper.map(wallet, WalletDTO.class);
    }

    public WalletDTO actualizar(Long id, WalletDTO walletDTO) {
        Wallet walletExistente = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));

        walletExistente.setDireccion(walletDTO.getDireccion());
        walletExistente.setSaldo(walletDTO.getSaldo());
        walletExistente.setEstado(walletDTO.getEstado());

        walletExistente = walletRepository.save(walletExistente);
        return modelMapper.map(walletExistente, WalletDTO.class);
    }

    public void eliminar(Long id) {
        if (!walletRepository.existsById(id)) {
            throw new RuntimeException("Wallet no encontrado");
        }
        walletRepository.deleteById(id);
    }

    public WalletDTO cambiarEstado(Long id, Boolean estado) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        wallet.setEstado(estado);
        wallet = walletRepository.save(wallet);
        return modelMapper.map(wallet, WalletDTO.class);
    }

    public List<WalletDTO> obtenerPorUsuario(Long usuarioId) {
        return walletRepository.findByUsuarioUsuarioId(usuarioId)
                .stream()
                .map(wallet -> modelMapper.map(wallet, WalletDTO.class))
                .collect(Collectors.toList());
    }

    public List<WalletDTO> obtenerPorCriptomoneda(Long criptoId) {
        return walletRepository.findByCriptomonedaCriptoId(criptoId)
                .stream()
                .map(wallet -> modelMapper.map(wallet, WalletDTO.class))
                .collect(Collectors.toList());
    }

    public WalletDTO obtenerPorUsuarioYCripto(Long usuarioId, Long criptoId) {
        Wallet wallet = walletRepository.findByUsuarioUsuarioIdAndCriptomonedaCriptoId(usuarioId, criptoId)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        return modelMapper.map(wallet, WalletDTO.class);
    }

    public WalletDTO actualizarSaldo(Long id, BigDecimal nuevoSaldo) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));

        wallet.setSaldo(nuevoSaldo);
        wallet = walletRepository.save(wallet);

        return modelMapper.map(wallet, WalletDTO.class);
    }

    public BigDecimal obtenerSaldoTotalUsuario(Long usuarioId) {
        BigDecimal saldoTotal = walletRepository.calcularSaldoTotalPorUsuario(usuarioId);
        return saldoTotal != null ? saldoTotal : BigDecimal.ZERO;
    }

    public List<WalletDTO> obtenerWalletsConSaldoMayorA(BigDecimal saldoMinimo) {
        return walletRepository.findWalletsConSaldoMayorA(saldoMinimo)
                .stream()
                .map(wallet -> modelMapper.map(wallet, WalletDTO.class))
                .collect(Collectors.toList());
    }
}