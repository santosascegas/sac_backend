package com.sac.backend.interfaces;

import com.sac.backend.models.AgendamentoModel;

import org.springframework.data.repository.CrudRepository;

public interface AgendamentoRepository extends CrudRepository<AgendamentoModel, Long> {

}