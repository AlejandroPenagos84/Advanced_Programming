package org.example.server.control;

import org.example.shared.Kimarite;

public class Dohyo {
    private static final int UMBRAL = 75;

    private boolean matchOver = false;
    private FighterThread winner;

    public synchronized boolean doTechnique(FighterThread fighterThread) {
        if (matchOver) {
            return true;
        }

        Kimarite current = fighterThread.selectTechnique();
        if (current == null) {
            return false;
        }

        fighterThread.addPercentageWinning(current.probability());

        System.out.println(fighterThread.getFighter().name() + " ha seleccionado "
                + current.name() + " -> " + fighterThread.getPercentageWinning() + "%");

        System.out.println(fighterThread.getFighter().name() + " vs "
                + fighterThread.getRival().getFighter().name());

        System.out.println(fighterThread.getPercentageWinning() + "% vs "
                + fighterThread.getRival().getPercentageWinning() + "%");

        System.out.println("---------------------------------------------");

        if (fighterThread.getPercentageWinning() >= UMBRAL
                && fighterThread.getPercentageWinning() >= fighterThread.getRival().getPercentageWinning()) {

            matchOver = true;
            winner = fighterThread;
            fighterThread.setWinner(true);

            return true;
        }

        return false;
    }

    public synchronized boolean isMatchOver() {
        return matchOver;
    }

    public synchronized FighterThread getWinner() {
        return winner;
    }

    public synchronized void resetMatch() {
        matchOver = false;
        winner = null;
    }
}