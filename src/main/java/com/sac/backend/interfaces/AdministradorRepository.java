package com.sac.backend.interfaces;

import com.sac.backend.models.AdministradorModel;
import org.springframework.data.repository.CrudRepository;

public interface AdministradorRepository extends CrudRepository<AdministradorModel, Long> {

    AdministradorModel findByLogin(String login);
}
