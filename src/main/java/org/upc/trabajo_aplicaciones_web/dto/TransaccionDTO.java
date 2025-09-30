package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransaccionDTO {
    private Long id;
    private Long usuarioId;
    private Long comercioId;
    private Long metodoPagoId;
    private BigDecimal montoTotal;
    private LocalDateTime fechaTransaccion;
    private String estado = "PENDIENTE";

    // Para respuestas
    private UsuarioDTO usuario;
    private ComercioDTO comercio;
    private MetodoPagoDTO metodoPago;
    private PlanPagoDTO planPago;

    public TransaccionDTO() {}

    public TransaccionDTO(BigDecimal montoTotal, String estado) {
        this.montoTotal = montoTotal;
        this.estado = estado;
        this.fechaTransaccion = LocalDateTime.now();
    }
}