package org.example.client;

import org.example.client.control.ControlSocket;
import org.example.shared.Fighter;
import org.example.shared.Kimarite;

import java.io.IOException;
import java.util.List;

public class LauncherClient {
    public static void main(String[] args) throws IOException {

        // --- Hakuho - Maestro del Yorikiri ---
        Fighter hakuho = new Fighter(
                "Hakuho",
                "Heavyweight",
                List.of(
                        new Kimarite("Yorikiri",   32),
                        new Kimarite("Uwatenage",   6),
                        new Kimarite("Oshidashi",  15),
                        new Kimarite("Hatakikomi",  8)
                )
        );

        // --- Asashoryu - Agresivo, muchos derribos ---
        Fighter asashoryu = new Fighter(
                "Asashoryu",
                "Heavyweight",
                List.of(
                        new Kimarite("Uwatenage",    6),
                        new Kimarite("Shitatenage",  4),
                        new Kimarite("Yorikiri",    32),
                        new Kimarite("Tsukiotoshi",  3)
                )
        );

        // --- Kakuryu - Especialista en torsiones ---
        Fighter kakuryu = new Fighter(
                "Kakuryu",
                "Middleweight",
                List.of(
                        new Kimarite("Katasukashi",  2),
                        new Kimarite("Tottari",      2),
                        new Kimarite("Hatakikomi",   8),
                        new Kimarite("Yorikiri",    32)
                )
        );

        // --- Terunofuji - Fuerza bruta, levantamientos ---
        Fighter terunofuji = new Fighter(
                "Terunofuji",
                "Heavyweight",
                List.of(
                        new Kimarite("Tsuridashi",   2),
                        new Kimarite("Yorikiri",    32),
                        new Kimarite("Uwatenage",    6),
                        new Kimarite("Yoritaoshi",   2)
                )
        );

        // --- Mainoumi - Tecnico, tropezones raros ---
        Fighter mainoumi = new Fighter(
                "Mainoumi",
                "Lightweight",
                List.of(
                        new Kimarite("Mitokorezeme", 1),
                        new Kimarite("Ketaguri",     1),
                        new Kimarite("Sotogake",     2),
                        new Kimarite("Hatakikomi",   8)
                )
        );

        // --- Oshidashi - Empujador puro ---
        Fighter chiyotaikai = new Fighter(
                "Chiyotaikai",
                "Middleweight",
                List.of(
                        new Kimarite("Oshidashi",   15),
                        new Kimarite("Tsukidashi",   3),
                        new Kimarite("Oshitaoshi",   4),
                        new Kimarite("Hatakikomi",   8)
                )
        );

        List<Fighter> fighters = List.of(
                hakuho, asashoryu, kakuryu,
                terunofuji, mainoumi, chiyotaikai
        );

        for (Fighter fighter : fighters) {
            new Thread(() -> {
                try {
                    new ControlSocket().connectServer(fighter);
                } catch (IOException _) {
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
