package org.example.controler;

import org.example.model.Team;
import org.example.model.Player;
import org.example.model.connection.PropertiesConnection;

import java.util.*;
import java.util.stream.Collectors;

public class ControlProperties {
    private final PropertiesConnection propertiesConnection;

    public ControlProperties(String filePath) {
        this.propertiesConnection = new PropertiesConnection(filePath);
    }

    /**
     * Lee el archivo properties y devuelve una lista de equipos parseados
     * @return List<Team> con los equipos cargados desde el archivo properties
     */
    public List<Team> read() {
        Properties properties = propertiesConnection.read();
        return parseTeams(properties);
    }

    /**
     * Parsea los datos de equipos desde un objeto Properties
     * @param properties objeto Properties con los datos cargados
     * @return List de Teams mapeados y listos para usar
     */
    private List<Team> parseTeams(Properties properties) {
        List<Team> teams = new ArrayList<>();

        Set<String> ids = properties.stringPropertyNames()
                .stream()
                .map(c -> c.split("\\.")[1])
                .collect(Collectors.toSet());

        for (String id : ids) {
            String curriculumProject = properties.getProperty("team." + id + ".curriculumProject");
            String teamName = properties.getProperty("team." + id + ".teamName");

            String playersString = properties.getProperty("team." + id + ".players");
            String[] playersArray = playersString.split(";");

            List<Player> players = new ArrayList<>();
            for (String player : playersArray) {
                String[] attributes = player.split(",");
                if (attributes.length != 2) {
                    throw new RuntimeException("Invalid player format for player: " + player);
                }

                String code = attributes[0].trim();
                String name = attributes[1].trim();
                players.add(new Player(code, name));
            }

            teams.add(new Team(curriculumProject, teamName, players));
        }

        return teams;
    }
}




