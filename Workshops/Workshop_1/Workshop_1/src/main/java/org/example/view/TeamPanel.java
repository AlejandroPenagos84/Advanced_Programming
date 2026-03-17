package org.example.view;

import javax.swing.*;
import java.awt.*;

public class TeamPanel extends JPanel {

    private static final Color BG              = new Color(0xFFF8F4);
    private static final Color PANEL_BG        = Color.WHITE;
    private static final Color ACCENT          = new Color(0xE07A5F);
    private static final Color ACCENT_DARK     = new Color(0xC05A3F);
    private static final Color BORDER_COLOR    = new Color(0xD4B8A8);
    private static final Color ACTIVE_BG       = new Color(0xE07A5F);
    private static final Color ACTIVE_FG       = Color.WHITE;
    private static final Color INACTIVE_BG     = new Color(0xF5EDE8);
    private static final Color INACTIVE_FG     = new Color(0x9E7060);
    private static final Color SCORE_FG        = new Color(0x3A7D44);
    private static final float WATERMARK_ALPHA = 0.35f;

    private static final String FONT = "Segoe UI";

    private final JLabel   nameLabel;
    private final JLabel   scoreLabel;
    private final JLabel[] playerLabels;

    public TeamPanel(String teamName, String teamScore, String[] playerNames) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(PANEL_BG);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        nameLabel  = buildNameLabel(teamName);
        scoreLabel = buildScoreLabel(teamScore);

        add(nameLabel);
        add(Box.createVerticalStrut(5));
        add(scoreLabel);
        add(Box.createVerticalStrut(10));

        playerLabels = new JLabel[playerNames.length];
        for (int i = 0; i < playerNames.length; i++) {
            JPanel card = buildPlayerCard(playerNames[i]);
            playerLabels[i] = (JLabel) card.getComponent(1);
            add(card);
            add(Box.createVerticalStrut(5));
        }
    }

    private JLabel buildNameLabel(String teamName) {
        JLabel lbl = new JLabel(teamName, SwingConstants.CENTER);
        lbl.setFont(new Font(FONT, Font.BOLD, 16));
        lbl.setOpaque(true);
        lbl.setBackground(ACCENT);
        lbl.setForeground(Color.WHITE);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        lbl.setPreferredSize(new Dimension(200, 40));
        lbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return lbl;
    }

    private JLabel buildScoreLabel(String teamScore) {
        JLabel lbl = new JLabel("Puntaje: " + teamScore, SwingConstants.CENTER);
        lbl.setFont(new Font(FONT, Font.BOLD, 14));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setForeground(SCORE_FG);
        return lbl;
    }

    private JPanel buildPlayerCard(String playerName) {
        JPanel card = new JPanel(new BorderLayout(8, 0));
        card.setBackground(INACTIVE_BG);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        card.add(new JLabel(PlayerIconFactory.createPlayerIcon()), BorderLayout.WEST);

        JLabel lbl = new JLabel(playerName);
        lbl.setFont(new Font(FONT, Font.PLAIN, 13));
        lbl.setForeground(INACTIVE_FG);
        card.add(lbl, BorderLayout.CENTER);

        return card;
    }

    public void updateScore(String score) {
        scoreLabel.setText("Puntaje: " + score);
    }

    public void applyActiveState(boolean isActive) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isActive ? ACCENT_DARK : BORDER_COLOR, isActive ? 3 : 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setComponentsAlpha(this, isActive ? 1.0f : WATERMARK_ALPHA);
    }

    public void applyPlayerStates(boolean[] activePlayers) {
        for (int i = 0; i < playerLabels.length; i++) {
            JPanel card  = (JPanel) playerLabels[i].getParent();
            boolean active = activePlayers[i];
            card.setBackground(active ? ACTIVE_BG : INACTIVE_BG);
            playerLabels[i].setForeground(active ? ACTIVE_FG : INACTIVE_FG);
            playerLabels[i].setFont(new Font(FONT, active ? Font.BOLD : Font.PLAIN, 13));
        }
    }

    private void setComponentsAlpha(Container container, float alpha) {
        for (Component c : container.getComponents()) {
            if (c instanceof JLabel lbl) {
                Color fg = lbl.getForeground();
                lbl.setForeground(new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), (int) (alpha * 255)));
            }
            if (c instanceof Container child) setComponentsAlpha(child, alpha);
        }
    }
}