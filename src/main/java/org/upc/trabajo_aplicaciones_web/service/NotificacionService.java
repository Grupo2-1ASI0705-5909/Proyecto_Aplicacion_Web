package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.NotificacionDTO;
import org.upc.trabajo_aplicaciones_web.model.Notificacion;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.repository.NotificacionRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public NotificacionDTO crear(NotificacionDTO notificacionDTO) {
        Usuario usuario = usuarioRepository.findById(notificacionDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Notificacion notificacion = modelMapper.map(notificacionDTO, Notificacion.class);
        notificacion.setUsuario(usuario);
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setLeido(false);

        notificacion = notificacionRepository.save(notificacion);
        return modelMapper.map(notificacion, NotificacionDTO.class);
    }

    public List<NotificacionDTO> obtenerTodos() {
        return notificacionRepository.findAll()
                .stream()
                .map(notificacion -> modelMapper.map(notificacion, NotificacionDTO.class))
                .collect(Collectors.toList());
    }

    public NotificacionDTO obtenerPorId(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        return modelMapper.map(notificacion, NotificacionDTO.class);
    }

    public NotificacionDTO actualizar(Long id, NotificacionDTO notificacionDTO) {
        Notificacion notificacionExistente = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notificacionExistente.setTitulo(notificacionDTO.getTitulo());
        notificacionExistente.setMensaje(notificacionDTO.getMensaje());
        notificacionExistente.setLeido(notificacionDTO.getLeido());

        notificacionExistente = notificacionRepository.save(notificacionExistente);
        return modelMapper.map(notificacionExistente, NotificacionDTO.class);
    }

    public void eliminar(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada");
        }
        notificacionRepository.deleteById(id);
    }

    public NotificacionDTO marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notificacion.marcarComoLeido();
        notificacion = notificacionRepository.save(notificacion);

        return modelMapper.map(notificacion, NotificacionDTO.class);
    }

    public List<NotificacionDTO> obtenerPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioUsuarioId(usuarioId)
                .stream()
                .map(notificacion -> modelMapper.map(notificacion, NotificacionDTO.class))
                .collect(Collectors.toList());
    }

    public List<NotificacionDTO> obtenerNoLeidasPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioUsuarioIdAndLeidoFalse(usuarioId)
                .stream()
                .map(notificacion -> modelMapper.map(notificacion, NotificacionDTO.class))
                .collect(Collectors.toList());
    }

    public List<NotificacionDTO> obtenerRecientes() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(7);
        return notificacionRepository.findNotificacionesRecientes(fechaLimite)
                .stream()
                .map(notificacion -> modelMapper.map(notificacion, NotificacionDTO.class))
                .collect(Collectors.toList());
    }

    public void marcarTodasComoLeidas(Long usuarioId) {
        notificacionRepository.marcarComoLeidas(usuarioId);
    }

    public long contarNoLeidasPorUsuario(Long usuarioId) {
        return notificacionRepository.countByUsuarioUsuarioIdAndLeidoFalse(usuarioId);
    }

    public List<NotificacionDTO> obtenerPorTipo(String tipo) {
        return notificacionRepository.findByTipo(tipo)
                .stream()
                .map(notificacion -> modelMapper.map(notificacion, NotificacionDTO.class))
                .collect(Collectors.toList());
    }
}