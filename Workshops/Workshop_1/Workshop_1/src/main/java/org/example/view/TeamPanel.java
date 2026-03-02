package org.example.view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel visual que representa un equipo individual.
 * Contiene el header con nombre, el puntaje y las tarjetas de jugadores.
 */
public class TeamPanel extends JPanel {

    private static final Color ACTIVE_PLAYER_BG = new Color(50, 50, 50);
    private static final Color ACTIVE_PLAYER_FG = Color.WHITE;
    private static final Color NORMAL_PLAYER_BG = new Color(220, 220, 220);
    private static final Color NORMAL_PLAYER_FG = Color.BLACK;
    private static final Color TEAM_HEADER_BG = new Color(70, 130, 180);
    private static final Color TEAM_HEADER_FG = Color.WHITE;
    private static final float WATERMARK_ALPHA = 0.35f;

    private final JLabel nameLabel;
    private final JLabel scoreLabel;
    private final JLabel[] playerLabels;

    public TeamPanel(String teamName, String teamScore, String[] playerNames) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(Color.WHITE);

        nameLabel = createNameLabel(teamName);
        add(nameLabel);
        add(Box.createVerticalStrut(5));

        scoreLabel = createScoreLabel(teamScore);
        add(scoreLabel);
        add(Box.createVerticalStrut(10));

        playerLabels = new JLabel[playerNames.length];
        for (int p = 0; p < playerNames.length; p++) {
            JPanel card = createPlayerCard(playerNames[p]);
            playerLabels[p] = (JLabel) card.getComponent(1);
            add(card);
            add(Box.createVerticalStrut(5));
        }
    }

    private JLabel createNameLabel(String teamName) {
        JLabel label = new JLabel(teamName, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setOpaque(true);
        label.setBackground(TEAM_HEADER_BG);
        label.setForeground(TEAM_HEADER_FG);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        label.setPreferredSize(new Dimension(200, 40));
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }

    private JLabel createScoreLabel(String teamScore) {
        JLabel label = new JLabel("Puntaje: " + teamScore, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(new Color(0, 100, 0));
        return label;
    }

    private JPanel createPlayerCard(String playerName) {
        JPanel card = new JPanel(new BorderLayout(8, 0));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        card.setBackground(NORMAL_PLAYER_BG);

        card.add(new JLabel(PlayerIconFactory.createPlayerIcon()), BorderLayout.WEST);

        JLabel label = new JLabel(playerName);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setForeground(NORMAL_PLAYER_FG);
        card.add(label, BorderLayout.CENTER);

        return card;
    }

    /**
     * Actualiza el puntaje mostrado
     */
    public void updateScore(String score) {
        scoreLabel.setText("Puntaje: " + score);
    }

    /**
     * Aplica el estado activo/inactivo al panel del equipo
     */
    public void applyActiveState(boolean isActive) {
        if (isActive) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 120, 215), 3, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            setComponentsAlpha(this, 1.0f);
        } else {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            setComponentsAlpha(this, WATERMARK_ALPHA);
        }
    }

    /**
     * Aplica el estado activo/inactivo a cada jugador
     */
    public void applyPlayerStates(boolean[] activePlayers) {
        for (int p = 0; p < playerLabels.length; p++) {
            JPanel card = (JPanel) playerLabels[p].getParent();
            if (activePlayers[p]) {
                card.setBackground(ACTIVE_PLAYER_BG);
                playerLabels[p].setForeground(ACTIVE_PLAYER_FG);
                playerLabels[p].setFont(new Font("SansSerif", Font.BOLD, 13));
            } else {
                card.setBackground(NORMAL_PLAYER_BG);
                playerLabels[p].setForeground(NORMAL_PLAYER_FG);
                playerLabels[p].setFont(new Font("SansSerif", Font.PLAIN, 13));
            }
        }
    }

    /**
     * Aplica opacidad simulada cambiando colores de componentes
     */
    private void setComponentsAlpha(Container container, float alpha) {
        for (Component c : container.getComponents()) {
            if (c instanceof JLabel label) {
                Color fg = label.getForeground();
                label.setForeground(new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), (int) (alpha * 255)));
            }
            if (c instanceof Container) {
                setComponentsAlpha((Container) c, alpha);
            }
        }
    }
}

