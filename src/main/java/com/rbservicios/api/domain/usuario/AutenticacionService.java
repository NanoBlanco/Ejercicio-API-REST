package com.rbservicios.api.domain.usuario;

import com.rbservicios.api.infraestructure.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    PasswordEncoder passEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return usuarioRepository.findByCorreo(username);
    }

    public AuthResponse loginUser(DatosAutenticacion datos) {
        var username = datos.correo();
        var password = datos.clave();
        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generaToken(authentication);
        return new AuthResponse(username,  accessToken, true);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetail = this.loadUserByUsername(username);
        if(userDetail == null) {
            throw new BadCredentialsException("Invalid Username or password");
        }
        if(!passEncoder.matches(password, userDetail.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetail.getPassword(), userDetail.getAuthorities());
    }

    public AuthResponse crearUsuario(@Valid DatosAutenticacion datos) {
        var correo = datos.correo();
        var clave = datos.clave();

        var nuevoUsuario = new Usuario(null, correo, passEncoder.encode(clave));

        usuarioRepository.save(nuevoUsuario);

        Authentication auth = new UsernamePasswordAuthenticationToken(nuevoUsuario.getUsername(), nuevoUsuario.getPassword());

        var accessToken = jwtUtils.generaToken(auth);
        return new AuthResponse(nuevoUsuario.getCorreo(), accessToken, true);
    }
}
