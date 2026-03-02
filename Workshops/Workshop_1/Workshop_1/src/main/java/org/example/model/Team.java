package org.example.model;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable {

    private String curriculumProject;
    private String teamName;
    private List<Player> players;

    public Team(String curriculumProject, String teamName, List<Player> players) {
        this.curriculumProject = curriculumProject;
        this.teamName = teamName;
        this.players = players;
    }

    public String getCurriculumProject() {
        return curriculumProject;
    }

    public void setCurriculumProject(String curriculumProject) {
        this.curriculumProject = curriculumProject;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
