package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.CuotaDTO;
import org.upc.trabajo_aplicaciones_web.model.Cuota;
import org.upc.trabajo_aplicaciones_web.model.PlanPago;
import org.upc.trabajo_aplicaciones_web.repository.CuotaRepository;
import org.upc.trabajo_aplicaciones_web.repository.PlanPagoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuotaService {
    private final CuotaRepository cuotaRepository;
    private final PlanPagoRepository planPagoRepository;


}