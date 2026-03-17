package org.example.model.connection;

import java.io.*;

public class SerializedFile implements IWritter<Object>, IReader<File> {
    private final String filePath;

    public SerializedFile(String filePath) {
        this.filePath = new File(filePath).getAbsolutePath();
    }

    @Override
    public void write(Object data) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File read() {
        return new File(filePath);
    }
}
