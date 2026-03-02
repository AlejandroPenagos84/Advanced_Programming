package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Panel que muestra los resultados finales del juego.
 * Recibe un Map donde:
 *   - Key: nombre del equipo
 *   - Value: "jugador1, jugador2, puntaje, W/E/L"
 */
public class ResultsPanel extends JPanel {

    private static final Color WINNER_BG = new Color(34, 139, 34);
    private static final Color TIE_BG = new Color(218, 165, 32);
    private static final Color LOSER_BG = new Color(180, 60, 60);
    private static final Color CARD_FG = Color.WHITE;
    private JButton closeButton;

    public ResultsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));

        closeButton = new JButton("Cerrar");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        closeButton.setFocusPainted(false);
        closeButton.setBackground(new Color(70, 130, 180));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(createTitle(), BorderLayout.NORTH);
        add(closeButton, BorderLayout.SOUTH);
    }

    /**
     * Carga los resultados del juego y los muestra en el panel.
     * @param resultGame Map con los resultados del juego
     * @param previousResult String con victorias anteriores
     */
    public void setResults(Map<String, String> resultGame, String previousResult) {
        add(createCardsContainer(resultGame), BorderLayout.CENTER);

        if (previousResult != null && !previousResult.isEmpty()) {
            remove(closeButton);
            JPanel southPanel = new JPanel();
            southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
            southPanel.setOpaque(false);
            southPanel.add(createPreviousResultsPanel(previousResult));
            southPanel.add(Box.createVerticalStrut(10));
            southPanel.add(closeButton);
            add(southPanel, BorderLayout.SOUTH);
        }

        revalidate();
        repaint();
    }


    private JLabel createTitle() {
        JLabel titleLabel = new JLabel("Resultados Finales", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        return titleLabel;
    }

    private JPanel createCardsContainer(Map<String, String> resultGame) {
        JPanel cardsContainer = new JPanel(new GridLayout(1, resultGame.size(), 15, 0));
        cardsContainer.setOpaque(false);

        for (Map.Entry<String, String> entry : resultGame.entrySet()) {
            String teamName = entry.getKey();
            String value = entry.getValue();

            String[] parts = value.split(",");
            String resultFlag = parts[parts.length - 1].trim();
            String score = parts[parts.length - 2].trim();
            StringBuilder playersBuilder = new StringBuilder();
            for (int i = 0; i < parts.length - 2; i++) {
                if (i > 0) playersBuilder.append(", ");
                playersBuilder.append(parts[i].trim());
            }
            String players = playersBuilder.toString();

            cardsContainer.add(createTeamResultCard(teamName, players, score, resultFlag));
        }

        return cardsContainer;
    }

    private JPanel createPreviousResultsPanel(String previousResult) {
        JPanel previousPanel = new JPanel();
        previousPanel.setLayout(new BoxLayout(previousPanel, BoxLayout.Y_AXIS));
        previousPanel.setBackground(new Color(245, 245, 245));
        previousPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JLabel prevTitle = new JLabel("Victorias anteriores", SwingConstants.CENTER);
        prevTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        prevTitle.setForeground(new Color(100, 100, 100));
        prevTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        previousPanel.add(prevTitle);
        previousPanel.add(Box.createVerticalStrut(4));

        JTextArea prevText = new JTextArea(previousResult);
        prevText.setEditable(false);
        prevText.setFont(new Font("Monospaced", Font.PLAIN, 10));
        prevText.setBackground(new Color(235, 235, 235));
        prevText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(prevText);
        scrollPane.setPreferredSize(new Dimension(0, 60));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        previousPanel.add(scrollPane);

        return previousPanel;
    }

    private JPanel createTeamResultCard(String teamName, String players, String score, String resultFlag) {
        Color bgColor;
        String resultText;

        switch (resultFlag) {
            case "W":
                bgColor = WINNER_BG;
                resultText = "GANADOR";
                break;
            case "E":
                bgColor = TIE_BG;
                resultText = "EMPATE";
                break;
            default:
                bgColor = LOSER_BG;
                resultText = "PERDEDOR";
                break;
        }

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel resultLabel = new JLabel(resultText, SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        resultLabel.setForeground(CARD_FG);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(resultLabel);
        card.add(Box.createVerticalStrut(10));

        JLabel nameLabel = new JLabel(teamName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(CARD_FG);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(10));

        JLabel scoreLabel = new JLabel("Puntaje: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        scoreLabel.setForeground(CARD_FG);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(scoreLabel);
        card.add(Box.createVerticalStrut(10));

        JLabel playersLabel = new JLabel("<html><center>Jugadores:<br>" + players.replace(", ", "<br>") + "</center></html>", SwingConstants.CENTER);
        playersLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        playersLabel.setForeground(CARD_FG);
        playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(playersLabel);

        return card;
    }

    public JButton getCloseButton() {
        return closeButton;
    }
}

