package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.upc.trabajo_aplicaciones_web.securities.JwtRequest;
import org.upc.trabajo_aplicaciones_web.securities.JwtResponse;
import org.upc.trabajo_aplicaciones_web.securities.JwtTokenUtil;
import org.upc.trabajo_aplicaciones_web.service.JwtUserDetailsService;

@RestController
    @CrossOrigin
    public class JwtAuthenticationController {


    }
