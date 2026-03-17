package org.example.view;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public Button(String text, Color color, Color bottonColor) {
        super(text);
        setBackground(color);
        setForeground(bottonColor);
        setFocusPainted(false);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setMargin(new Insets(2, 4, 2, 4));
    }
}
