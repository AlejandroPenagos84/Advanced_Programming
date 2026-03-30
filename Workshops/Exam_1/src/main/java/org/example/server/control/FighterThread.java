package org.example.server.control;

import org.example.shared.Fighter;
import org.example.shared.Kimarite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class FighterThread implements Runnable {
    private final Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Fighter fighter;
    private Dohyo dohyo;
    private FighterThread rival;

    private int percentageWinning = 0;
    private boolean winner = false;

    public FighterThread(Socket clientSocket, Dohyo dohyo) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.dohyo = dohyo;
        createFighter();
    }

    private void createFighter() throws IOException, ClassNotFoundException {
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(clientSocket.getInputStream());

        fighter = (Fighter) in.readObject();
        System.out.println("Luchador conectado: " + fighter.name());
    }

    @Override
    public void run() {
        while (!dohyo.isMatchOver()) {
            boolean finished = dohyo.doTechnique(this);

            if (finished) {
                break;
            }

            try {
                Thread.sleep(new Random().nextInt(300));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        sendResult();
    }

    private void sendResult() {
        try {
            if (winner) {
                out.writeObject("Ganaste");
            } else {
                out.writeObject("Perdiste");
            }
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Kimarite selectTechnique() {
        if (fighter.techniques() == null || fighter.techniques().isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(fighter.techniques().size());
        return fighter.techniques().get(randomIndex);
    }

    public synchronized void addPercentageWinning(int value) {
        this.percentageWinning += value;
    }

    public synchronized int getPercentageWinning() {
        return percentageWinning;
    }

    public synchronized void resetForFight() {
        this.percentageWinning = 0;
        this.winner = false;
    }

    public Fighter getFighter() {
        return fighter;
    }

    public FighterThread getRival() {
        return rival;
    }

    public void setRival(FighterThread rival) {
        this.rival = rival;
    }

    public void setDohyo(Dohyo dohyo) {
        this.dohyo = dohyo;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}