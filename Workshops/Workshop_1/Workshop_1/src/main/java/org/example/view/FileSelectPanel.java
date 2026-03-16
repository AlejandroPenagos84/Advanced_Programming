package org.example.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileSelectPanel extends JPanel {
    private final JFileChooser fileChooser;
    private final JButton loadButton;
    private final JButton preChargeButton;
    private final JLabel addTimeLabel;
    private final JSpinner timeSpinner;

    public FileSelectPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        loadButton = new JButton("Cargar Archivo");
        loadButton.setFont(loadButton.getFont().deriveFont(16f));
        loadButton.setFocusPainted(false);
        loadButton.setBackground(new Color(70, 130, 180));
        loadButton.setForeground(Color.WHITE);
        loadButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        preChargeButton = new JButton("Cargar Equipos Predefinidos");
        preChargeButton.setFont(preChargeButton.getFont().deriveFont(16f));
        preChargeButton.setFocusPainted(false);
        preChargeButton.setBackground(new Color(34, 139, 34));
        preChargeButton.setForeground(Color.WHITE);
        preChargeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        addTimeLabel = new JLabel("Tiempo (seg):");
        addTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(4, 4, 120, 1);
        timeSpinner = new JSpinner(spinnerModel);
        timeSpinner.setFont(new Font("SansSerif", Font.PLAIN, 14));
        timeSpinner.setPreferredSize(new Dimension(70, 30));

        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo de datos");

        add(loadButton);
        add(preChargeButton);
        add(addTimeLabel);
        add(timeSpinner);
    }

    public File openFileChooser() {
        return fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION
                ? fileChooser.getSelectedFile()
                : null;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getPreChargeButton() {
        return preChargeButton;
    }

    public int getSelectedTime() {
        return (int) timeSpinner.getValue();
    }
}
