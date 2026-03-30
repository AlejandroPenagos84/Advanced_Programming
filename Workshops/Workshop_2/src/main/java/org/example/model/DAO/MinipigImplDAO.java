package org.example.model.DAO;

import org.example.model.Minipig;
import org.example.controller.MinipigMapper;
import org.example.model.conecction.BDConecction;

import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.Map;

public class MinipigImplDAO implements MinipigDAO {
    private final String url;
    private final String user;
    private final String password;
    private final String driver;
    private final MinipigMapper minipigMapper;

    public MinipigImplDAO(String driver,String url, String user, String password, MinipigMapper minipigMapper) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
        this.minipigMapper = minipigMapper;
    }

    @Override
    public Minipig findByCode(String code) {
        String query = "SELECT * FROM minipig WHERE code = ?";
        try (Connection cn = BDConecction.getConnection(driver,url, user, password);
             PreparedStatement stm = cn.prepareStatement(query)) {

            stm.setString(1, code);
            try (ResultSet resultSet = stm.executeQuery()) {
                if (resultSet.next()) {
                    Map<String, Object> data = mapResultSetToMap(resultSet);
                    return minipigMapper.mapMiniPig(data);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding by code: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Minipig> findAll() {
        String query = "SELECT * FROM minipig";
        try (Connection cn = BDConecction.getConnection(driver,url, user, password);
             Statement stm = cn.createStatement();
             ResultSet resultSet = stm.executeQuery(query)) {

            List<Minipig> minipigs = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> data = mapResultSetToMap(resultSet);
                minipigs.add(minipigMapper.mapMiniPig(data));
            }
            return minipigs;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Minipig> findByParameter(String parameter, String value) {
        List<String> allowedColumns = List.of("code", "microchipId", "name", "race", "gender");

        if (!allowedColumns.contains(parameter)) {
            throw new IllegalArgumentException("Columna no permitida: " + parameter);
        }

        String query = "SELECT * FROM minipig WHERE " + parameter + " LIKE '%' || ? || '%'";

        try (Connection cn = BDConecction.getConnection(driver,url, user, password);
             PreparedStatement stm = cn.prepareStatement(query)) {

            stm.setString(1, value);

            try (ResultSet resultSet = stm.executeQuery()) {
                List<Minipig> minipigs = new ArrayList<>();
                while (resultSet.next()) {
                    Map<String, Object> data = mapResultSetToMap(resultSet);
                    minipigs.add(minipigMapper.mapMiniPig(data));
                }
                return minipigs;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding by " + parameter + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void save(Minipig minipig) {
        String query = "INSERT INTO minipig (code, name, gender, microchipId, race, color, weight, height, characteristic1, characteristic2, photoUrl) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = BDConecction.getConnection(driver,url, user, password);
             PreparedStatement stm = cn.prepareStatement(query)) {

            stm.setString(1, minipig.getCode());
            stm.setString(2, minipig.getName());
            stm.setString(3, minipig.getGender());
            stm.setString(4, minipig.getMicrochipId());
            stm.setString(5, minipig.getRace());
            stm.setString(6, minipig.getColor());
            stm.setDouble(7, minipig.getWeight());
            stm.setDouble(8, minipig.getHeight());
            stm.setString(9, minipig.getCharacteristic1());
            stm.setString(10, minipig.getCharacteristic2());
            stm.setString(11, minipig.getPhotoUrl());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving minipig: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Minipig minipig) {
        String query = "UPDATE minipig SET name = ?, gender = ?, microchipId = ?, race = ?, color = ?, weight = ?, height = ?, characteristic1 = ?, characteristic2 = ?, photoUrl = ? " +
                "WHERE code = ?";
        try (Connection cn = BDConecction.getConnection(driver,url, user, password);
             PreparedStatement stm = cn.prepareStatement(query)) {

            System.out.println(minipig.getPhotoUrl());
            stm.setString(1, minipig.getName());
            stm.setString(2, minipig.getGender());
            stm.setString(3, minipig.getMicrochipId());
            stm.setString(4, minipig.getRace());
            stm.setString(5, minipig.getColor());
            stm.setDouble(6, minipig.getWeight());
            stm.setDouble(7, minipig.getHeight());
            stm.setString(8, minipig.getCharacteristic1());
            stm.setString(9, minipig.getCharacteristic2());
            stm.setString(10, minipig.getPhotoUrl());
            stm.setString(11, minipig.getCode());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating minipig: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteByCode(String code) {
        String query = "DELETE FROM minipig WHERE code = ?";
        try (Connection cn = BDConecction.getConnection(driver,url, user, password);
             PreparedStatement stm = cn.prepareStatement(query)) {

            stm.setString(1, code);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting by code: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteByMicrochipId(String microchipId) {
        String query = "DELETE FROM minipig WHERE microchipId = ?";
        try (Connection cn = BDConecction.getConnection(driver,url, user, password);
             PreparedStatement stm = cn.prepareStatement(query)) {

            stm.setString(1, microchipId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting by microchipId: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> mapResultSetToMap(ResultSet resultSet) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        data.put("code", resultSet.getString("code"));
        data.put("name", resultSet.getString("name"));
        data.put("gender", resultSet.getString("gender"));
        data.put("microchipId", resultSet.getString("microchipId"));
        data.put("race", resultSet.getString("race"));
        data.put("color", resultSet.getString("color"));
        data.put("weight", resultSet.getDouble("weight"));
        data.put("height", resultSet.getDouble("height"));
        data.put("characteristic1", resultSet.getString("characteristic1"));
        data.put("characteristic2", resultSet.getString("characteristic2"));
        data.put("photo", resultSet.getString("photoUrl"));
        return data;
    }
}
