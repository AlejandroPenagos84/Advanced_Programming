package org.example.model.DAO;

import org.example.model.Minipig;

import java.util.List;

public interface MinipigDAO {
    Minipig findByCode(String code);
    List<Minipig> findAll();
    List<Minipig> findByParameter(String parameter, String value);
    void save(Minipig minipig);
    void update(Minipig minipig);
    void deleteByCode(String code);
    void deleteByMicrochipId(String microchipId);
}
