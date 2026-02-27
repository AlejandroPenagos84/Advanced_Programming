package org.example;

import org.example.controler.Controller;
import org.example.model.Game;
import org.example.model.connection.IReader;
import org.example.model.connection.PropertiesConnection;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        IReader readerProperties = new PropertiesConnection("teams.properties");
        System.out.println(readerProperties);
        new Controller(readerProperties);
    }
}
