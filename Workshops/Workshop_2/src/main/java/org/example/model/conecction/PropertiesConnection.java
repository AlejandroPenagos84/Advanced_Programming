package org.example.model.conecction;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesConnection implements IReader<Properties> {
    private final String filePath;

    public PropertiesConnection(String filePath) {
        this.filePath = new File(filePath).getAbsolutePath();
    }

    /**
     * Lee y devuelve el archivo Properties sin parsear.
     * Intenta primero desde el filesystem, si no existe busca en el classpath.
     * @return Properties cargadas desde el archivo
     */
    @Override
    public Properties read() {
        Properties properties = new Properties();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Error loading properties file: " + e.getMessage(), e);
        }
        return properties;
    }
}
