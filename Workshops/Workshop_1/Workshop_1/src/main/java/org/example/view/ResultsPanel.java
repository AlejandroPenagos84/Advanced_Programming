package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ResultsPanel extends JPanel {

    private static final Color BG          = new Color(0xFFF8F4);
    private static final Color PANEL_BG    = Color.WHITE;
    private static final Color ACCENT      = new Color(0xE07A5F);
    private static final Color ACCENT_DARK = new Color(0xC05A3F);
    private static final Color BORDER_COLOR= new Color(0xD4B8A8);
    private static final Color WINNER_BG   = new Color(0x3A7D44);
    private static final Color TIE_BG      = new Color(0xC8922A);
    private static final Color LOSER_BG    = new Color(0xB43C3C);
    private static final Color CARD_FG     = Color.WHITE;
    private static final Color TITLE_FG    = new Color(0x2E2E2E);
    private static final Color PREV_BG     = new Color(0xF5EDE8);
    private static final Color PREV_FG     = new Color(0x9E7060);

    private static final String FONT = "Segoe UI";

    private final JButton closeButton;

    public ResultsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(BG);

        closeButton = buildCloseButton();

        add(buildTitle(),   BorderLayout.NORTH);
        add(closeButton,    BorderLayout.SOUTH);
    }

    private JButton buildCloseButton() {
        JButton btn = new JButton("Cerrar");
        btn.setFont(new Font(FONT, Font.BOLD, 14));
        btn.setForeground(CARD_FG);
        btn.setBackground(ACCENT);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_DARK, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    private JLabel buildTitle() {
        JLabel lbl = new JLabel("Resultados Finales", SwingConstants.CENTER);
        lbl.setFont(new Font(FONT, Font.BOLD, 20));
        lbl.setForeground(TITLE_FG);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        return lbl;
    }

    public void setResults(Map<String, String> resultGame, String previousResult) {
        add(buildCardsContainer(resultGame), BorderLayout.CENTER);

        if (previousResult != null && !previousResult.isEmpty()) {
            remove(closeButton);
            JPanel south = new JPanel();
            south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
            south.setOpaque(false);
            south.add(buildPreviousResultsPanel(previousResult));
            south.add(Box.createVerticalStrut(10));
            south.add(closeButton);
            add(south, BorderLayout.SOUTH);
        }

        revalidate();
        repaint();
    }

    private JPanel buildCardsContainer(Map<String, String> resultGame) {
        JPanel container = new JPanel(new GridLayout(1, resultGame.size(), 15, 0));
        container.setOpaque(false);

        for (Map.Entry<String, String> entry : resultGame.entrySet()) {
            String[] parts      = entry.getValue().split(",");
            String resultFlag   = parts[parts.length - 1].trim();
            String score        = parts[parts.length - 2].trim();
            String players      = buildPlayerList(parts);
            container.add(buildTeamResultCard(entry.getKey(), players, score, resultFlag));
        }

        return container;
    }

    private String buildPlayerList(String[] parts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length - 2; i++) {
            if (i > 0) sb.append(", ");
            sb.append(parts[i].trim());
        }
        return sb.toString();
    }

    private JPanel buildPreviousResultsPanel(String previousResult) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PREV_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JLabel title = new JLabel("Victorias anteriores", SwingConstants.CENTER);
        title.setFont(new Font(FONT, Font.BOLD, 12));
        title.setForeground(PREV_FG);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea text = new JTextArea(previousResult);
        text.setEditable(false);
        text.setFont(new Font("Monospaced", Font.PLAIN, 10));
        text.setBackground(new Color(0xF0E0D8));
        text.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scroll = new JScrollPane(text);
        scroll.setPreferredSize(new Dimension(0, 60));
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(scroll);
        return panel;
    }

    private JPanel buildTeamResultCard(String teamName, String players, String score, String resultFlag) {
        Color bg         = resolveResultColor(resultFlag);
        String resultText= resolveResultText(resultFlag);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 2, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        card.add(cardLabel(resultText, Font.BOLD,  18));
        card.add(Box.createVerticalStrut(10));
        card.add(cardLabel(teamName,   Font.BOLD,  16));
        card.add(Box.createVerticalStrut(10));
        card.add(cardLabel("Puntaje: " + score, Font.PLAIN, 14));
        card.add(Box.createVerticalStrut(10));
        card.add(buildPlayersPanel(players));

        return card;
    }

    private JPanel buildPlayersPanel(String players) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel title = cardLabel("Jugadores:", Font.BOLD, 12);
        panel.add(title);
        panel.add(Box.createVerticalStrut(4));

        for (String player : players.split(",")) {
            panel.add(cardLabel(player.trim(), Font.PLAIN, 12));
        }

        return panel;
    }

    private JLabel cardLabel(String text, int style, int size) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font(FONT, style, size));
        lbl.setForeground(CARD_FG);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private Color resolveResultColor(String flag) {
        return switch (flag) {
            case "W" -> WINNER_BG;
            case "E" -> TIE_BG;
            default  -> LOSER_BG;
        };
    }

    private String resolveResultText(String flag) {
        return switch (flag) {
            case "W" -> "GANADOR";
            case "E" -> "EMPATE";
            default  -> "PERDEDOR";
        };
    }

    public JButton getCloseButton() { return closeButton; }
}