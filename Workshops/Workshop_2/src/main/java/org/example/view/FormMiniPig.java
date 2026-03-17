package org.example.view;

import org.example.view.form.StyledComboBox;
import org.example.view.form.StyledLabel;
import org.example.view.form.StyledSpinner;
import org.example.view.form.StyledTextField;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class FormMiniPig extends JPanel {

    private StyledTextField codeField;
    private StyledTextField nameField;
    private StyledComboBox  genderCombo;
    private StyledTextField microchipIdField;
    private StyledTextField raceField;
    private StyledTextField colorField;
    private StyledSpinner   weightSpinner;
    private StyledSpinner   pigHeightSpinner;
    private StyledTextField characteristic1Field;
    private StyledTextField characteristic2Field;

    private JLabel photoLabel;
    private Button photoButton;
    private Button saveButton;
    private Button cancelButton;

    private static final Color BG           = new Color(0xFFF8F4);
    private static final Color PANEL_BG     = Color.WHITE;
    private static final Color ACCENT       = new Color(0xE07A5F);
    private static final Color FIELD_BORDER = new Color(0xD4B8A8);
    private static final Color BTN_CANCEL   = new Color(0x9E9E9E);
    private static final Color BTN_PHOTO    = new Color(0x7C8DB5);
    private static final Color FIELD_ERROR  = new Color(0xFFCDD2);

    private static final String GENDER_PLACEHOLDER = "— Seleccionar —";
    private static final String PHOTO_PLACEHOLDER  = "Sin imagen seleccionada";

    public FormMiniPig() {
        setLayout(new BorderLayout());
        setBackground(BG);
        add(buildForm(),   BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private JScrollPane buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(PANEL_BG);
        form.setBorder(BorderFactory.createEmptyBorder(24, 32, 8, 32));

        GridBagConstraints gbc = defaultGbc();

        addSectionTitle(form, gbc, "Identificación", 0);
        codeField        = new StyledTextField(10);
        microchipIdField = new StyledTextField(20);
        nameField        = new StyledTextField(20);
        genderCombo      = new StyledComboBox(new String[]{GENDER_PLACEHOLDER, "Macho", "Hembra"});
        addRow(form, gbc, "Código *",       codeField,        1, 0);
        addRow(form, gbc, "ID Microchip *", microchipIdField, 1, 2);
        addRow(form, gbc, "Nombre *",       nameField,        2, 0);
        addRow(form, gbc, "Género *",       genderCombo,      2, 2);

        addSectionTitle(form, gbc, "Características físicas", 3);
        raceField        = new StyledTextField(16);
        colorField       = new StyledTextField(16);
        weightSpinner    = new StyledSpinner(0.0, 500.0, 0.1);
        pigHeightSpinner = new StyledSpinner(0.0, 200.0, 0.1);
        addRow(form, gbc, "Raza",        raceField,        4, 0);
        addRow(form, gbc, "Color",       colorField,       4, 2);
        addRow(form, gbc, "Peso (kg)",   weightSpinner,    5, 0);
        addRow(form, gbc, "Altura (cm)", pigHeightSpinner, 5, 2);

        addSectionTitle(form, gbc, "Características especiales", 6);
        characteristic1Field = new StyledTextField(24);
        characteristic2Field = new StyledTextField(24);
        addFullRow(form, gbc, "Característica 1", characteristic1Field, 7);
        addFullRow(form, gbc, "Característica 2", characteristic2Field, 8);

        addSectionTitle(form, gbc, "Fotografía", 9);
        addPhotoRow(form, gbc, 10);

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(PANEL_BG);
        return scroll;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 14));
        footer.setBackground(new Color(0xF5EDE8));
        footer.setBorder(new MatteBorder(1, 0, 0, 0, FIELD_BORDER));

        cancelButton = new Button("Cancelar", BTN_CANCEL, Color.BLACK);
        saveButton   = new Button("Guardar",  ACCENT,     Color.BLACK);

        footer.add(cancelButton);
        footer.add(saveButton);
        return footer;
    }

    private GridBagConstraints defaultGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 6, 7, 6);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private void addSectionTitle(JPanel p, GridBagConstraints gbc, String text, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        gbc.insets = new Insets(18, 6, 4, 6);
        JLabel lbl = new JLabel(text.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(ACCENT);
        lbl.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0xF0D8CC)));
        p.add(lbl, gbc);
        gbc.gridwidth = 1;
        gbc.insets    = new Insets(7, 6, 7, 6);
    }

    private void addRow(JPanel p, GridBagConstraints gbc,
                        String label, JComponent field, int row, int col) {
        gbc.gridx = col;     gbc.gridy = row; gbc.weightx = 0; p.add(new StyledLabel(label), gbc);
        gbc.gridx = col + 1;                  gbc.weightx = 1; p.add(field,                  gbc);
        gbc.weightx = 0;
    }

    private void addFullRow(JPanel p, GridBagConstraints gbc,
                            String label, JComponent field, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0; p.add(new StyledLabel(label), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1;                  p.add(field,                  gbc);
        gbc.gridwidth = 1; gbc.weightx = 0;
    }

    private void addPhotoRow(JPanel p, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0;
        p.add(new StyledLabel("Foto del animal"), gbc);

        photoButton = new Button("Seleccionar foto", BTN_PHOTO, Color.BLACK);
        photoLabel  = new JLabel(PHOTO_PLACEHOLDER);
        photoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        photoLabel.setForeground(new Color(0xAA8877));

        JPanel photoPanel = new JPanel(new BorderLayout(10, 0));
        photoPanel.setOpaque(false);
        photoPanel.add(photoButton, BorderLayout.WEST);
        photoPanel.add(photoLabel,  BorderLayout.CENTER);

        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1;
        p.add(photoPanel, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0;
    }

    public boolean isFormValid() {
        boolean valid = true;
        valid &= validateTextField(codeField);
        valid &= validateTextField(microchipIdField);
        valid &= validateTextField(nameField);
        valid &= validateGender();
        valid &= validateSpinner(weightSpinner);
        valid &= validateSpinner(pigHeightSpinner);
        valid &= validatePhoto();
        return valid;
    }

    private boolean validateTextField(StyledTextField field) {
        boolean empty = field.getText().trim().isEmpty();
        field.setBackground(empty ? FIELD_ERROR : Color.WHITE);
        return !empty;
    }

    private boolean validateSpinner(StyledSpinner spinner) {
        boolean invalid = spinner.getDoubleValue() <= 0.0;
        spinner.setBackground(invalid ? FIELD_ERROR : Color.WHITE);
        return !invalid;
    }

    private boolean validateGender() {
        boolean invalid = GENDER_PLACEHOLDER.equals(genderCombo.getSelectedValue());
        genderCombo.setBackground(invalid ? FIELD_ERROR : Color.WHITE);
        return !invalid;
    }

    private boolean validatePhoto() {
        boolean invalid = PHOTO_PLACEHOLDER.equals(photoLabel.getText());
        photoLabel.setBackground(invalid ? FIELD_ERROR : Color.WHITE);
        photoLabel.setOpaque(invalid);
        return !invalid;
    }

    private void clearValidationHighlights() {
        codeField.setBackground(Color.WHITE);
        microchipIdField.setBackground(Color.WHITE);
        nameField.setBackground(Color.WHITE);
        genderCombo.setBackground(Color.WHITE);
        weightSpinner.setBackground(Color.WHITE);
        pigHeightSpinner.setBackground(Color.WHITE);
        photoLabel.setOpaque(false);
    }

    public Map<String, Object> getFormData() {
        Map<String, Object> data = new HashMap<>();
        data.put("code",            codeField.getText().trim());
        data.put("name",            nameField.getText().trim());
        data.put("microchipId",     microchipIdField.getText().trim());
        data.put("race",            raceField.getText().trim());
        data.put("color",           colorField.getText().trim());
        data.put("characteristic1", characteristic1Field.getText().trim());
        data.put("characteristic2", characteristic2Field.getText().trim());
        data.put("weight",          weightSpinner.getDoubleValue());
        data.put("height",          pigHeightSpinner.getDoubleValue());
        data.put("gender",          resolveGender());
        data.put("photo",           extractPhotoPath());
        return data;
    }

    private String resolveGender() {
        String gender = genderCombo.getSelectedValue();
        return GENDER_PLACEHOLDER.equals(gender) ? "" : gender;
    }

    private String extractPhotoPath() {
        String text = photoLabel.getText().trim();
        if (!text.startsWith("✅")) return null;
        String path = text.replaceFirst("^✅\\s*", "").trim();
        return path.isEmpty() ? null : path;
    }

    public void setData(Map<String, Object> data) {
        clearValidationHighlights();
        if (data == null) { clearFields(); return; }
        populateFields(data);
    }

    private void clearFields() {
        codeField.setText("");
        nameField.setText("");
        microchipIdField.setText("");
        raceField.setText("");
        colorField.setText("");
        characteristic1Field.setText("");
        characteristic2Field.setText("");
        weightSpinner.setValue(0.0);
        pigHeightSpinner.setValue(0.0);
        genderCombo.setSelectedIndex(0);
        photoLabel.setText(PHOTO_PLACEHOLDER);
        photoLabel.setForeground(new Color(0xAA8877));
    }

    private void populateFields(Map<String, Object> data) {
        setTextField(codeField,            data.get("code"));
        setTextField(nameField,            data.get("name"));
        setTextField(microchipIdField,     data.get("microchipId"));
        setTextField(raceField,            data.get("race"));
        setTextField(colorField,           data.get("color"));
        setTextField(characteristic1Field, data.get("characteristic1"));
        setTextField(characteristic2Field, data.get("characteristic2"));
        setSpinner(weightSpinner,    data.get("weight"));
        setSpinner(pigHeightSpinner, data.get("height"));

        Object gender = data.get("gender");
        if (gender != null) {
            String g = String.valueOf(gender);
            genderCombo.setSelectedItem(g.isEmpty() ? GENDER_PLACEHOLDER : g);
        }

        Object photo = data.get("photo");
        if (photo != null) setPhotoLabel(String.valueOf(photo));
    }

    private void setTextField(StyledTextField field, Object value) {
        field.setText(value == null ? "" : String.valueOf(value));
    }

    private void setSpinner(StyledSpinner spinner, Object value) {
        if (value instanceof Number) {
            spinner.setValue(((Number) value).doubleValue());
        } else if (value != null) {
            try { spinner.setValue(Double.parseDouble(String.valueOf(value))); }
            catch (NumberFormatException ignored) {}
        }
    }

    public void setPhotoLabel(String fileName) {
        photoLabel.setText("✅  " + fileName);
        photoLabel.setForeground(new Color(0x3A7D44));
    }

    public void setCommandButton(String action) {
        saveButton.setText(action);
        saveButton.setActionCommand(action.toLowerCase());
    }

    public void setIdFieldsEnabled(boolean enabled) {
        codeField.setEnabled(enabled);
        microchipIdField.setEnabled(enabled);
    }

    public void setActionSaveButton(ActionListener l)                  { saveButton.addActionListener(l);                              }
    public void setActionCancelButton(ActionListener l, String action) { cancelButton.addActionListener(l); cancelButton.setActionCommand(action); }
    public void setActionPhotoButton(ActionListener l, String action)  { photoButton.addActionListener(l);  photoButton.setActionCommand(action);  }

    public void showError(String message) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                message, "Campo requerido", JOptionPane.WARNING_MESSAGE);
    }
}