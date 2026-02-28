package org.example.model.connection;

import java.util.Properties;

public class PropertiesConnection implements IReader<Properties> {
    private final String filePath;

    public PropertiesConnection(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Lee y devuelve el archivo Properties sin parsear
     * @return Properties cargadas desde el archivo
     */
    @Override
    public Properties read() {
        Properties properties = new Properties();
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("File not found: " + filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading properties file: " + e.getMessage(), e);
        }
        return properties;
    }
}
