package org.example.controller;

import org.example.model.Minipig;

import java.util.Map;
import java.util.Optional;

public class MinipigMapper {
    public Minipig mapMiniPig(Map<String, Object> data) {
        String code = (String) data.get("code");
        String name = (String) data.get("name");
        String gender = (String) data.get("gender");
        String microchipId = (String) data.get("microchipId");
        String race = (String) data.get("race");
        String color = (String) data.get("color");
        double weight = ((Number) data.get("weight")).doubleValue();
        double height = ((Number) data.get("height")).doubleValue();
        String characteristic1 = (String) data.get("characteristic1");
        String characteristic2 = (String) data.get("characteristic2");
        String photoUrl = (String) data.get("photo");

        return new Minipig(code, photoUrl, characteristic2, characteristic1,
                          height, weight, color, name, gender, race, microchipId);
    }

    public Map<String, Object> miniPigToMap(Minipig minipig) {
        return Map.ofEntries(
                Map.entry("code", Optional.ofNullable(minipig.getCode()).orElse("N/A")),
                Map.entry("name", Optional.ofNullable(minipig.getName()).orElse("Sin nombre")),
                Map.entry("gender", Optional.ofNullable(minipig.getGender()).orElse("No especificado")),
                Map.entry("microchipId", Optional.ofNullable(minipig.getMicrochipId()).orElse("Pendiente")),
                Map.entry("race", Optional.ofNullable(minipig.getRace()).orElse("Desconocida")),
                Map.entry("color", Optional.ofNullable(minipig.getColor()).orElse("N/A")),
                Map.entry("weight", Optional.of(minipig.getWeight()).orElse(0.0)),
                Map.entry("height", Optional.of(minipig.getHeight()).orElse(0.0)),
                Map.entry("characteristic1", Optional.ofNullable(minipig.getCharacteristic1()).orElse("")),
                Map.entry("characteristic2", Optional.ofNullable(minipig.getCharacteristic2()).orElse("")),
                Map.entry("photo", Optional.ofNullable(minipig.getPhotoUrl()).orElse("default-pig.png"))
        );
    }
}
