package com.sac.backend.interfaces;

import java.util.List;

import com.sac.backend.models.Datas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface DatasRepository extends JpaRepository<Datas, Long> {

    @Query("from Datas d where d.status = 0")
    List<Datas> listarPorStatus();

}
