package org.example.controller;

import org.example.model.DAO.MinipigDAO;
import org.example.model.DAO.MinipigImplDAO;
import org.example.model.Minipig;

import java.util.List;
import java.util.Map;

public class Controller {
    private MinipigDAO minipigDAO;
    private MinipigMapper minipigMapper;
    private FileManager fileManager;
    private ControlView controlView;

    public Controller() {
        this.minipigMapper = new MinipigMapper();
        this.fileManager = new FileManager("src/main/resources/db.properties");
        this.initializeDAO();
        this.controlView = new ControlViewSwing(this);
    }

    private void initializeDAO() {
        String[] dbConfig = fileManager.getDatabaseConfig();
        this.minipigDAO = new MinipigImplDAO(dbConfig[0], dbConfig[1], dbConfig[2], minipigMapper);
    }

    public Map<String, Object> findByCode(String code) {
        return minipigMapper.miniPigToMap(minipigDAO.findByCode(code));
    }

    public List<Map<String,Object>> findAll() {
        return minipigDAO.findAll().stream().map(p -> minipigMapper.miniPigToMap(p)).toList();
    }

    public List<Map<String,Object>> findByParameter(String parameter, String value) {
        return minipigDAO.findByParameter(parameter,value).stream().map(p -> minipigMapper.miniPigToMap(p)).toList();
    }

    public void save(Map<String,Object> data) {
        minipigDAO.save(minipigMapper.mapMiniPig(data));
    }

    public void update(Map<String,Object> minipig) {
        minipigDAO.update(minipigMapper.mapMiniPig(minipig));
    }

    public void deleteByCode(String code) {
        minipigDAO.deleteByCode(code);
    }

    public void deleteByMicrochipId(String microchipId) {
        minipigDAO.deleteByMicrochipId(microchipId);
    }
}
