package org.example.view.form;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StyledTextField extends JTextField {

    private static final Color FIELD_BORDER = new Color(0xD4B8A8);

    public StyledTextField(int cols) {
        super(cols);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(FIELD_BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
    }
}
