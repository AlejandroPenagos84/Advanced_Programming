package org.example.controler;

import org.example.model.Team;
import org.example.model.connection.SerializedFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ControlSerializedFile {
    private final SerializedFile serializedFile;

    public ControlSerializedFile(String filePath) {
        this.serializedFile = new SerializedFile(filePath);
    }

    /**
     * Lee el archivo serializado y devuelve una lista de equipos parseados
     * @return List<Team> con los equipos deserializados
     */
    public List<Team> read() {
        File file = serializedFile.read();
        return parseTeams(file);
    }

    /**
     * Deserializa el archivo y parsea los equipos
     * @param file archivo serializado
     * @return List de Teams deserializados
     */
    private List<Team> parseTeams(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                return (List<Team>) obj;
            } else if (obj instanceof Team) {
                List<Team> teams = new ArrayList<>();
                teams.add((Team) obj);
                return teams;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error al leer el archivo serializado: " + e.getMessage(), e);
        }
        return List.of();
    }

    /**
     * Escribe una lista de equipos en el archivo serializado
     * @param teams lista de equipos a serializar
     */
    public void write(List<Team> teams) {
        serializedFile.write(teams);
    }
}
