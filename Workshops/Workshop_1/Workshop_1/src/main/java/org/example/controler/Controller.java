package org.example.controler;

import org.example.model.Suscriber;
import org.example.model.Team;
import org.example.view.PrincipalView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

public class Controller implements Suscriber, ActionListener {

    private List<Team> teams;
    private ControlGame game;

    private final GestorArchivos gestorArchivos;
    private final PrincipalView  view;

    public Controller() {
        this.gestorArchivos = new GestorArchivos();
        this.view           = new PrincipalView();
        bindListeners();
    }

    // ── Listeners ─────────────────────────────────────────────────────────────

    private void bindListeners() {
        view.getLoadButton().addActionListener(this);
        view.getStartGameButton().addActionListener(this);
        view.getPreChargeButton().addActionListener(this);
        view.getCloseButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if      (src == view.getLoadButton())      handleLoad();
        else if (src == view.getStartGameButton()) handleStartGame();
        else if (src == view.getPreChargeButton()) handlePreCharge();
        else if (src == view.getCloseButton())     System.exit(0);
    }

    // ── Handlers ──────────────────────────────────────────────────────────────

    private void handleLoad() {
        File file = view.openFileChooser();
        if (file != null) loadFromProperties(file.getAbsolutePath());
    }

    private void handlePreCharge() {
        File file = view.openFileChooser();
        if (file != null) loadFromSerialized(file.getAbsolutePath());
    }

    private void handleStartGame() {
        view.getStartGameButton().setEnabled(false);
        view.getLoadButton().setEnabled(false);
        game = new ControlGame(this, teams);
        game.setTime(Math.max(4, view.getSelectedTime()));
        new Thread(() -> game.startGame()).start();
    }

    // ── Carga de equipos ──────────────────────────────────────────────────────

    private void loadFromProperties(String path) {
        try {
            TeamGridMapper teamGridMapper = new TeamGridMapper();
            teams = gestorArchivos.loadTeamsFromProperties(path);
            view.showGameGrid(teamGridMapper.buildGridData(teams));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFromSerialized(String path) {
        try {
            TeamGridMapper teamGridMapper = new TeamGridMapper();
            teams = gestorArchivos.loadTeamsFromSerialized(path);
            view.showGameGrid(teamGridMapper.buildGridData(teams));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ── Suscriber ─────────────────────────────────────────────────────────────

    @Override
    public void notifyEndGame(Map<String, String> resultGame) {
        gestorArchivos.saveGameResults(resultGame);
        gestorArchivos.saveTeamsSerialized(teams);
        view.showResults(resultGame, buildWinnerSummary(resultGame));
    }

    @Override
    public void notifyTurnChange(int teamIndex, int playerIndex, BaleroOptions lastResult, Map<String, Integer> scores) {
        String     resultText   = lastResult != null ? lastResult.name() + " (+" + lastResult.getPoints() + " pts)" : "";
        String[]   scoresArray  = buildScoresArray(scores);
        boolean[]  activeTeams  = buildActiveTeams(teamIndex);
        boolean[][] activePlayers = buildActivePlayers(teamIndex, playerIndex);

        view.updateActiveState(activeTeams, activePlayers, resultText, scoresArray);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String buildWinnerSummary(Map<String, String> resultGame) {
        for (Map.Entry<String, String> entry : resultGame.entrySet()) {
            if (!entry.getValue().trim().endsWith("W")) continue;
            String teamName = entry.getKey();
            List<String> wins = gestorArchivos.readWinsByTeam(teamName);
            return wins.isEmpty()
                    ? "El equipo " + teamName + " ha ganado por primera vez:\n"
                    : "El equipo " + teamName + " ha ganado " + wins.size() + " veces:\n";
        }
        return "";
    }

    private String[] buildScoresArray(Map<String, Integer> scores) {
        String[] arr = new String[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            Integer score = scores.get(teams.get(i).getTeamName());
            arr[i] = String.valueOf(score != null ? score : 0);
        }
        return arr;
    }

    private boolean[] buildActiveTeams(int teamIndex) {
        boolean[] active = new boolean[teams.size()];
        active[teamIndex] = true;
        return active;
    }

    private boolean[][] buildActivePlayers(int teamIndex, int playerIndex) {
        boolean[][] active = new boolean[teams.size()][];
        for (int i = 0; i < teams.size(); i++) {
            active[i] = new boolean[teams.get(i).getPlayers().size()];
            if (i == teamIndex) active[i][playerIndex] = true;
        }
        return active;
    }
}