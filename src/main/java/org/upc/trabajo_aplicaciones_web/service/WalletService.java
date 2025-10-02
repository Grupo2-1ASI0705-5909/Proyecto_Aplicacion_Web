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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UsuarioRepository usuarioRepository;
    private final CriptomonedaRepository criptomonedaRepository;

    public WalletDTO crear(WalletDTO walletDTO) {
        Usuario usuario = usuarioRepository.findById(walletDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Criptomoneda criptomoneda = criptomonedaRepository.findById(walletDTO.getCriptoId())
                .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));

        if (walletRepository.findByUsuarioUsuarioIdAndCriptomonedaCriptoId(
                walletDTO.getUsuarioId(), walletDTO.getCriptoId()).isPresent()) {
            throw new RuntimeException("El usuario ya tiene un wallet para esta criptomoneda");
        }

        Wallet wallet = new Wallet();
        wallet.setUsuario(usuario);
        wallet.setCriptomoneda(criptomoneda);
        wallet.setDireccion(walletDTO.getDireccion());
        wallet.setSaldo(walletDTO.getSaldo() != null ? walletDTO.getSaldo() : BigDecimal.ZERO);
        wallet.setEstado(walletDTO.getEstado() != null ? walletDTO.getEstado() : true);

        wallet = walletRepository.save(wallet);
        return convertirAWalletDTO(wallet);
    }

    public List<WalletDTO> obtenerTodos() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletDTO> walletDTOs = new ArrayList<>();
        for (Wallet wallet : wallets) {
            walletDTOs.add(convertirAWalletDTO(wallet));
        }
        return walletDTOs;
    }

    public WalletDTO obtenerPorId(Long id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        return convertirAWalletDTO(wallet);
    }

    public WalletDTO actualizar(Long id, WalletDTO walletDTO) {
        Wallet walletExistente = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));

        walletExistente.setDireccion(walletDTO.getDireccion());
        walletExistente.setSaldo(walletDTO.getSaldo());
        walletExistente.setEstado(walletDTO.getEstado());

        walletExistente = walletRepository.save(walletExistente);
        return convertirAWalletDTO(walletExistente);
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
        return convertirAWalletDTO(wallet);
    }

    public List<WalletDTO> obtenerPorUsuario(Long usuarioId) {
        List<Wallet> wallets = walletRepository.findByUsuarioUsuarioId(usuarioId);
        List<WalletDTO> walletDTOs = new ArrayList<>();
        for (Wallet wallet : wallets) {
            walletDTOs.add(convertirAWalletDTO(wallet));
        }
        return walletDTOs;
    }

    public List<WalletDTO> obtenerPorCriptomoneda(Long criptoId) {
        List<Wallet> wallets = walletRepository.findByCriptomonedaCriptoId(criptoId);
        List<WalletDTO> walletDTOs = new ArrayList<>();
        for (Wallet wallet : wallets) {
            walletDTOs.add(convertirAWalletDTO(wallet));
        }
        return walletDTOs;
    }

    public WalletDTO obtenerPorUsuarioYCripto(Long usuarioId, Long criptoId) {
        Wallet wallet = walletRepository.findByUsuarioUsuarioIdAndCriptomonedaCriptoId(usuarioId, criptoId)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        return convertirAWalletDTO(wallet);
    }

    public WalletDTO actualizarSaldo(Long id, BigDecimal nuevoSaldo) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrado"));
        wallet.setSaldo(nuevoSaldo);
        wallet = walletRepository.save(wallet);
        return convertirAWalletDTO(wallet);
    }

    public BigDecimal obtenerSaldoTotalUsuario(Long usuarioId) {
        BigDecimal saldoTotal = walletRepository.calcularSaldoTotalPorUsuario(usuarioId);
        return saldoTotal != null ? saldoTotal : BigDecimal.ZERO;
    }

    public List<WalletDTO> obtenerWalletsConSaldoMayorA(BigDecimal saldoMinimo) {
        List<Wallet> wallets = walletRepository.findWalletsConSaldoMayorA(saldoMinimo);
        List<WalletDTO> walletDTOs = new ArrayList<>();
        for (Wallet wallet : wallets) {
            walletDTOs.add(convertirAWalletDTO(wallet));
        }
        return walletDTOs;
    }

    private WalletDTO convertirAWalletDTO(Wallet wallet) {
        WalletDTO dto = new WalletDTO();
        dto.setWalletId(wallet.getWalletId());
        dto.setUsuarioId(wallet.getUsuario().getUsuarioId());
        dto.setCriptoId(wallet.getCriptomoneda().getCriptoId());
        dto.setDireccion(wallet.getDireccion());
        dto.setSaldo(wallet.getSaldo());
        dto.setEstado(wallet.getEstado());
        dto.setUltimaActualizacion(wallet.getUltimaActualizacion());
        return dto;
    }
}