package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson1.homework.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author sberdyshev
 */
public class Team {
    private static final String DEFAULT_TEAM_NAME = "default team";

    //Что делать с жестью ниже? (Если нужен список имен по умолчанию)
    private static final String DEFAULT_TEAMMATE_NAME_1 = "Anton";
    private static final String DEFAULT_TEAMMATE_NAME_2 = "Nick";
    private static final String DEFAULT_TEAMMATE_NAME_3 = "Mike";
    private static final String DEFAULT_TEAMMATE_NAME_4 = "Andrew";

    private String name;
    private List<TeamMate> team;

    public Team() {
        this.name = DEFAULT_TEAM_NAME;
        generateTeamMembers();
    }

    public Team(String name) {
        this.name = name;
        generateTeamMembers();
    }

    public Team(List<TeamMate> team) {
        this.name = DEFAULT_TEAM_NAME;
        this.team = team;
    }

    public Team(String name, List<TeamMate> team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showTeamMates(RaceResult result) {
        System.out.println("Showing teammates of team \"" + name + "\" with state \"" + result.getPrinatableVersion() + "\"");
        for (Iterator<TeamMate> tm = team.iterator(); tm.hasNext(); ) {
            TeamMate teamMate = tm.next();
            if (teamMate.getState() == result) {
                teamMate.showInfo();
            }
        }
    }

    public void showTeamMates() {
        System.out.println("Showing all teammates of team \"" + name + "\"");
        for (Iterator<TeamMate> tm = team.iterator(); tm.hasNext(); ) {
            TeamMate teamMate = tm.next();
            teamMate.showInfo();
        }
    }

    public void setResults(List<RaceResult> results) {
        Iterator<RaceResult> raceResultIterator = results.iterator();
        Iterator<TeamMate> teamMateIterator = team.iterator();
        //записываем результаты из входящего списка каждому члену команды по очереди.
        //Если список результатов коничился раньше списка членов команды, то заполняем дефолтными значениями
        while (teamMateIterator.hasNext()) {
            RaceResult result;
            if (raceResultIterator.hasNext()) {
                result = raceResultIterator.next();
            } else {
                result = RaceResult.getDefaultResult();
            }
            TeamMate teamMate = teamMateIterator.next();
            teamMate.setState(result);
        }
    }

    public int getSize() {
        return team.size();
    }

    private void generateTeamMembers() {
        team = new ArrayList<TeamMate>();
        team.add(new TeamMate(DEFAULT_TEAMMATE_NAME_1));
        team.add(new TeamMate(DEFAULT_TEAMMATE_NAME_2));
        team.add(new TeamMate(DEFAULT_TEAMMATE_NAME_3));
        team.add(new TeamMate(DEFAULT_TEAMMATE_NAME_4));
    }
}
