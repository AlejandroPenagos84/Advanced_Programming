package org.example.model;

import java.util.Map;

public interface Suscriber {
    void notifyEndGame(Map<String, String> resultGame);
    void notifyTurnChange(int teamIndex, int playerIndex, BaleroOptions lastResult, Map<String, Integer> scores);
}
