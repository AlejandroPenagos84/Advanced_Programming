package org.example.controller;

import org.example.view.FormMiniPig;
import org.example.view.ViewManager;

import javax.naming.ldap.Control;
import javax.swing.text.html.FormView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public interface ControlView{
    void showSingleMinipig(String code);
    void addRow(Map<String, Object> data);
    void addMinipig(Map<String, Object> data);
    void showAllMiniPig();
    void showMiniPigByCriteria(String parameter, String value);
    void openFormToCreate();
    void openFormToModify(String code);
}
