package org.example.controler;

import org.example.model.Game;
import org.example.model.Player;
import org.example.model.Team;
import org.example.model.connection.IReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    private List<Team> teams;
    private IReader readData;
    private Game game;

    public Controller(IReader readData) {
        this.teams = new ArrayList<>();
        this.readData = readData;
        loadTeams();
        this.game = new Game(teams);
        this.game.setTime(16);
        this.game.startGame();
    }

    private void loadTeams(){
        assert readData != null;
        Map<List<String>,List<List<String>>> data = readData.read();
        for(Map.Entry<List<String>,List<List<String>>> entry : data.entrySet()) {
            List<String> teamInfo = entry.getKey();
            List<List<String>> playersInfo = entry.getValue();

            List<Player> players = new ArrayList<>();
            for(List<String> playerInfo : playersInfo) {
                String code = playerInfo.get(0);
                String name = playerInfo.get(1);
                players.add(new Player(code, name));
            }

            teams.add(new Team(teamInfo.get(0), teamInfo.get(1), players));
        }
    }


}
