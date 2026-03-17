package org.example.controler;

import org.example.model.Player;
import org.example.model.Team;

import java.util.List;

public class TeamGridMapper {

    public String[][][] buildGridData(List<Team> teams) {
        String[][][] data = new String[teams.size()][][];

        for (int t = 0; t < teams.size(); t++) {
            Team team = teams.get(t);
            List<Player> players = team.getPlayers();
            data[t] = new String[players.size() + 1][2];

            data[t][0] = new String[]{team.getTeamName(), "0"};

            for (int p = 0; p < players.size(); p++) {
                data[t][p + 1] = new String[]{players.get(p).getName(), ""};
            }
        }

        return data;
    }
}

