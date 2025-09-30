package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.MetodoPagoDTO;
import org.upc.trabajo_aplicaciones_web.model.MetodoPago;
import org.upc.trabajo_aplicaciones_web.repository.MetodoPagoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;
    private final ModelMapper modelMapper;

    public MetodoPagoDTO crear(MetodoPagoDTO metodoPagoDTO) {
        if (metodoPagoRepository.existsByNombre(metodoPagoDTO.getNombre())) {
            throw new RuntimeException("El método de pago ya existe");
        }

        MetodoPago metodoPago = modelMapper.map(metodoPagoDTO, MetodoPago.class);
        metodoPago = metodoPagoRepository.save(metodoPago);
        return modelMapper.map(metodoPago, MetodoPagoDTO.class);
    }

    public List<MetodoPagoDTO> obtenerTodos() {
        return metodoPagoRepository.findAll()
                .stream()
                .map(metodo -> modelMapper.map(metodo, MetodoPagoDTO.class))
                .collect(Collectors.toList());
    }

    public MetodoPagoDTO obtenerPorId(Long id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        return modelMapper.map(metodoPago, MetodoPagoDTO.class);
    }

    public MetodoPagoDTO actualizar(Long id, MetodoPagoDTO metodoPagoDTO) {
        MetodoPago metodoExistente = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        metodoExistente.setNombre(metodoPagoDTO.getNombre());
        metodoExistente.setDescripcion(metodoPagoDTO.getDescripcion());
        metodoExistente.setEstado(metodoPagoDTO.getEstado());

        metodoExistente = metodoPagoRepository.save(metodoExistente);
        return modelMapper.map(metodoExistente, MetodoPagoDTO.class);
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
        return modelMapper.map(metodoPago, MetodoPagoDTO.class);
    }

    public List<MetodoPagoDTO> obtenerActivos() {
        return metodoPagoRepository.findByEstadoTrue()
                .stream()
                .map(metodo -> modelMapper.map(metodo, MetodoPagoDTO.class))
                .collect(Collectors.toList());
    }

    public long contarMetodosActivos() {
        return metodoPagoRepository.countByEstadoTrue();
    }
}