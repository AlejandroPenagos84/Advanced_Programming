package org.example.view.form;

import javax.swing.*;
import java.awt.*;

public class StyledLabel extends JLabel {

    private static final Color LABEL_FG = new Color(0x5C4033);

    public StyledLabel(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setForeground(LABEL_FG);
    }
}