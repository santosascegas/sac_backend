package com.sac.backend.interfaces;

import com.sac.backend.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);
}
