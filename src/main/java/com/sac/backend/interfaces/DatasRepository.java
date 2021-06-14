package com.sac.backend.interfaces;

import com.sac.backend.models.Datas;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DatasRepository extends JpaRepository<Datas, Long> {

}
