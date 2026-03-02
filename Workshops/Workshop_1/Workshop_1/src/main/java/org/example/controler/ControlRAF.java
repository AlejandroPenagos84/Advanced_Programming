package org.example.controler;

import org.example.model.connection.AccessFileConnection;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControlRAF {
    private final AccessFileConnection fileConnection;
    private final int RECORD_FILE = 600;

    public ControlRAF(AccessFileConnection fileConnection) {
        this.fileConnection = fileConnection;
    }

    /**
     * Lee el archivo y devuelve las líneas donde el equipo ganó (W).
     * @param teamName nombre del equipo a buscar
     * @return List<String> con las líneas que coinciden
     */
    public List<String> readTeams(String teamName) {
        try (RandomAccessFile file = fileConnection.read()) {
            long fileLength = file.length();
            if (fileLength == 0) return new ArrayList<>();

            byte[] bytes = new byte[(int) fileLength];
            file.readFully(bytes);
            String content = new String(bytes, StandardCharsets.UTF_8);
            String[] lines = content.split("\n");

            List<String> teamLines = new ArrayList<>();

            for (String team : lines) {
                String trimmed = team.trim();
                if (trimmed.isEmpty()) continue;

                String[] parts = trimmed.split(",");
                if (parts.length >= 3
                        && parts[1].trim().equals(teamName)
                        && parts[parts.length - 1].trim().equals("W")) {
                    teamLines.add(trimmed);
                }
            }

            return teamLines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Procesa el mapa de resultados y escribe en el archivo
     * @param resultGame Map con los resultados del juego
     */
    public void write(Map<String, String> resultGame) {
        try (RandomAccessFile file = fileConnection.read()) {
            int currentKey = getLastKey(file) + 1;

            for (Map.Entry<String, String> entry : resultGame.entrySet()) {
                String teamName = entry.getKey();
                String teamResult = entry.getValue();

                String data = currentKey + "," + teamName + "," + teamResult;
                String paddedRegister = padString(data, RECORD_FILE);

                fileConnection.write(paddedRegister);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lee el último registro del archivo
     */
    private String readLastRegister(RandomAccessFile file) {
        try {
            long fileLength = file.length();

            if (fileLength > 0) {
                int RECORD_WITH_NEWLINE = RECORD_FILE + 1;
                long lastRecordPosition = fileLength - RECORD_WITH_NEWLINE;

                if (lastRecordPosition < 0) {
                    lastRecordPosition = 0;
                }

                file.seek(lastRecordPosition);

                byte[] buffer = new byte[RECORD_FILE];
                int bytesRead = file.read(buffer);

                if (bytesRead > 0) {
                    return new String(buffer, 0, bytesRead, StandardCharsets.UTF_8).trim();
                }
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene la última clave del archivo
     */
    private int getLastKey(RandomAccessFile file) {
        String lastRegister = readLastRegister(file);

        if (lastRegister.trim().isEmpty()) {
            return 0;
        }

        String[] parts = lastRegister.split(",");
        if (parts.length > 0) {
            try {
                return Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Rellena o trunca el string para que tenga exactamente el tamaño especificado
     */
    private String padString(String str, int length) {
        if (str == null) {
            str = "";
        }

        if (str.length() > length) {
            return str.substring(0, length);
        }

        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < length) {
            sb.append(" ");
        }

        return sb.toString();
    }
}

