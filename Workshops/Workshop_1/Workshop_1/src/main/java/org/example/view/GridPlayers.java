package org.example.view;

import javax.swing.*;
import java.awt.*;

/**
 * Grilla que muestra los equipos y jugadores.
 * Recibe String[][][] donde:
 *   - Primera dimensión: equipos
 *   - Segunda dimensión: filas (0 = nombre equipo, 1..N = jugadores)
 *   - Tercera dimensión: [0] = nombre equipo/jugador, [1] = puntaje
 */
public class GridPlayers extends JPanel {

    private final TeamPanel[] teamPanels;
    private final JLabel lastResultLabel;

    public GridPlayers(String[][][] data) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 245, 245));

        int numTeams = data.length;
        teamPanels = new TeamPanel[numTeams];

        JPanel teamsContainer = new JPanel(new GridLayout(1, numTeams, 15, 0));
        teamsContainer.setOpaque(false);

        for (int t = 0; t < numTeams; t++) {
            String[][] teamData = data[t];
            String teamName = teamData[0][0];
            String teamScore = teamData[0][1];

            String[] playerNames = new String[teamData.length - 1];
            for (int p = 0; p < playerNames.length; p++) {
                playerNames[p] = teamData[p + 1][0];
            }

            teamPanels[t] = new TeamPanel(teamName, teamScore, playerNames);
            teamsContainer.add(teamPanels[t]);
        }

        lastResultLabel = new JLabel("Esperando inicio del juego...", SwingConstants.CENTER);
        lastResultLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        lastResultLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        add(teamsContainer, BorderLayout.CENTER);
        add(lastResultLabel, BorderLayout.SOUTH);
    }

    /**
     * Actualiza la grilla según los estados resueltos por el Controller.
     * @param activeTeams booleano por equipo indicando si está activo
     * @param activePlayers booleano por equipo/jugador indicando si es el jugador activo
     * @param lastResult texto de la última embocada
     * @param scores puntajes por equipo
     */
    public void updateActiveState(boolean[] activeTeams, boolean[][] activePlayers, String lastResult, String[] scores) {
        SwingUtilities.invokeLater(() -> {
            for (int t = 0; t < teamPanels.length; t++) {
                if (scores != null && t < scores.length) {
                    teamPanels[t].updateScore(scores[t]);
                }
                teamPanels[t].applyActiveState(activeTeams[t]);
                teamPanels[t].applyPlayerStates(activePlayers[t]);
            }

            if (lastResult != null && !lastResult.isEmpty()) {
                lastResultLabel.setText("Última embocada: " + lastResult);
            } else {
                lastResultLabel.setText("Turno en curso...");
            }

            revalidate();
            repaint();
        });
    }
}
