package com.sac.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sac.backend.DTO.CredencialDTO;
import com.sac.backend.interfaces.UsuarioRepository;
import com.sac.backend.models.Usuario;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authMan;
    private JWTUtil jwtUtil;
    private UsuarioRepository usuarioRepo;

    public JWTAuthenticationFilter(AuthenticationManager authMan,
               JWTUtil jwtUtil, UsuarioRepository usuarioRepo) {
        this.authMan = authMan;
        this.jwtUtil = jwtUtil;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            CredencialDTO cdto = new ObjectMapper().readValue(request
            .getInputStream(), CredencialDTO.class);

            UsernamePasswordAuthenticationToken authToken = new
                    UsernamePasswordAuthenticationToken(cdto.getLogin(),
                    cdto.getSenha(), new ArrayList<>());

            Authentication auth = authMan.authenticate(authToken);
            return auth;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException, ServletException {
        
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        
        response.addHeader("Authentication", "Bearer " + token);
        
        response.addHeader("access-control-expose-headers", "Authentication");
        
        Usuario usuario = usuarioRepo.findByLogin(username);
        
        usuario.setSenha(null);
        
        Gson gson = new Gson();
        String cliStr = gson.toJson(usuario);
        
        PrintWriter out = response.getWriter();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        out.print(cliStr);
        out.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().append(json());
    }

    private String json() {
        return "{\"timestamp\": " + new Date().getTime() + ", "
                + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
    }
}
