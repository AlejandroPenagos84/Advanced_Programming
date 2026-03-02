package org.example.model.connection;

import java.io.*;

public class SerializedFile implements IWritter<Object>, IReader<File> {
    private final String filePath;

    public SerializedFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Serializa y escribe un objeto en el archivo
     * @param data objeto a escribir
     */
    @Override
    public void write(Object data) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Devuelve el File asociado al archivo serializado
     * @return File del archivo
     */
    @Override
    public File read() {
        return new File(filePath);
    }
}
