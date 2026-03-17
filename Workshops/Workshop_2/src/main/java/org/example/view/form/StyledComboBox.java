package org.example.view.form;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StyledComboBox extends JComboBox<String> {

    private static final Color FIELD_BORDER = new Color(0xD4B8A8);

    public StyledComboBox(String[] options) {
        super(options);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setBackground(Color.WHITE);
        setBorder(new LineBorder(FIELD_BORDER, 1, true));
    }

    public String getSelectedValue() {
        return getSelectedIndex() == 0 ? "" : (String) getSelectedItem();
    }
}