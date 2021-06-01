package com.sac.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sac.backend.DTO.CredencialDTO;
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
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authMan;
    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authMan, JWTUtil jwtUtil) {
        this.authMan = authMan;
        this.jwtUtil = jwtUtil;
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

            return authMan.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        response.addHeader("access-control-expose-headers", "Authorization");
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











