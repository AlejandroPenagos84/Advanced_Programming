package org.example.controler;

import org.example.model.BaleroOptions;
import org.example.model.Game;
import org.example.model.Player;
import org.example.model.Suscriber;
import org.example.model.Team;
import org.example.model.connection.AccessFileConnection;
import org.example.view.PrincipalView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

public class Controller implements Suscriber , ActionListener {
    private List<Team> teams;
    private Game game;
    private ControlRAF controlRAF;
    private PrincipalView view;
    private ControlSerializedFile controlSerializedFile;

    public Controller() {
        this.controlRAF = new ControlRAF(new AccessFileConnection("score.dat"));
        this.controlSerializedFile = new ControlSerializedFile("teams.ser");

        this.view = new PrincipalView();
        this.view.getLoadButton().addActionListener(this);
        this.view.getStartGameButton().addActionListener(this);
        this.view.getPreChargeButton().addActionListener(this);
        this.view.getCloseButton().addActionListener(this);
    }

    /**
     * Construye el String[][][] para la grilla.
     * [equipo][0] = {nombreEquipo, "0"} (header con puntaje)
     * [equipo][1..N] = {nombreJugador, ""} (jugadores)
     */
    private String[][][] buildGridData() {
        String[][][] data = new String[teams.size()][][];

        for (int t = 0; t < teams.size(); t++) {
            Team team = teams.get(t);
            List<Player> players = team.getPlayers();
            data[t] = new String[players.size() + 1][2];

            data[t][0] = new String[]{team.getTeamName(), "0"};

            for (int p = 0; p < players.size(); p++) {
                data[t][p + 1] = new String[]{players.get(p).getName(), ""};
            }
        }

        return data;
    }

    @Override
    public void notifyEndGame(Map<String, String> resultGame) {
        this.controlRAF.write(resultGame);
        this.controlSerializedFile.write(this.teams);

        String teamResult = "";
        for (Map.Entry<String, String> entry : resultGame.entrySet()) {
            String result = entry.getValue().trim();
            if (result.endsWith("W")) {
                List<String> wins = this.controlRAF.readTeams(entry.getKey());
                if(!wins.isEmpty()) teamResult = "El equipos " + entry.getKey() + " ha ganado " + wins.size() + " veces:\n";
                else teamResult = "El equipo " + entry.getKey() + " ha ganado por primera vez:\n";
            }
        }

        this.view.showResults(resultGame, teamResult);
    }

    @Override
    public void notifyTurnChange(int teamIndex, int playerIndex, BaleroOptions lastResult, Map<String, Integer> scores) {
        String resultText = (lastResult != null) ? lastResult.name() + " (+" + lastResult.getPoints() + " pts)" : "";

        String[] scoresArray = new String[teams.size()];
        boolean[] activeTeams = new boolean[teams.size()];
        boolean[][] activePlayers = new boolean[teams.size()][];

        for (int i = 0; i < teams.size(); i++) {
            String teamName = teams.get(i).getTeamName();
            Integer score = scores.get(teamName);
            scoresArray[i] = String.valueOf(score != null ? score : 0);

            activeTeams[i] = (i == teamIndex);

            int numPlayers = teams.get(i).getPlayers().size();
            activePlayers[i] = new boolean[numPlayers];
            for (int j = 0; j < numPlayers; j++) {
                activePlayers[i][j] = (i == teamIndex && j == playerIndex);
            }
        }

        view.updateActiveState(activeTeams, activePlayers, resultText, scoresArray);
    }

    /**
     * Carga un archivo .properties externo con los equipos.
     * @param path ruta absoluta del archivo .properties seleccionado
     */
    private void loadPropertiesFile(String path) {
        try {
            ControlProperties controlProperties = new ControlProperties(path);
            this.teams = controlProperties.read();

            String[][][] gridData = buildGridData();
            this.view.showGameGrid(gridData);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadSerializedFile(String path) {
        try {
            this.controlSerializedFile = new ControlSerializedFile(path);
            this.teams = controlSerializedFile.read();

            String[][][] gridData = buildGridData();
            this.view.showGameGrid(gridData);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.getLoadButton()) {
            File selectedFile = this.view.openFileChooser();
            if (selectedFile != null) {
                loadPropertiesFile(selectedFile.getAbsolutePath());
            }
        } else if (e.getSource() == this.view.getStartGameButton()) {
            this.view.getStartGameButton().setEnabled(false);
            this.view.getLoadButton().setEnabled(false);
            this.game = new Game(this, teams);
            int selectedTime = Math.max(4, this.view.getSelectedTime());
            this.game.setTime(selectedTime);

            new Thread(() -> this.game.startGame()).start();
        }else if (e.getSource() == this.view.getPreChargeButton()){
            loadSerializedFile("teams.ser");
        }else if(e.getSource() == this.view.getCloseButton()){
            System.exit(0);
        }
    }
}
