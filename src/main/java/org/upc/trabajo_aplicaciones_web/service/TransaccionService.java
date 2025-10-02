package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.TransaccionDTO;
import org.upc.trabajo_aplicaciones_web.model.*;
import org.upc.trabajo_aplicaciones_web.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComercioRepository comercioRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final CriptomonedaRepository criptomonedaRepository;
    private final TipoCambioRepository tipoCambioRepository;
    private final ModelMapper modelMapper;

    public TransaccionDTO crear(TransaccionDTO transaccionDTO) {
        Usuario usuario = usuarioRepository.findById(transaccionDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comercio comercio = comercioRepository.findById(transaccionDTO.getComercioId())
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        MetodoPago metodoPago = metodoPagoRepository.findById(transaccionDTO.getMetodoPagoId())
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        Transaccion transaccion = modelMapper.map(transaccionDTO, Transaccion.class);
        transaccion.setUsuario(usuario);
        transaccion.setComercio(comercio);
        transaccion.setMetodoPago(metodoPago);

        // Asignar criptomoneda si se proporciona
        if (transaccionDTO.getCriptoId() != null) {
            Criptomoneda criptomoneda = criptomonedaRepository.findById(transaccionDTO.getCriptoId())
                    .orElseThrow(() -> new RuntimeException("Criptomoneda no encontrada"));
            transaccion.setCriptomoneda(criptomoneda);
        }

        // Asignar tipo de cambio si se proporciona
        if (transaccionDTO.getTipoCambioId() != null) {
            TipoCambio tipoCambio = tipoCambioRepository.findById(transaccionDTO.getTipoCambioId())
                    .orElseThrow(() -> new RuntimeException("Tipo de cambio no encontrado"));
            transaccion.setTipoCambio(tipoCambio);
        }

        transaccion.setFechaTransaccion(LocalDateTime.now());

        transaccion = transaccionRepository.save(transaccion);
        return modelMapper.map(transaccion, TransaccionDTO.class);
    }

    public List<TransaccionDTO> obtenerTodos() {
        return transaccionRepository.findAll()
                .stream()
                .map(transaccion -> modelMapper.map(transaccion, TransaccionDTO.class))
                .collect(Collectors.toList());
    }

    public TransaccionDTO obtenerPorId(Long id) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
        return modelMapper.map(transaccion, TransaccionDTO.class);
    }

    public TransaccionDTO actualizar(Long id, TransaccionDTO transaccionDTO) {
        Transaccion transaccionExistente = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        transaccionExistente.setMontoTotalFiat(transaccionDTO.getMontoTotalFiat());
        transaccionExistente.setMontoTotalCripto(transaccionDTO.getMontoTotalCripto());
        transaccionExistente.setEstado(transaccionDTO.getEstado());
        transaccionExistente.setTxHash(transaccionDTO.getTxHash());

        transaccionExistente = transaccionRepository.save(transaccionExistente);
        return modelMapper.map(transaccionExistente, TransaccionDTO.class);
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
        return modelMapper.map(transaccion, TransaccionDTO.class);
    }

    public List<TransaccionDTO> obtenerPorUsuario(Long usuarioId) {
        return transaccionRepository.findByUsuarioUsuarioId(usuarioId)
                .stream()
                .map(transaccion -> modelMapper.map(transaccion, TransaccionDTO.class))
                .collect(Collectors.toList());
    }

    public List<TransaccionDTO> obtenerPorComercio(Long comercioId) {
        return transaccionRepository.findByComercioComercioId(comercioId)
                .stream()
                .map(transaccion -> modelMapper.map(transaccion, TransaccionDTO.class))
                .collect(Collectors.toList());
    }

    public List<TransaccionDTO> obtenerPorEstado(String estado) {
        return transaccionRepository.findByEstado(estado)
                .stream()
                .map(transaccion -> modelMapper.map(transaccion, TransaccionDTO.class))
                .collect(Collectors.toList());
    }

    public List<TransaccionDTO> obtenerTransaccionesConCripto() {
        return transaccionRepository.findTransaccionesConCripto()
                .stream()
                .map(transaccion -> modelMapper.map(transaccion, TransaccionDTO.class))
                .collect(Collectors.toList());
    }

    public Double calcularTotalFiatPorUsuario(Long usuarioId) {
        return transaccionRepository.calcularTotalFiatPorUsuario(usuarioId);
    }

    public Double calcularTotalCriptoPorUsuario(Long usuarioId) {
        return transaccionRepository.calcularTotalCriptoPorUsuario(usuarioId);
    }

    public List<TransaccionDTO> obtenerRecientes() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(30);
        return transaccionRepository.findTransaccionesRecientes(fechaLimite)
                .stream()
                .map(transaccion -> modelMapper.map(transaccion, TransaccionDTO.class))
                .collect(Collectors.toList());
    }
}