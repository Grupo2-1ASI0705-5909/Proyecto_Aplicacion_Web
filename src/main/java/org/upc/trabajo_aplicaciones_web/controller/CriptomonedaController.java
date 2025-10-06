package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.CriptomonedaDTO;
import org.upc.trabajo_aplicaciones_web.service.CriptomonedaService;

import java.util.List;

@RestController
@RequestMapping("/api/criptomonedas")
@RequiredArgsConstructor
public class CriptomonedaController {

    private final CriptomonedaService criptomonedaService;



}
