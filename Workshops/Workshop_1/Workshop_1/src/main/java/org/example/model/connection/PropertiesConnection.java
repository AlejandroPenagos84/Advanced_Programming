package org.example.model.connection;

import java.util.*;
import java.util.stream.Collectors;

public class PropertiesConnection implements IReader{
    private Properties properties;

    public PropertiesConnection() {
    }

    public PropertiesConnection(String filePath) {
            properties = new Properties();
            try (var inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
                if (inputStream != null) {
                    properties.load(inputStream);
                } else {
                    throw new RuntimeException("File not found: " + filePath);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error loading properties file: " + e.getMessage(), e);
            }
    }

    /**
     * This method is used to read data from a properties file and return it as a Map.
     * The keys of the Map are Lists of Strings(Project Curriculum and Team's name),
     * and the values are the players
     * */

    @Override
    public Map<List<String>,List<List<String>>> read() {
        Map<List<String>,List<List<String>>> dataFile = new HashMap<>();

        System.out.println(properties.stringPropertyNames());

        Set<String> ids = properties.stringPropertyNames().
                stream().map(c -> c.split("\\.")[1]).collect(Collectors.toSet());

        System.out.println(ids);
        for(String id: ids){

            String curriculumProject = properties.getProperty("team." + id + ".curriculumProject");
            String teamName = properties.getProperty("team." + id + ".teamName");
            List<String> key = Arrays.asList(curriculumProject, teamName);

            String playersString = properties.getProperty("team." + id + ".players");

            String [] playersArray = playersString.split(";");

            for(String player: playersArray){
                String [] attributes = player.split(",");
                if(attributes.length != 2) {
                    throw new RuntimeException("Invalid player format for player: " + player);
                }

                List<String> playerData = Arrays.asList(attributes[0].trim(), attributes[1].trim());
                dataFile.computeIfAbsent(key, k -> new ArrayList<>()).add(playerData);
            }
        }
        return dataFile;
    }
}
