package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.PlanPagoDTO;
import org.upc.trabajo_aplicaciones_web.model.PlanPago;
import org.upc.trabajo_aplicaciones_web.model.Transaccion;
import org.upc.trabajo_aplicaciones_web.repository.PlanPagoRepository;
import org.upc.trabajo_aplicaciones_web.repository.TransaccionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanPagoService {

}