package org.example.model;

import java.util.*;

public class Game {
    private List<Team> teams;
    private String winnerTeam;
    private Map<String, Integer> score;
    private Map<BaleroOptions, Double> percentages;
    private String turno;
    private int intentos;
    private long gameDurationSeconds;

    public Game(List<Team> teams) {
        this.teams = teams;
        determinePercentages();
        createScoreMap();
    }

    public void setTime(int seconds){
        gameDurationSeconds = seconds;
    }

    public void startGame() {
        for(Team t: teams){
            actionTeam(t);
        }
        endGame();
    }

    private void actionTeam(Team team) {
        turno = team.getTeamName();
        for(Player p : team.getPlayers()){
            intentos = 0;
            long startSeconds = System.currentTimeMillis() / 1000L;
            long endSeconds = startSeconds + (gameDurationSeconds / teams.size());
            long nextTickSeconds = startSeconds;

            while ((System.currentTimeMillis() / 1000L) <= endSeconds) {
                long nowSeconds = System.currentTimeMillis() / 1000L;
                if (nowSeconds >= nextTickSeconds) {
                    BaleroOptions result = getRandomBaleroOption();
                    nextTickSeconds += 2;
                    score.put(turno, score.get(turno) + result.getPoints());
                    intentos++;
                    System.out.println("Resultado para el equipo " + team.getTeamName() + " y jugador " + p.getName() + ": " + result);
                    System.out.println("Intentos: " + intentos);
                }
            }
        }
    }

    private BaleroOptions getRandomBaleroOption() {
        Random random = new Random();
        double randomValue = random.nextDouble(); // Genera número entre 0.0 y 1.0

        double cumulativeProbability = 0.0;

        for (Map.Entry<BaleroOptions, Double> entry : percentages.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomValue <= cumulativeProbability) {
                return entry.getKey();
            }
        }

        return BaleroOptions.NONE;
    }

    private void determinePercentages() {
        percentages = new HashMap<>();

        percentages.put(BaleroOptions.NONE, 0.40);           // 0 puntos - 40% - Más probable
        percentages.put(BaleroOptions.SIMPLE, 0.25);         // 2 puntos - 25%
        percentages.put(BaleroOptions.VERTICAL, 0.15);       // 3 puntos - 15%
        percentages.put(BaleroOptions.MARIQUITA, 0.10);      // 4 puntos - 10%
        percentages.put(BaleroOptions.PUÑALADA, 0.05);       // 5 puntos - 5%
        percentages.put(BaleroOptions.PURTIÑA, 0.03);        // 6 puntos - 3%
        percentages.put(BaleroOptions.DOMINIO_REVES, 0.01);  // 8 puntos - 1%
        percentages.put(BaleroOptions.DOBLE, 0.01);          // 10 puntos - 1% - Menos probable
    }

    private void createScoreMap() {
        score = new HashMap<>();
        for(Team t: teams){
            score.put(t.getTeamName(), 0);
        }
    }

    private void endGame() {
        System.out.println("Juego terminado");
        System.out.println("Resultados finales:");
        for(Map.Entry<String, Integer> entry : score.entrySet()) {
            System.out.println("Equipo: " + entry.getKey() + " - Puntos: " + entry.getValue());
        }
    }
}
