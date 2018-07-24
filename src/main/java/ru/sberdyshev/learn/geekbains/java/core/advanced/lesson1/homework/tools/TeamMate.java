package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson1.homework.tools;

/**
 * @author sberdyshev
 */
public class TeamMate {
    private String name;
    private RaceResult state;

    public TeamMate(String name) {
        this.name = name;
        state = RaceResult.NOTSTARTED;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RaceResult getState() {
        return state;
    }

    public void setState(RaceResult state) {
        this.state = state;
    }

    public void showInfo() {
        System.out.println("Team member " + name + " has state \"" + state.getPrinatableVersion() + "\"");
    }
}
