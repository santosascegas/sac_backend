package com.sac.backend.interfaces;

import com.sac.backend.models.DatasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Maurício Freire
 * Date 03/06/2021 at 18:31
 * Created on IntelliJ IDEA
 */
public interface DatasRepository extends JpaRepository<DatasModel, Long> {

    @Query("SELECT * FROM Datas WHERE ic_disponivel = true ORDER BY id")
    List<DatasModel> findByDisponibilidade();

    @Query("SELECT * FROM Datas WHERE datas BETWEEN CURRENT_DATE() "
        + "+ CURRENT_DATE() + 7")
    List<DatasModel> findBySemana();
}
