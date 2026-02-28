package org.example.model.connection;

import java.io.IOException;
import java.io.RandomAccessFile;

public class AccessFileConnection implements IWritter<String> {
    private final String filePath;

    public AccessFileConnection(String filePath){
        this.filePath = filePath;
    }

    /**
     * Escribe un string en el archivo
     * @param data String a escribir en el archivo
     */
    @Override
    public void write(String data) {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            long fileLength = file.length();
            file.seek(fileLength);
            file.writeBytes(data);
            file.writeBytes("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
