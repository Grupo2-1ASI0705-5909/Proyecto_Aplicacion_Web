package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransaccionDTO {
    private Long transaccionId;
    private Long usuarioId;
    private Long comercioId;
    private Long metodoPagoId;
    private Long criptoId;
    private Long tipoCambioId;
    private String codigoMoneda;
    private BigDecimal montoTotalFiat;
    private BigDecimal montoTotalCripto;
    private BigDecimal tasaAplicada;
    private String txHash;
    private LocalDateTime fechaTransaccion;
    private String estado = "PENDIENTE";

    // Para respuestas
    private UsuarioDTO usuario;
    private ComercioDTO comercio;
    private MetodoPagoDTO metodoPago;
    private CriptomonedaDTO criptomoneda;
    private TipoCambioDTO tipoCambio;
    private PlanPagoDTO planPago;

    public TransaccionDTO() {}

    public TransaccionDTO(BigDecimal montoTotalFiat, String estado) {
        this.montoTotalFiat = montoTotalFiat;
        this.estado = estado;
        this.fechaTransaccion = LocalDateTime.now();
    }
}