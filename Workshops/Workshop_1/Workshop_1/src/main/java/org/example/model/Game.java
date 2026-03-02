package org.example.model;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final List<Team> teams;
    private Map<String, Integer> score;
    private Map<BaleroOptions, Double> percentages;
    private String turno;
    private Integer[] attemptsPerTeam;
    private Integer[] pointsPerTeam;
    private long gameDurationSeconds;
    private final Suscriber suscriber;

    public Game(Suscriber s, List<Team> teams) {
        this.teams = teams;
        this.suscriber = s;
        this.attemptsPerTeam = new Integer[teams.size()];
        this.pointsPerTeam = new Integer[teams.size()];

        determinePercentages();
        createScoreMap();
    }

    public void setTime(int seconds){
        gameDurationSeconds = seconds;
    }

    public void startGame() {
        for(int i = 0; i < teams.size(); i++){
            actionTeam(teams.get(i), i);
        }
        endGame();
    }

    private void actionTeam(Team team, int teamIndex) {
        turno = team.getTeamName();
        List<Player> players = team.getPlayers();
        Integer attempts = 0;
        Integer points = 0;
        for(int j = 0; j < players.size(); j++){
            long startSeconds = System.currentTimeMillis() / 1000L;
            long endSeconds = startSeconds + (gameDurationSeconds / teams.size());
            long nextTickSeconds = startSeconds;

            while ((System.currentTimeMillis() / 1000L) <= endSeconds) {
                long nowSeconds = System.currentTimeMillis() / 1000L;
                if (nowSeconds >= nextTickSeconds) {
                    BaleroOptions result = getRandomBaleroOption();
                    nextTickSeconds += 2;
                    score.put(turno, score.get(turno) + result.getPoints());
                    if(result.getPoints() > 0) attempts++;
                    points += result.getPoints();
                    suscriber.notifyTurnChange(teamIndex, j, result, score);
                }
            }
        }
        this.pointsPerTeam[teamIndex] = points;
        this.attemptsPerTeam[teamIndex] = attempts;
    }

    private BaleroOptions getRandomBaleroOption() {
        Random random = new Random();
        double randomValue = random.nextDouble();

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
        Map<String, String> resultGame = new HashMap<>();
        Map<String,String> winners = determinateWinner();

        for(Map.Entry<String, Integer> entry : score.entrySet()) {
            String teamName = entry.getKey();
            String teamScore = String.valueOf(entry.getValue());

            String playersNames = teams.stream()
                    .filter(t -> t.getTeamName().equals(teamName))
                    .flatMap(t -> t.getPlayers().stream())
                    .map(Player::getName)
                    .collect(Collectors.joining(", "));
            String result = winners.getOrDefault(teamName, "L");

            resultGame.put(teamName, playersNames + ", " + teamScore + ", " + result);
        }
        this.suscriber.notifyEndGame(resultGame);
    }

    private Map<String,String> determinateWinner(){
        List<Integer> listPoints = Arrays.stream(this.pointsPerTeam).toList();
        int maxPoints = Collections.max(listPoints);

        List<Integer> winners = new ArrayList<>();
        for (int i = 0; i < listPoints.size(); i++) {
            if (listPoints.get(i) == maxPoints) {
                winners.add(i);
            }
        }

        if(winners.size() > 1) {
            int minAttempts = Integer.MAX_VALUE;
            for (Integer index : winners) {
                if (this.attemptsPerTeam[index] < minAttempts) {
                    minAttempts = this.attemptsPerTeam[index];
                }
            }

            List<Integer> finalWinners = new ArrayList<>();
            for (Integer index : winners) {
                if (attemptsPerTeam[index] == minAttempts) {
                    finalWinners.add(index);
                }
            }
            winners = finalWinners;
        }

        String result = winners.size() == 1 ? "W":"E";
        return winners.stream().collect(Collectors.toMap(
                index -> teams.get(index).getTeamName(),
                index -> result
        ));
    }
}
