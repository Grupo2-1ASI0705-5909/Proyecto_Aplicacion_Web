package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.TransaccionDTO;
import org.upc.trabajo_aplicaciones_web.model.*;
import org.upc.trabajo_aplicaciones_web.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//actualizacion
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComercioRepository comercioRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final CriptomonedaRepository criptomonedaRepository;
    private final TipoCambioRepository tipoCambioRepository;

    public TransaccionDTO crear(TransaccionDTO transaccionDTO) {
        Usuario usuario = usuarioRepository.findById(transaccionDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comercio comercio = comercioRepository.findById(transaccionDTO.getComercioId())
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        MetodoPago metodoPago = metodoPagoRepository.findById(transaccionDTO.getMetodoPagoId())
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        Transaccion transaccion = new Transaccion();
        transaccion.setUsuario(usuario);
        transaccion.setComercio(comercio);
        transaccion.setMetodoPago(metodoPago);
        transaccion.setMontoTotalFiat(transaccionDTO.getMontoTotalFiat());
        transaccion.setMontoTotalCripto(transaccionDTO.getMontoTotalCripto());
        transaccion.setTasaAplicada(transaccionDTO.getTasaAplicada());
        transaccion.setTxHash(transaccionDTO.getTxHash());
        transaccion.setEstado(transaccionDTO.getEstado() != null ? transaccionDTO.getEstado() : "PENDIENTE");

        if (transaccionDTO.getCriptoId() != null) {
            Criptomoneda criptomoneda = criptomonedaRepository.findById(transaccionDTO.getCriptoId())
                    .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));
            transaccion.setCriptomoneda(criptomoneda);
        }

        if (transaccionDTO.getTipoCambioId() != null) {
            TipoCambio tipoCambio = tipoCambioRepository.findById(transaccionDTO.getTipoCambioId())
                    .orElseThrow(() -> new RuntimeException("Tipo de cambio no encontrado"));
            transaccion.setTipoCambio(tipoCambio);
        }

        transaccion = transaccionRepository.save(transaccion);
        return convertirATransaccionDTO(transaccion);
    }

    public List<TransaccionDTO> obtenerTodos() {
        List<Transaccion> transacciones = transaccionRepository.findAll();
        List<TransaccionDTO> transaccionDTOs = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            transaccionDTOs.add(convertirATransaccionDTO(transaccion));
        }
        return transaccionDTOs;
    }

    public TransaccionDTO obtenerPorId(Long id) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
        return convertirATransaccionDTO(transaccion);
    }

    public TransaccionDTO actualizar(Long id, TransaccionDTO transaccionDTO) {
        Transaccion transaccionExistente = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        transaccionExistente.setMontoTotalFiat(transaccionDTO.getMontoTotalFiat());
        transaccionExistente.setMontoTotalCripto(transaccionDTO.getMontoTotalCripto());
        transaccionExistente.setEstado(transaccionDTO.getEstado());
        transaccionExistente.setTxHash(transaccionDTO.getTxHash());

        transaccionExistente = transaccionRepository.save(transaccionExistente);
        return convertirATransaccionDTO(transaccionExistente);
    }

    public void eliminar(Long id) {
        if (!transaccionRepository.existsById(id)) {
            throw new RuntimeException("Transacción no encontrada");
        }
        transaccionRepository.deleteById(id);
    }

    public TransaccionDTO cambiarEstado(Long id, String estado) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
        transaccion.setEstado(estado);
        transaccion = transaccionRepository.save(transaccion);
        return convertirATransaccionDTO(transaccion);
    }

    public List<TransaccionDTO> obtenerPorUsuario(Long usuarioId) {
        List<Transaccion> transacciones = transaccionRepository.findByUsuarioUsuarioId(usuarioId);
        List<TransaccionDTO> transaccionDTOs = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            transaccionDTOs.add(convertirATransaccionDTO(transaccion));
        }
        return transaccionDTOs;
    }

    public List<TransaccionDTO> obtenerPorComercio(Long comercioId) {
        List<Transaccion> transacciones = transaccionRepository.findByComercioComercioId(comercioId);
        List<TransaccionDTO> transaccionDTOs = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            transaccionDTOs.add(convertirATransaccionDTO(transaccion));
        }
        return transaccionDTOs;
    }

    public List<TransaccionDTO> obtenerPorEstado(String estado) {
        List<Transaccion> transacciones = transaccionRepository.findByEstado(estado);
        List<TransaccionDTO> transaccionDTOs = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            transaccionDTOs.add(convertirATransaccionDTO(transaccion));
        }
        return transaccionDTOs;
    }

    public List<TransaccionDTO> obtenerTransaccionesConCripto() {
        List<Transaccion> transacciones = transaccionRepository.findTransaccionesConCripto();
        List<TransaccionDTO> transaccionDTOs = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            transaccionDTOs.add(convertirATransaccionDTO(transaccion));
        }
        return transaccionDTOs;
    }

    public Double calcularTotalFiatPorUsuario(Long usuarioId) {
        return transaccionRepository.calcularTotalFiatPorUsuario(usuarioId);
    }

    public Double calcularTotalCriptoPorUsuario(Long usuarioId) {
        return transaccionRepository.calcularTotalCriptoPorUsuario(usuarioId);
    }

    public List<TransaccionDTO> obtenerRecientes() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(30);
        List<Transaccion> transacciones = transaccionRepository.findTransaccionesRecientes(fechaLimite);
        List<TransaccionDTO> transaccionDTOs = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            transaccionDTOs.add(convertirATransaccionDTO(transaccion));
        }
        return transaccionDTOs;
    }

    private TransaccionDTO convertirATransaccionDTO(Transaccion transaccion) {
        TransaccionDTO dto = new TransaccionDTO();
        dto.setTransaccionId(transaccion.getTransaccionId());
        dto.setUsuarioId(transaccion.getUsuario().getUsuarioId());
        dto.setComercioId(transaccion.getComercio().getComercioId());
        dto.setMetodoPagoId(transaccion.getMetodoPago().getMetodoPagoId());
        dto.setMontoTotalFiat(transaccion.getMontoTotalFiat());
        dto.setMontoTotalCripto(transaccion.getMontoTotalCripto());
        dto.setTasaAplicada(transaccion.getTasaAplicada());
        dto.setTxHash(transaccion.getTxHash());
        dto.setEstado(transaccion.getEstado());
        dto.setFechaTransaccion(transaccion.getFechaTransaccion());

        if (transaccion.getCriptomoneda() != null) {
            dto.setCriptoId(transaccion.getCriptomoneda().getCriptoId());
        }
        if (transaccion.getTipoCambio() != null) {
            dto.setTipoCambioId(transaccion.getTipoCambio().getTipoCambioId());
        }

        return dto;
    }
}