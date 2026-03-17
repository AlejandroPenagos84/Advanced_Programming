package org.example.controler;

import org.example.model.Team;
import org.example.model.connection.AccessFileConnection;
import org.example.model.connection.PropertiesConnection;
import org.example.model.connection.SerializedFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GestorArchivos {

    private static final int RECORD_SIZE         = 600;
    private static final int RECORD_SIZE_NEWLINE  = RECORD_SIZE + 1;

    private final GestorEquipo          gestorEquipo;
    private final AccessFileConnection  accessFileConnection;
    private SerializedFile              serializedFile;

    public GestorArchivos() {
        this.gestorEquipo         = new GestorEquipo();
        this.accessFileConnection = new AccessFileConnection("score.dat");
        this.serializedFile       = new SerializedFile("teams.ser");
    }

    // ── Properties ────────────────────────────────────────────────────────────

    public List<Team> loadTeamsFromProperties(String path) {
        Properties props = new PropertiesConnection(path).read();
        return gestorEquipo.parseTeamsFromProperties(props);
    }

    // ── Serialized ────────────────────────────────────────────────────────────

    public List<Team> loadTeamsFromSerialized(String path) {
        this.serializedFile = new SerializedFile(path);
        File file = serializedFile.read();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return gestorEquipo.parseTeamsFromSerialized(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error al leer el archivo serializado: " + e.getMessage(), e);
        }
    }

    public void saveTeamsSerialized(List<Team> teams) {
        serializedFile.write(teams);
    }

    // ── Random Access File ────────────────────────────────────────────────────

    public List<String> readWinsByTeam(String teamName) {
        try (RandomAccessFile file = accessFileConnection.read()) {
            if (file.length() == 0) return new ArrayList<>();
            return filterWinLines(readAllLines(file), teamName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveGameResults(Map<String, String> resultGame) {
        try (RandomAccessFile file = accessFileConnection.read()) {
            int nextKey = getLastKey(file) + 1;
            for (Map.Entry<String, String> entry : resultGame.entrySet()) {
                String record = nextKey + "," + entry.getKey() + "," + entry.getValue();
                accessFileConnection.write(padToSize(record, RECORD_SIZE));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ── Helpers RAF ───────────────────────────────────────────────────────────

    private String[] readAllLines(RandomAccessFile file) throws IOException {
        byte[] bytes = new byte[(int) file.length()];
        file.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8).split("\n");
    }

    private List<String> filterWinLines(String[] lines, String teamName) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] parts = trimmed.split(",");
            if (parts.length >= 3
                    && parts[1].trim().equals(teamName)
                    && parts[parts.length - 1].trim().equals("W")) {
                result.add(trimmed);
            }
        }
        return result;
    }

    private int getLastKey(RandomAccessFile file) {
        String last = readLastRecord(file);
        if (last.isBlank()) return 0;
        try {
            return Integer.parseInt(last.split(",")[0].trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String readLastRecord(RandomAccessFile file) {
        try {
            long length = file.length();
            if (length == 0) return "";
            long pos = Math.max(0, length - RECORD_SIZE_NEWLINE);
            file.seek(pos);
            byte[] buffer = new byte[RECORD_SIZE];
            int read = file.read(buffer);
            return read > 0 ? new String(buffer, 0, read, StandardCharsets.UTF_8).trim() : "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String padToSize(String str, int size) {
        if (str == null) str = "";
        if (str.length() >= size) return str.substring(0, size);
        return String.format("%-" + size + "s", str);
    }
}