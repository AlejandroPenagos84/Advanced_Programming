package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Factory para crear íconos estándar de jugador usando Graphics2D.
 */
public class PlayerIconFactory {

    private static final int ICON_SIZE = 36;
    private static final Color ICON_COLOR = new Color(100, 149, 237);

    public static ImageIcon createPlayerIcon() {
        BufferedImage img = new BufferedImage(
                ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(ICON_COLOR);
        g2.fillOval(ICON_SIZE / 2 - 7, 2, 14, 14);
        g2.fillRoundRect(ICON_SIZE / 2 - 10, 17, 20, 16, 6, 6);

        g2.dispose();
        return new ImageIcon(img);
    }
}

