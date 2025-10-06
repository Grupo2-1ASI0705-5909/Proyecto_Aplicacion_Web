package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.ComercioDTO;
import org.upc.trabajo_aplicaciones_web.model.Comercio;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.repository.ComercioRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComercioService {
    private final ComercioRepository comercioRepository;
    private final UsuarioRepository usuarioRepository;


}