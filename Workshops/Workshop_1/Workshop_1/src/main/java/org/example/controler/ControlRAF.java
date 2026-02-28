package org.example.controler;

import org.example.model.connection.AccessFileConnection;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ControlRAF {
    private final AccessFileConnection fileConnection;
    private final int RECORD_FILE = 600;

    public ControlRAF(String filePath) {
        this.fileConnection = new AccessFileConnection(filePath);
    }

    /**
     * Procesa el mapa de resultados y escribe en el archivo
     * @param resultGame Map con los resultados del juego
     */
    public void write(Map<String, String> resultGame) {
        try (RandomAccessFile file = new RandomAccessFile("score.dat", "rw")) {
            int currentKey = getLastKey(file) + 1;

            for (Map.Entry<String, String> entry : resultGame.entrySet()) {
                String teamName = entry.getKey();
                String teamResult = entry.getValue();

                // Construir la línea completa con formato
                String data = currentKey + "," + teamName + "," + teamResult;
                String paddedRegister = padString(data, RECORD_FILE);

                // Pasar al AccessFileConnection para escribir
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
                String keyStr = parts[0].trim();
                return Integer.parseInt(keyStr);
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

