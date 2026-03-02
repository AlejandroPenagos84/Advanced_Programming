package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

/**
 * Vista principal del juego de Balero.
 */
public class PrincipalView extends JFrame {

    private GridPlayers gridPlayers;
    private final FileSelectPanel fileSelectPanel;
    private final JButton startGameButton;
    private ResultsPanel resultsPanel;

    public PrincipalView() {
        setTitle("Juego de Balero - Workshop 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Juego de Balero", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        title.setForeground(new Color(50, 50, 50));
        add(title, BorderLayout.NORTH);

        fileSelectPanel = new FileSelectPanel();
        add(fileSelectPanel, BorderLayout.CENTER);

        startGameButton = new JButton("Iniciar Juego");
        startGameButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        startGameButton.setFocusPainted(false);
        startGameButton.setBackground(new Color(34, 139, 34));
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        startGameButton.setEnabled(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(startGameButton);
        add(buttonPanel, BorderLayout.SOUTH);

        resultsPanel = new ResultsPanel();

        setVisible(true);
    }

    /**
     * Delega la actualización de estados a la grilla internamente.
     */
    public void updateActiveState(boolean[] activeTeams, boolean[][] activePlayers, String lastResult, String[] scores) {
        gridPlayers.updateActiveState(activeTeams, activePlayers, lastResult, scores);
    }

    public JButton getLoadButton() {
        return fileSelectPanel.getLoadButton();
    }

    public JButton getStartGameButton() {
        return startGameButton;
    }

    public JButton getPreChargeButton() {
        return fileSelectPanel.getPreChargeButton();
    }

    public int getSelectedTime() {
        return fileSelectPanel.getSelectedTime();
    }

    public JButton getCloseButton() {
        return this.resultsPanel.getCloseButton();
    }
    /**
     * Abre el JFileChooser y retorna el archivo seleccionado, o null si se canceló.
     */
    public File openFileChooser() {
        return fileSelectPanel.openFileChooser();
    }

    /**
     * Reemplaza el panel de selección de archivo por la grilla del juego.
     * @param data datos String[][][] para construir la grilla
     */
    public void showGameGrid(String[][][] data) {
        remove(fileSelectPanel);
        gridPlayers = new GridPlayers(data);
        add(gridPlayers, BorderLayout.CENTER);
        startGameButton.setEnabled(true);
        revalidate();
        repaint();
    }

    /**
     * Reemplaza la grilla por el panel de resultados finales.
     * @param resultGame Map con los resultados del juego
     * @param previousResult String con las victorias anteriores del equipo ganador
     */
    public void showResults(Map<String, String> resultGame, String previousResult) {
        SwingUtilities.invokeLater(() -> {
            if (gridPlayers != null) {
                remove(gridPlayers);
            }
            this.resultsPanel.setResults(resultGame, previousResult);
            add(resultsPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
    }
}
