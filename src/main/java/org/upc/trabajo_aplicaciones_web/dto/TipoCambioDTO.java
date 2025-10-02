package org.upc.trabajo_aplicaciones_web.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TipoCambioDTO {
    private Long tipoCambioId;
    private String desdeCodigo;
    private String hastaCodigo;
    private BigDecimal tasa;
    private LocalDateTime fechaHora;
    private String fuente;

    public TipoCambioDTO() {}

    public TipoCambioDTO(String desdeCodigo, String hastaCodigo, BigDecimal tasa) {
        this.desdeCodigo = desdeCodigo;
        this.hastaCodigo = hastaCodigo;
        this.tasa = tasa;
        this.fechaHora = LocalDateTime.now();
    }
}