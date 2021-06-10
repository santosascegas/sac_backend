package com.sac.backend.interfaces;

import com.sac.backend.models.DatasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface DatasRepository extends CrudRepository<DatasModel, Long> {

}
