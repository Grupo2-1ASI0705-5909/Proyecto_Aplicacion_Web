package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WalletDTO {
    private Long walletId;
    private Long usuarioId;
    private Long criptoId;
    private String direccion;
    private BigDecimal saldo = BigDecimal.ZERO;
    private Boolean estado = true;
    private LocalDateTime ultimaActualizacion;

    // Para respuestas
    private UsuarioDTO usuario;
    private CriptomonedaDTO criptomoneda;

    public WalletDTO() {}

    public WalletDTO(String direccion, BigDecimal saldo) {
        this.direccion = direccion;
        this.saldo = saldo;
    }
}