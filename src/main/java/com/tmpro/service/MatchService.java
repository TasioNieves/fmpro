package com.tmpro.service;

import com.tmpro.model.Match;
import com.tmpro.model.Player;
import com.tmpro.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerService playerService;

    public List<Match> findAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> findMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    public Optional<Match> updateMatch(Long id, Match match) {
        if (matchRepository.existsById(id)) {
            match.setId(id); // Asegúrate de que la clase Match tenga un método setId
            return Optional.of(matchRepository.save(match));
        } else {
            return Optional.empty();
        }
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    public Optional<Match> updateLineup(Long matchId, List<Long> playerIds) {
        Optional<Match> matchOptional = matchRepository.findById(matchId);

        if (matchOptional.isPresent()) {
            Match match = matchOptional.get();

            // Convertir los IDs de jugadores a instancias de Player
            List<Player> players = playerIds.stream()
                    .map(playerService::findById) // Usar findById para obtener el jugador por su ID
                    .flatMap(Optional::stream)    // Filtrar los jugadores que están presentes
                    .collect(Collectors.toList());

            // Actualizar la alineación del partido
            match.setLineup(players); // Establece la alineación
            return Optional.of(matchRepository.save(match));
        }

        return Optional.empty();
    }
}
