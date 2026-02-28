package org.example.controler;

import org.example.model.Game;
import org.example.model.Team;

import java.util.List;
import java.util.Map;

public class Controller{
    private List<Team> teams;
    private Game game;
    private ControlRAF writeData;
    private ControlProperties controlProperties;

    public Controller() {
        this.controlProperties = new ControlProperties("teams.properties");
        this.teams = controlProperties.read();
        this.writeData = new ControlRAF("score.dat");
        this.game = new Game(this, teams);
        this.game.setTime(4);
        this.game.startGame();
    }

    public void notifyEndGame(Map<String, String> resultGame) {
        this.writeData.write(resultGame);
    }
}
