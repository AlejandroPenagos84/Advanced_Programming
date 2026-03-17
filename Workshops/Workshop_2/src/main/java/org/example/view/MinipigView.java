package org.example.view;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MinipigView extends JPanel {

    private final JPanel           tablePanel;
    private final JScrollPane      scrollPane;
    private final List<MinipigRow> rows;
    private int currentRow = 1;

    private final JTextField        searchField  = new JTextField(20);
    private final JComboBox<String> searchCombo  = new JComboBox<>(new String[]{
            "Código", "Microchip", "Nombre", "Raza", "Género"
    });
    private final JButton searchButton = new JButton("Buscar");
    private final JButton resetButton  = new JButton("Restablecer");

    private static final Color ACCENT       = new Color(0xE07A5F);
    private static final Color ACCENT_DARK  = new Color(0xC05A3F);
    private static final Color HEADER_FG    = Color.WHITE;
    private static final Color PANEL_BG     = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(0xD4B8A8);
    private static final Color SEARCH_BG    = new Color(0xFFF8F4);
    private static final Color BTN_RESET    = new Color(0x9E9E9E);

    private static final Map<String, String> CRITERIA_MAP = Map.of(
            "Código",    "code",
            "Microchip", "microchipId",
            "Nombre",    "name",
            "Raza",      "race",
            "Género",    "gender"
    );

    private static final String[] HEADERS = {
            "Código", "Microchip", "Nombre", "Raza", "Género",
            "Ver", "Editar", "Código", "Microchip"
    };

    public MinipigView() {
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);
        rows = new ArrayList<>();

        add(buildSearchBar(), BorderLayout.NORTH);

        tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(PANEL_BG);
        buildHeader();
        buildVerticalFiller();

        scrollPane = new JScrollPane(tablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_COLOR));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(PANEL_BG);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel buildSearchBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bar.setBackground(SEARCH_BG);
        bar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JLabel icon = new JLabel("🔍");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        searchCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchCombo.setBackground(Color.WHITE);

        styleButton(searchButton, ACCENT,    ACCENT_DARK);
        styleButton(resetButton,  BTN_RESET, new Color(0x757575));

        bar.add(icon);
        bar.add(searchField);
        bar.add(searchCombo);
        bar.add(searchButton);
        bar.add(resetButton);
        return bar;
    }

    private void styleButton(JButton btn, Color bg, Color border) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border, 1),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }

    private void buildHeader() {
        for (int i = 0; i < HEADERS.length; i++) {
            JLabel lbl = new JLabel(HEADERS[i], SwingConstants.CENTER);
            lbl.setForeground(HEADER_FG);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setOpaque(true);
            lbl.setBackground(ACCENT);
            lbl.setPreferredSize(new Dimension(0, 42));
            lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT_DARK));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx   = i;
            gbc.gridy   = 0;
            gbc.weightx = MinipigRow.COL_WEIGHTS[i];
            gbc.fill    = GridBagConstraints.BOTH;
            tablePanel.add(lbl, gbc);
        }
    }

    private void buildVerticalFiller() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx     = 0;
        gbc.gridy     = 9999;
        gbc.weighty   = 1.0;
        gbc.gridwidth = HEADERS.length;
        tablePanel.add(Box.createGlue(), gbc);
    }

    public void addRow(Map<String, Object> data, ActionListener actionListener) {
        MinipigRow row = new MinipigRow(data);
        row.setActionListener(actionListener, data);
        rows.add(row);

        Component[] cols = row.getComponents();
        for (int i = 0; i < cols.length; i++) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx   = i;
            gbc.gridy   = currentRow;
            gbc.weightx = MinipigRow.COL_WEIGHTS[i];
            gbc.fill    = GridBagConstraints.BOTH;
            gbc.insets  = new Insets(1, 0, 1, 0);
            tablePanel.add(cols[i], gbc);
        }

        addRowSeparator(cols.length);
        currentRow++;
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private void addRowSeparator(int colSpan) {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx     = 0;
        gbc.gridy     = currentRow;
        gbc.gridwidth = colSpan;
        gbc.fill      = GridBagConstraints.HORIZONTAL;
        gbc.anchor    = GridBagConstraints.SOUTH;
        tablePanel.add(sep, gbc);
    }

    public void clearRows() {
        rows.forEach(row -> {
            for (Component c : row.getComponents()) tablePanel.remove(c);
        });
        rows.clear();
        currentRow = 1;
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    public String getSearchText()     { return searchField.getText().trim(); }
    public String getSearchCriteria() { return CRITERIA_MAP.get((String) searchCombo.getSelectedItem()); }

    public void addSearchListener(ActionListener l, String action) { searchButton.addActionListener(l); searchButton.setActionCommand(action); }
    public void addResetListener(ActionListener l, String action)  { resetButton.addActionListener(l);  resetButton.setActionCommand(action);  }
}