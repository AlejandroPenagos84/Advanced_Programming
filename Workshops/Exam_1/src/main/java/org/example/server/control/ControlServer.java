package org.example.server.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class ControlServer {
    private final Stack<FighterThread> fightersThreads = new Stack<>();
    private final Dohyo dohyo;

    public ControlServer() throws IOException, ClassNotFoundException {
        dohyo = new Dohyo();
        start();
        organizeFights();
    }

    public void start() throws IOException {
        int PORT = 9090;
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Dohyo is ready to accept connections on port " + PORT);

        while (fightersThreads.size() < 6) {
            try {
                Socket clientSocket = serverSocket.accept();
                FighterThread fighterThread = new FighterThread(clientSocket, dohyo);
                fightersThreads.push(fighterThread);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void organizeFights() {
        while (fightersThreads.size() > 1) {
            FighterThread fighterOne = fightersThreads.pop();
            FighterThread fighterTwo = fightersThreads.pop();

            fighterOne.resetForFight();
            fighterTwo.resetForFight();

            dohyo.resetMatch();

            fighterOne.setRival(fighterTwo);
            fighterTwo.setRival(fighterOne);

            Thread t1 = new Thread(fighterOne);
            Thread t2 = new Thread(fighterTwo);

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("La pelea fue interrumpida", e);
            }

            FighterThread winner = dohyo.getWinner();

            if (winner == null) {
                throw new IllegalStateException("La pelea terminó sin ganador");
            }

            System.out.println("Ganador de la ronda: " + winner.getFighter().name());
            fightersThreads.push(winner);
        }

        FighterThread champion = fightersThreads.pop();
        System.out.println("CAMPEON: " + champion.getFighter().name());
    }
}

