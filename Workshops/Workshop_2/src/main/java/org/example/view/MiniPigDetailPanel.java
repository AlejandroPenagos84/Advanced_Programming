package org.example.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;

public class MiniPigDetailPanel extends JPanel {

    private static final Color BG           = new Color(0xFFF8F4);
    private static final Color PANEL_BG     = Color.WHITE;
    private static final Color ACCENT       = new Color(0xE07A5F);
    private static final Color FIELD_BORDER = new Color(0xD4B8A8);
    private static final Color LABEL_FG     = new Color(0x9E7060);
    private static final Color VALUE_FG     = new Color(0x2E2E2E);

    private static final int PHOTO_W = 160;
    private static final int PHOTO_H = 160;

    private final JLabel codeValue            = valueLabel();
    private final JLabel nameValue            = valueLabel();
    private final JLabel microchipIdValue     = valueLabel();
    private final JLabel genderValue          = valueLabel();
    private final JLabel raceValue            = valueLabel();
    private final JLabel colorValue           = valueLabel();
    private final JLabel weightValue          = valueLabel();
    private final JLabel heightValue          = valueLabel();
    private final JLabel characteristic1Value = valueLabel();
    private final JLabel characteristic2Value = valueLabel();
    private final JLabel photoLabel           = buildPhotoLabel();

    public MiniPigDetailPanel() {
        setLayout(new BorderLayout());
        setBackground(BG);
        add(buildScrollableContent(), BorderLayout.CENTER);
    }

    private JLabel buildPhotoLabel() {
        JLabel lbl = new JLabel("Sin imagen", SwingConstants.CENTER);
        lbl.setPreferredSize(new Dimension(PHOTO_W, PHOTO_H));
        lbl.setMinimumSize(new Dimension(PHOTO_W, PHOTO_H));
        lbl.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lbl.setForeground(new Color(0xBBA090));
        lbl.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(12, FIELD_BORDER),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        lbl.setOpaque(false);
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        return lbl;
    }

    private JScrollPane buildScrollableContent() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(PANEL_BG);
        content.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        GridBagConstraints gbc = defaultGbc();

        addSectionHeader(content, gbc, "Identificación", 0);
        addIdentificationBlock(content, gbc);

        addSection(content, gbc, "Características físicas", 2);
        addDetailRow(content, gbc, "Raza",        raceValue,   3, 0);
        addDetailRow(content, gbc, "Color",       colorValue,  3, 2);
        addDetailRow(content, gbc, "Peso (kg)",   weightValue, 4, 0);
        addDetailRow(content, gbc, "Altura (cm)", heightValue, 4, 2);

        addSection(content, gbc, "Características especiales", 5);
        addFullDetailRow(content, gbc, "Característica 1", characteristic1Value, 6);
        addFullDetailRow(content, gbc, "Característica 2", characteristic2Value, 7);

        gbc.gridx = 0; gbc.gridy = 8; gbc.weighty = 1;
        content.add(Box.createVerticalGlue(), gbc);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(PANEL_BG);
        return scroll;
    }

    private void addIdentificationBlock(JPanel content, GridBagConstraints gbc) {
        JPanel idGrid = new JPanel(new GridBagLayout());
        idGrid.setOpaque(false);
        GridBagConstraints ig = defaultGbc();
        addDetailRow(idGrid, ig, "Código",       codeValue,        0, 0);
        addDetailRow(idGrid, ig, "ID Microchip", microchipIdValue, 0, 2);
        addDetailRow(idGrid, ig, "Nombre",       nameValue,        1, 0);
        addDetailRow(idGrid, ig, "Género",       genderValue,      1, 2);

        JPanel idAndPhoto = new JPanel(new BorderLayout(20, 0));
        idAndPhoto.setOpaque(false);
        idAndPhoto.add(idGrid,     BorderLayout.CENTER);
        idAndPhoto.add(photoLabel, BorderLayout.EAST);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4; gbc.weightx = 1;
        content.add(idAndPhoto, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0;
    }

    private GridBagConstraints defaultGbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 6, 7, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        return g;
    }

    private void addSectionHeader(JPanel p, GridBagConstraints gbc, String text, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 6, 4, 6);
        p.add(sectionLabel(text), gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(7, 6, 7, 6);
    }

    private void addSection(JPanel p, GridBagConstraints gbc, String text, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 6, 4, 6);
        p.add(sectionLabel(text), gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(7, 6, 7, 6);
    }

    private void addDetailRow(JPanel p, GridBagConstraints gbc,
                              String lbl, JComponent val, int row, int col) {
        gbc.gridx = col;     gbc.gridy = row; gbc.weightx = 0; p.add(captionLabel(lbl), gbc);
        gbc.gridx = col + 1;                  gbc.weightx = 1; p.add(val,               gbc);
        gbc.weightx = 0;
    }

    private void addFullDetailRow(JPanel p, GridBagConstraints gbc,
                                  String lbl, JComponent val, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0; p.add(captionLabel(lbl), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1;                  p.add(val,               gbc);
        gbc.gridwidth = 1; gbc.weightx = 0;
    }

    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(ACCENT);
        l.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0xF0D8CC)));
        return l;
    }

    private JLabel captionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(LABEL_FG);
        return l;
    }

    private static JLabel valueLabel() {
        JLabel l = new JLabel("—");
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(VALUE_FG);
        l.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8, new Color(0xEEDDD5)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        l.setOpaque(true);
        l.setBackground(new Color(0xFFF8F4));
        return l;
    }

    public void setData(Map<String, Object> data) {
        if (data == null) return;

        setText(codeValue,            data.get("code"));
        setText(nameValue,            data.get("name"));
        setText(microchipIdValue,     data.get("microchipId"));
        setText(genderValue,          data.get("gender"));
        setText(raceValue,            data.get("race"));
        setText(colorValue,           data.get("color"));
        setText(characteristic1Value, data.get("characteristic1"));
        setText(characteristic2Value, data.get("characteristic2"));

        Object w = data.get("weight");
        weightValue.setText(w != null ? w + " kg" : "—");

        Object h = data.get("height");
        heightValue.setText(h != null ? h + " cm" : "—");

        Object photo = data.get("photo");
        if (photo != null) setPhoto(String.valueOf(photo));
    }

    public void setPhoto(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            if (img == null) { showPhotoError("Imagen no válida"); return; }

            double scale = Math.min((double) PHOTO_W / img.getWidth(), (double) PHOTO_H / img.getHeight());
            Image scaled = img.getScaledInstance(
                    (int) (img.getWidth()  * scale),
                    (int) (img.getHeight() * scale),
                    Image.SCALE_SMOOTH);

            photoLabel.setIcon(new ImageIcon(scaled));
            photoLabel.setText("");
        } catch (Exception e) {
            showPhotoError("No se pudo cargar");
        }
    }

    public void clearPhoto() {
        photoLabel.setIcon(null);
        photoLabel.setText("Sin imagen");
    }

    private void setText(JLabel lbl, Object value) {
        lbl.setText((value == null || String.valueOf(value).isBlank()) ? "—" : String.valueOf(value));
    }

    private void showPhotoError(String msg) {
        photoLabel.setIcon(null);
        photoLabel.setText("⚠ " + msg);
        photoLabel.setForeground(ACCENT);
    }

    private static class RoundedBorder extends AbstractBorder {
        private final int   radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color  = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }
    }
}