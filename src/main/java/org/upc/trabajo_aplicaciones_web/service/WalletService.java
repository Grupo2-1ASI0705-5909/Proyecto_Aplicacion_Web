package org.upc.trabajo_aplicaciones_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.WalletDTO;
import org.upc.trabajo_aplicaciones_web.model.Criptomoneda;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.model.Wallet;
import org.upc.trabajo_aplicaciones_web.repository.CriptomonedaRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;
import org.upc.trabajo_aplicaciones_web.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {


}