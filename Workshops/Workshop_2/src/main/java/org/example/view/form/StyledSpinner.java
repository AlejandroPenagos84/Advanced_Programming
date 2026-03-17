package org.example.view.form;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StyledSpinner extends JSpinner {

    private static final Color FIELD_BORDER = new Color(0xD4B8A8);

    public StyledSpinner(double min, double max, double step) {
        super(new SpinnerNumberModel(min, min, max, step));
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ((JSpinner.DefaultEditor) getEditor()).getTextField()
                .setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        setBorder(new LineBorder(FIELD_BORDER, 1, true));
    }

    public double getDoubleValue() {
        return (Double) getValue();
    }
}