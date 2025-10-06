package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.MetodoPagoDTO;
import org.upc.trabajo_aplicaciones_web.model.MetodoPago;
import org.upc.trabajo_aplicaciones_web.repository.MetodoPagoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetodoPagoService {
    private final MetodoPagoRepository metodoPagoRepository;

    //David MetodoPagoService

    public MetodoPagoDTO crear(MetodoPagoDTO metodoPagoDTO) {
        if (metodoPagoRepository.existsByNombre(metodoPagoDTO.getNombre())) {
            throw new RuntimeException("El método de pago ya existe");
        }

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setNombre(metodoPagoDTO.getNombre());
        metodoPago.setDescripcion(metodoPagoDTO.getDescripcion());
        metodoPago.setEstado(metodoPagoDTO.getEstado() != null ? metodoPagoDTO.getEstado() : true);

        metodoPago = metodoPagoRepository.save(metodoPago);
        return convertirAMetodoPagoDTO(metodoPago);
    }

    public List<MetodoPagoDTO> obtenerTodos() {
        List<MetodoPago> metodosPago = metodoPagoRepository.findAll();
        List<MetodoPagoDTO> metodoPagoDTOs = new ArrayList<>();
        for (MetodoPago metodo : metodosPago) {
            metodoPagoDTOs.add(convertirAMetodoPagoDTO(metodo));
        }
        return metodoPagoDTOs;
    }

    public MetodoPagoDTO obtenerPorId(Long id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        return convertirAMetodoPagoDTO(metodoPago);
    }

    public MetodoPagoDTO actualizar(Long id, MetodoPagoDTO metodoPagoDTO) {
        MetodoPago metodoExistente = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        metodoExistente.setNombre(metodoPagoDTO.getNombre());
        metodoExistente.setDescripcion(metodoPagoDTO.getDescripcion());
        metodoExistente.setEstado(metodoPagoDTO.getEstado());

        metodoExistente = metodoPagoRepository.save(metodoExistente);
        return convertirAMetodoPagoDTO(metodoExistente);
    }

    public void eliminar(Long id) {
        if (!metodoPagoRepository.existsById(id)) {
            throw new RuntimeException("Método de pago no encontrado");
        }
        metodoPagoRepository.deleteById(id);
    }

    public MetodoPagoDTO cambiarEstado(Long id, Boolean estado) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        metodoPago.setEstado(estado);
        metodoPago = metodoPagoRepository.save(metodoPago);
        return convertirAMetodoPagoDTO(metodoPago);
    }

    public List<MetodoPagoDTO> obtenerActivos() {
        List<MetodoPago> metodosPago = metodoPagoRepository.findByEstadoTrue();
        List<MetodoPagoDTO> metodoPagoDTOs = new ArrayList<>();
        for (MetodoPago metodo : metodosPago) {
            metodoPagoDTOs.add(convertirAMetodoPagoDTO(metodo));
        }
        return metodoPagoDTOs;
    }

    public long contarMetodosActivos() {
        return metodoPagoRepository.countByEstadoTrue();
    }

    private MetodoPagoDTO convertirAMetodoPagoDTO(MetodoPago metodoPago) {
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setMetodoPagoId(metodoPago.getMetodoPagoId());
        dto.setNombre(metodoPago.getNombre());
        dto.setDescripcion(metodoPago.getDescripcion());
        dto.setEstado(metodoPago.getEstado());
        return dto;
    }
}