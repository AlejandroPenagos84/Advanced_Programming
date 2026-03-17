package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

public class PrincipalView extends JFrame {

    private static final Color BG       = new Color(0xFFF8F4);
    private static final Color ACCENT   = new Color(0xE07A5F);
    private static final Color ACCENT_DARK = new Color(0xC05A3F);
    private static final Color TITLE_FG = new Color(0x2E2E2E);
    private static final Color BTN_BG   = new Color(0xF5EDE8);

    private static final String FONT = "Segoe UI";

    private GridPlayers        gridPlayers;
    private final FileSelectPanel fileSelectPanel;
    private final JButton         startGameButton;
    private final ResultsPanel    resultsPanel;

    public PrincipalView() {
        setTitle("Juego de Balero - Workshop 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        startGameButton = buildStartButton();
        resultsPanel    = new ResultsPanel();

        add(buildTitle(),                            BorderLayout.NORTH);
        add(fileSelectPanel = new FileSelectPanel(), BorderLayout.CENTER);
        add(buildButtonPanel(),                      BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel buildTitle() {
        JLabel lbl = new JLabel("Juego de Balero", SwingConstants.CENTER);
        lbl.setFont(new Font(FONT, Font.BOLD, 22));
        lbl.setForeground(TITLE_FG);
        lbl.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        return lbl;
    }

    private JButton buildStartButton() {
        JButton btn = new JButton("Iniciar Juego");
        btn.setFont(new Font(FONT, Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(ACCENT);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_DARK, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setEnabled(false);
        return btn;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(BTN_BG);
        panel.add(startGameButton);
        return panel;
    }

    public void showGameGrid(String[][][] data) {
        remove(fileSelectPanel);
        gridPlayers = new GridPlayers(data);
        add(gridPlayers, BorderLayout.CENTER);
        startGameButton.setEnabled(true);
        revalidate();
        repaint();
    }

    public void showResults(Map<String, String> resultGame, String previousResult) {
        SwingUtilities.invokeLater(() -> {
            if (gridPlayers != null) remove(gridPlayers);
            resultsPanel.setResults(resultGame, previousResult);
            add(resultsPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
    }

    public void updateActiveState(boolean[] activeTeams, boolean[][] activePlayers, String lastResult, String[] scores) {
        SwingUtilities.invokeLater(() ->
                gridPlayers.updateActiveState(activeTeams, activePlayers, lastResult, scores)
        );
    }

    public File    openFileChooser()   { return fileSelectPanel.openFileChooser();      }
    public int     getSelectedTime()   { return fileSelectPanel.getSelectedTime();      }
    public JButton getLoadButton()     { return fileSelectPanel.getLoadButton();        }
    public JButton getPreChargeButton(){ return fileSelectPanel.getPreChargeButton();   }
    public JButton getStartGameButton(){ return startGameButton;                        }
    public JButton getCloseButton()    { return resultsPanel.getCloseButton();          }
}