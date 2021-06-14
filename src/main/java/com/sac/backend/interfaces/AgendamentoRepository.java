package com.sac.backend.interfaces;

import com.sac.backend.models.Agendamento;

import org.springframework.data.jpa.repository.JpaRepository;
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

}