package org.example.model.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConnection implements IReader<Properties> {
    private final String filePath;

    public PropertiesConnection(String filePath) {
        this.filePath = filePath;
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

        try {
            InputStream inputStream;
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            }

            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();
            } else {
                throw new RuntimeException("File not found: " + filePath);
            }
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("Error loading properties file: " + e.getMessage(), e);
        }
        return properties;
    }
}
