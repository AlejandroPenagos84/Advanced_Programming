package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class MinipigRow {

    private final JLabel codeLabel;
    private final JLabel microchipLabel;
    private final JLabel nameLabel;
    private final JLabel raceLabel;
    private final JLabel genderLabel;

    private final Button viewButton;
    private final Button editButton;
    private final Button deleteByCodeButton;
    private final Button deleteByMicroChipIdButton;

    private static final Color BTN_VIEW    = new Color(0x7C8DB5);   // azul pizarra
    private static final Color BTN_EDIT    = new Color(0xE07A5F);   // terracota (= ACCENT del form)
    private static final Color BTN_DEL_C   = new Color(0xC0503A);   // terracota oscuro
    private static final Color BTN_DEL_M   = new Color(0x9E9E9E);   // gris neutro

    private static final Color ROW_ODD    = new Color(0xFFF8F4);    // mismo BG del form
    private static final Color ROW_EVEN   = new Color(0xFFF0E8);    // un tono más cálido
    private static final Color TEXT_FG    = new Color(0x5C4033);    // mismo LABEL_FG del form

    public static final double[] COL_WEIGHTS = {1.0, 1.0, 1.0, 1.0, 1.0, 0.3, 0.3, 0.35, 0.4};

    private static int instanceCount = 0;
    private final Color rowBg;

    public MinipigRow(Map<String, Object> data) {
        rowBg = (instanceCount++ % 2 == 0) ? ROW_ODD : ROW_EVEN;

        codeLabel      = createLabel(String.valueOf(data.get("code")));
        microchipLabel = createLabel(String.valueOf(data.get("microchipId")));
        nameLabel      = createLabel(String.valueOf(data.get("name")));
        raceLabel      = createLabel(String.valueOf(data.get("race")));
        genderLabel    = createLabel(String.valueOf(data.get("gender")));

        viewButton              = new Button("Ver",        BTN_VIEW, Color.BLACK);
        editButton              = new Button("Editar",     BTN_EDIT, Color.BLACK);
        deleteByCodeButton      = new Button("Código",     BTN_DEL_C, Color.BLACK);
        deleteByMicroChipIdButton = new Button("Microchip", BTN_DEL_M, Color.BLACK);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(TEXT_FG);
        label.setOpaque(true);
        label.setBackground(rowBg);
        label.setPreferredSize(new Dimension(0, 38));
        return label;
    }

    public Component[] getComponents() {
        return new Component[]{
                codeLabel, microchipLabel, nameLabel, raceLabel, genderLabel,
                viewButton, editButton, deleteByCodeButton, deleteByMicroChipIdButton
        };
    }

    public void setActionListener(ActionListener listener, Map<String, Object> data) {
        String code      = data.get("code").toString();
        String microchip = data.get("microchipId").toString();

        viewButton.addActionListener(listener);
        viewButton.setActionCommand("view:" + code + ":" + microchip);

        editButton.addActionListener(listener);
        editButton.setActionCommand("edit:" + code + ":" + microchip);

        deleteByCodeButton.addActionListener(listener);
        deleteByCodeButton.setActionCommand("deleteByCode:" + code);

        deleteByMicroChipIdButton.addActionListener(listener);
        deleteByMicroChipIdButton.setActionCommand("deleteByMicrochip:" + microchip);
    }
}
