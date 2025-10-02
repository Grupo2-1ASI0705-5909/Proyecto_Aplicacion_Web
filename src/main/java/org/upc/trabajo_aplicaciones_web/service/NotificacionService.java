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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    public NotificacionDTO crear(NotificacionDTO notificacionDTO) {
        Usuario usuario = usuarioRepository.findById(notificacionDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(usuario);
        notificacion.setTitulo(notificacionDTO.getTitulo());
        notificacion.setMensaje(notificacionDTO.getMensaje());
        notificacion.setLeido(notificacionDTO.getLeido() != null ? notificacionDTO.getLeido() : false);

        notificacion = notificacionRepository.save(notificacion);
        return convertirANotificacionDTO(notificacion);
    }

    public List<NotificacionDTO> obtenerTodos() {
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        List<NotificacionDTO> notificacionDTOs = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notificacionDTOs.add(convertirANotificacionDTO(notificacion));
        }
        return notificacionDTOs;
    }

    public NotificacionDTO obtenerPorId(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        return convertirANotificacionDTO(notificacion);
    }

    public NotificacionDTO actualizar(Long id, NotificacionDTO notificacionDTO) {
        Notificacion notificacionExistente = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notificacionExistente.setTitulo(notificacionDTO.getTitulo());
        notificacionExistente.setMensaje(notificacionDTO.getMensaje());
        notificacionExistente.setLeido(notificacionDTO.getLeido());

        notificacionExistente = notificacionRepository.save(notificacionExistente);
        return convertirANotificacionDTO(notificacionExistente);
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
        return convertirANotificacionDTO(notificacion);
    }

    public List<NotificacionDTO> obtenerPorUsuario(Long usuarioId) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioUsuarioId(usuarioId);
        List<NotificacionDTO> notificacionDTOs = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notificacionDTOs.add(convertirANotificacionDTO(notificacion));
        }
        return notificacionDTOs;
    }

    public List<NotificacionDTO> obtenerNoLeidasPorUsuario(Long usuarioId) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioUsuarioIdAndLeidoFalse(usuarioId);
        List<NotificacionDTO> notificacionDTOs = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notificacionDTOs.add(convertirANotificacionDTO(notificacion));
        }
        return notificacionDTOs;
    }

    public List<NotificacionDTO> obtenerRecientes() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(7);
        List<Notificacion> notificaciones = notificacionRepository.findNotificacionesRecientes(fechaLimite);
        List<NotificacionDTO> notificacionDTOs = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notificacionDTOs.add(convertirANotificacionDTO(notificacion));
        }
        return notificacionDTOs;
    }

    public void marcarTodasComoLeidas(Long usuarioId) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioUsuarioIdAndLeidoFalse(usuarioId);
        for (Notificacion notificacion : notificaciones) {
            notificacion.marcarComoLeido();
            notificacionRepository.save(notificacion);
        }
    }

    public long contarNoLeidasPorUsuario(Long usuarioId) {
        return notificacionRepository.countByUsuarioUsuarioIdAndLeidoFalse(usuarioId);
    }

    public List<NotificacionDTO> obtenerPorTipo(String tipo) {
        List<Notificacion> notificaciones = notificacionRepository.findByTipo(tipo);
        List<NotificacionDTO> notificacionDTOs = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notificacionDTOs.add(convertirANotificacionDTO(notificacion));
        }
        return notificacionDTOs;
    }

    private NotificacionDTO convertirANotificacionDTO(Notificacion notificacion) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setNotificacionId(notificacion.getNotificacionId());
        dto.setUsuarioId(notificacion.getUsuario().getUsuarioId());
        dto.setTitulo(notificacion.getTitulo());
        dto.setMensaje(notificacion.getMensaje());
        dto.setFechaEnvio(notificacion.getFechaEnvio());
        dto.setLeido(notificacion.getLeido());
        return dto;
    }
}