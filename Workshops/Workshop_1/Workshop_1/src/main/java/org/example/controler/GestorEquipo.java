package org.example.controler;

import org.example.model.Player;
import org.example.model.Team;

import java.util.*;
import java.util.stream.Collectors;

public class GestorEquipo {

    private final GestorJugador gestorJugador;

    public GestorEquipo() {
        this.gestorJugador = new GestorJugador();
    }

    public List<Team> parseTeamsFromProperties(Properties properties) {
        List<Team> teams = new ArrayList<>();

        Set<String> ids = properties.stringPropertyNames()
                .stream()
                .map(c -> c.split("\\.")[1])
                .collect(Collectors.toSet());

        for (String id : ids) {
            String curriculumProject = properties.getProperty("team." + id + ".curriculumProject");
            String teamName = properties.getProperty("team." + id + ".teamName");
            String playersString = properties.getProperty("team." + id + ".players");

            List<Player> players = gestorJugador.parsePlayers(playersString);
            teams.add(new Team(curriculumProject, teamName, players));
        }

        return teams;
    }

    /**
     * Parsea equipos desde un archivo deserializado
     * @param obj objeto deserializado
     * @return List de Teams
     */
    public List<Team> parseTeamsFromSerialized(Object obj) {
        if (obj instanceof List<?>) {
            return (List<Team>) obj;
        } else if (obj instanceof Team) {
            List<Team> teams = new ArrayList<>();
            teams.add((Team) obj);
            return teams;
        }
        return List.of();
    }
}

