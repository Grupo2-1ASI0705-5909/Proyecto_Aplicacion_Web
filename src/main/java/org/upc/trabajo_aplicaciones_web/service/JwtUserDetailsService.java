package org.upc.trabajo_aplicaciones_web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.upc.trabajo_aplicaciones_web.model.Rol;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public JwtUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + email));

        Set<GrantedAuthority> authorities = new HashSet<>();
        Rol rol = usuario.getRol();
        if (rol != null && rol.getNombre() != null && !rol.getNombre().isBlank()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre().trim().toUpperCase()));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_Usuario"));
        }

        // Si manejas cuenta deshabilitada/bloqueada/expirada, pásalos aquí:
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(
                usuario.getEmail(),
                usuario.getPasswordHash(),     // Debe estar BCRYPT en BD
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities
        );
    }
}
