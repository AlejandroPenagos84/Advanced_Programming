package org.example.view;

import javax.swing.*;
import java.awt.*;

/**
 * Header reutilizable para todos los paneles de la aplicación.
 * Uso: new PigHeader("Título", "Subtítulo")
 */
public class PigHeader extends JPanel {

    private static final Color ACCENT = new Color(0xE07A5F);

    private final JLabel titleLabel;
    private final JLabel subtitleLabel;

    public PigHeader(String title, String subtitle) {
        setLayout(new BorderLayout());
        setBackground(ACCENT);
        setBorder(BorderFactory.createEmptyBorder(18, 28, 18, 28));

        JLabel icon = new JLabel("🐷");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 14));

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));

        JPanel texts = new JPanel(new GridLayout(2, 1));
        texts.setOpaque(false);
        texts.add(titleLabel);
        texts.add(subtitleLabel);

        add(icon,  BorderLayout.WEST);
        add(texts, BorderLayout.CENTER);
    }

    public void setInfo(String title, String subtitle) {
        titleLabel.setText(title);
        subtitleLabel.setText(subtitle);
    }
}