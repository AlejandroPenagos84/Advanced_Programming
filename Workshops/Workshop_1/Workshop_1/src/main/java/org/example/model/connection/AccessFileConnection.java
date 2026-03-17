package org.example.model.connection;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AccessFileConnection implements IWritter<String>, IReader<RandomAccessFile> {
    private final String filePath;

    public AccessFileConnection(String filePath){
        this.filePath = new File(filePath).getAbsolutePath();
    }

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

    @Override
    public RandomAccessFile read() {
        try {
            return new RandomAccessFile(filePath, "rw");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
