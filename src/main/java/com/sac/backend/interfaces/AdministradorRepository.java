package com.sac.backend.interfaces;

import com.sac.backend.models.Administrador;

import org.springframework.data.jpa.repository.JpaRepository;
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

}