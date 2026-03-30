package org.example.client.control;

import org.example.shared.Fighter;

import java.io.*;
import java.net.Socket;

public class ControlSocket {
    private Socket socket;
    private String HOST = "localhost";
    private int PORT = 9090;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ControlSocket() throws IOException {
        socket = new Socket(HOST,PORT);
        System.out.println("Conectando con...");
    }

    public void connectServer(Fighter fighter) throws IOException, ClassNotFoundException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        in = new ObjectInputStream(socket.getInputStream());

        out.writeObject(fighter);
        out.flush();

        String response = (String) in.readObject();
        System.out.println("Respuesta del server: " + response);
    }
}
