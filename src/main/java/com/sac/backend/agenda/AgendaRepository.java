package com.sac.backend.agenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    @Query(value = "from agenda d where d.is_available = 0", nativeQuery = true)
    List<Agenda> listOnlyAvailable();
}
