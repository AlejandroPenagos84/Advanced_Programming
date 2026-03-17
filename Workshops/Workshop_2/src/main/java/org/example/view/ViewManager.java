package org.example.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

public class ViewManager extends JFrame {

    public static final String VIEW_LIST   = "LIST";
    public static final String VIEW_FORM   = "FORM";
    public static final String VIEW_DETAIL = "DETAIL";

    private final MinipigView        minipigView;
    private final FormMiniPig        formMiniPig;
    private final MiniPigDetailPanel detailPanel;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel     cardPanel  = new JPanel(cardLayout);

    private static final Color ACCENT    = new Color(0xE07A5F);
    private static final Color BG        = new Color(0xFFF8F4);
    private static final Color BTN_BG    = new Color(0xC45A3F);

    private final PigHeader header         = new PigHeader("Mini Pig Manager", "Listado de animales registrados");
    private final JButton   principalButton = new JButton("← Insertar");
    private final JPanel    northPanel;

    private final JFileChooser fileChooser = new JFileChooser(
            new File(System.getProperty("user.dir"))
    );

    public ViewManager() {
        setTitle("Mini Pig Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 600);
        setMinimumSize(new Dimension(900, 400));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        minipigView = new MinipigView();
        formMiniPig = new FormMiniPig();
        detailPanel = new MiniPigDetailPanel();

        cardPanel.add(minipigView, VIEW_LIST);
        cardPanel.add(formMiniPig, VIEW_FORM);
        cardPanel.add(detailPanel, VIEW_DETAIL);

        northPanel = buildNorth();
        add(northPanel, BorderLayout.NORTH);
        add(cardPanel,  BorderLayout.CENTER);

        cardLayout.show(cardPanel, VIEW_LIST);
        principalButton.setActionCommand("insertar");
    }

    private JPanel buildNorth() {
        stylePrincipalButton();

        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(ACCENT);
        right.setBorder(new EmptyBorder(0, 0, 0, 28));
        right.add(principalButton);

        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(ACCENT);
        north.add(header, BorderLayout.CENTER);
        north.add(right,  BorderLayout.EAST);
        return north;
    }

    private void stylePrincipalButton() {
        principalButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        principalButton.setForeground(Color.WHITE);
        principalButton.setBackground(BTN_BG);
        principalButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x8B3A28), 1),
                new EmptyBorder(8, 20, 8, 20)
        ));
        principalButton.setFocusPainted(false);
        principalButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        principalButton.setOpaque(true);
        principalButton.setContentAreaFilled(true);
        principalButton.setVisible(true);
    }

    private void setPrincipalButton(String text, String actionCommand, boolean visible) {
        principalButton.setText(text);
        principalButton.setActionCommand(actionCommand);
        principalButton.setVisible(visible);
        northPanel.revalidate();
        northPanel.repaint();
    }

    public void showDetail(Map<String, Object> data) {
        header.setInfo("Detalle del Mini Pig", "Información completa del animal seleccionado");
        setPrincipalButton("← Principal", "principal", true);
        detailPanel.setData(data);
        cardLayout.show(cardPanel, VIEW_DETAIL);
    }

    public void showFormToCreate() {
        header.setInfo("Registrar Nuevo Mini Pig", "Complete el formulario para agregar un nuevo animal");
        setPrincipalButton("← Principal", "principal", true);
        formMiniPig.setData(null);
        formMiniPig.setIdFieldsEnabled(true);
        formMiniPig.setCommandButton("Guardar");
        cardLayout.show(cardPanel, VIEW_FORM);
    }

    public void showFormToModify(Map<String, Object> data) {
        header.setInfo("Modificar Mini Pig", "Actualice los campos necesarios y presione 'Actualizar'");
        setPrincipalButton("← Principal", "principal", true);
        formMiniPig.setData(data);
        formMiniPig.setIdFieldsEnabled(false);
        formMiniPig.setCommandButton("Actualizar");
        cardLayout.show(cardPanel, VIEW_FORM);
    }

    public void showList() {
        header.setInfo("Mini Pig Manager", "Listado de animales registrados");
        setPrincipalButton("← Insertar", "insertar", true);
        cardLayout.show(cardPanel, VIEW_LIST);
    }

    public void showView() { setVisible(true); }

    public void addPrincipalListener(ActionListener l)                          { principalButton.addActionListener(l);            }
    public void addRow(Map<String, Object> data, ActionListener l)              { minipigView.addRow(data, l);                     }
    public void clearRows()                                                     { minipigView.clearRows();                         }
    public void setActionSaveButton(ActionListener l)                           { formMiniPig.setActionSaveButton(l);              }
    public void setActionCancelButton(ActionListener l, String action)          { formMiniPig.setActionCancelButton(l, action);    }
    public void setActionPhotoButton(ActionListener l, String action)           { formMiniPig.setActionPhotoButton(l, action);     }
    public void setListenersList(ActionListener l, String add, String reset)    { minipigView.addSearchListener(l, add); minipigView.addResetListener(l, reset); }
    public void setPhotoLabel(String filename)                                  { formMiniPig.setPhotoLabel(filename);             }
    public void showError(String message)                                       { formMiniPig.showError(message);                  }
    public boolean isValidForm()                                                { return formMiniPig.isFormValid();                }
    public Map<String, Object> getDataForm()                                    { return formMiniPig.getFormData();                }
    public String getTextFieldSearch()                                          { return minipigView.getSearchText();              }
    public String getCriteria()                                                 { return minipigView.getSearchCriteria();          }

    public File openFileChooser() {
        return fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION
                ? fileChooser.getSelectedFile()
                : null;
    }
}