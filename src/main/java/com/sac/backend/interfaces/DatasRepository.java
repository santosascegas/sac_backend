package com.sac.backend.interfaces;

import com.sac.backend.models.DatasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DatasRepository extends JpaRepository<DatasModel, Long> {

    @Query("SELECT d FROM Datas d WHERE d.status = true ORDER BY d.id")
    List<DatasModel> findByDisponibilidade();

    @Query("SELECT d FROM Datas d WHERE d.data BETWEEN d.data=?1 AND d.data=?2")
    List<DatasModel> findByDataBetween(Date ini, Date fim);
}
