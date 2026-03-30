package org.example.client.control;

import org.example.shared.Fighter;
import org.example.shared.Kimarite;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControlFighter {

    public static Fighter toFighter(Map<String, String> data, List<List<String>> rawTechniques) {
        String name   = data.get("name");
        String weight = data.get("weight");
        List<Kimarite> techniques = toKimariteList(rawTechniques);

        return new Fighter(name, weight, techniques);
    }

    private static List<Kimarite> toKimariteList(List<List<String>> rawTechniques) {
        return rawTechniques.stream()
                .map(ControlFighter::toKimarite)
                .collect(Collectors.toList());
    }

    private static Kimarite toKimarite(List<String> raw) {
        if (raw.size() < 2) throw new IllegalArgumentException("La tecnica necesita nombre y probabilidad");

        String name        = raw.get(0);
        int    probability = Integer.parseInt(raw.get(1));

        return new Kimarite(name, probability);
    }
}