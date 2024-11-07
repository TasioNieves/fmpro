package com.tmpro.repository;

import com.tmpro.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    // Puedes añadir métodos personalizados si es necesario
    List<Match> findByDate(LocalDateTime date);
}
