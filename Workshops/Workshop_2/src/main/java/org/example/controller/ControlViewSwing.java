package org.example.controller;

import org.example.view.ViewManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ControlViewSwing implements ActionListener, ControlView {

    private final ViewManager viewManager;
    private final Controller  controller;

    public ControlViewSwing(Controller controller) {
        this.controller  = controller;
        this.viewManager = new ViewManager();

        bindListeners();
        this.viewManager.showView();
        showAllMiniPig();
    }

    private void bindListeners() {
        this.viewManager.addPrincipalListener(this);
        this.viewManager.setListenersList(this, "search", "reset");
        this.viewManager.setActionSaveButton(this);
        this.viewManager.setActionCancelButton(this, "cancel");
        this.viewManager.setActionPhotoButton(this, "photo");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.contains("view"))             { handleView(cmd);         return; }
        if (cmd.contains("edit"))             { handleEdit(cmd);         return; }
        if (cmd.contains("deleteByCode"))     { handleDeleteByCode(cmd); return; }
        if (cmd.contains("deleteByMicrochip")){ handleDeleteByMicrochip(cmd); return; }
        if (cmd.contains("principal"))        { viewManager.showList();  return; }

        switch (cmd) {
            case "insertar"   -> openFormToCreate();
            case "guardar"    -> handleSave();
            case "actualizar" -> handleUpdate();
            case "cancel"     -> viewManager.showList();
            case "search"     -> handleSearch();
            case "reset"      -> showAllMiniPig();
            case "photo"      -> handlePhoto();
        }
    }

    private void handleView(String cmd) {
        String code = extractParam(cmd);
        showSingleMinipig(code);
    }

    private void handleEdit(String cmd) {
        String code = extractParam(cmd);
        openFormToModify(code);
    }

    private void handleDeleteByCode(String cmd) {
        String code = extractParam(cmd);
        this.controller.deleteByCode(code);
        showAllMiniPig();
    }

    private void handleDeleteByMicrochip(String cmd) {
        String microchipId = extractParam(cmd);
        this.controller.deleteByMicrochipId(microchipId);
        showAllMiniPig();
    }

    private void handleSave() {
        if (!viewManager.isValidForm()) {
            viewManager.showError("Por favor, complete todos los campos correctamente.");
            return;
        }
        addMinipig(viewManager.getDataForm());
        showAllMiniPig();
    }

    private void handleUpdate() {
        if (!viewManager.isValidForm()) {
            viewManager.showError("Por favor, complete todos los campos correctamente.");
            return;
        }
        this.controller.update(viewManager.getDataForm());
        showAllMiniPig();
    }

    private void handleSearch() {
        String value     = viewManager.getTextFieldSearch();
        String parameter = viewManager.getCriteria();
        showMiniPigByCriteria(parameter, value);
    }

    private void handlePhoto() {
        File file = viewManager.openFileChooser();
        if (file == null) return;
        try {
            viewManager.setPhotoLabel(file.getCanonicalPath());
        } catch (IOException ex) {
            throw new RuntimeException("Error al cargar la foto: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void showSingleMinipig(String code) {
        Map<String, Object> data = this.controller.findByCode(code);
        this.viewManager.showDetail(data);
    }

    @Override
    public void addRow(Map<String, Object> data) {
        this.viewManager.addRow(data, this);
    }

    @Override
    public void addMinipig(Map<String, Object> data) {
        this.controller.save(data);
        this.viewManager.addRow(data, this);
    }

    @Override
    public void showAllMiniPig() {
        this.viewManager.clearRows();
        this.controller.findAll().forEach(this::addRow);
        this.viewManager.showList();
    }

    @Override
    public void showMiniPigByCriteria(String parameter, String value) {
        this.viewManager.clearRows();
        this.controller.findByParameter(parameter, value).forEach(this::addRow);
        this.viewManager.showList();
    }

    @Override
    public void openFormToCreate() {
        this.viewManager.showFormToCreate();
    }

    @Override
    public void openFormToModify(String code) {
        Map<String, Object> data = this.controller.findByCode(code);
        this.viewManager.showFormToModify(data);
    }

    private String extractParam(String cmd) {
        return cmd.split(":")[1].trim();
    }
}