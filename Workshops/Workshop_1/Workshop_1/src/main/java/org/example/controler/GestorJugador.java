package org.example.controler;

import org.example.model.Player;

import java.util.ArrayList;
import java.util.List;

public class GestorJugador {

    public List<Player> parsePlayers(String playersString) {
        List<Player> players = new ArrayList<>();
        String[] playersArray = playersString.split(";");

        for (String player : playersArray) {
            String[] attributes = player.split(",");
            if (attributes.length != 2) {
                throw new RuntimeException("Invalid player format for player: " + player);
            }

            String code = attributes[0].trim();
            String name = attributes[1].trim();
            players.add(new Player(code, name));
        }

        return players;
    }
}

