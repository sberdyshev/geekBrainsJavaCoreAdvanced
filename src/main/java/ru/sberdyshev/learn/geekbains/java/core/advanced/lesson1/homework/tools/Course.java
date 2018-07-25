package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson1.homework.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sberdyshev
 */
public class Course {
    private List<String> obstacles;

    public Course() {
    }

    public Course(List<String> obstacles) {

        this.obstacles = obstacles;
    }

    public List<String> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<String> obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt(Team team) {
        //генерим лист результатов с количеством элементов, равным количеству людей в команде
        List<RaceResult> raceResults = new ArrayList<RaceResult>();
        for (int i = 0; i < team.getSize(); i++) {
            raceResults.add(RaceResult.getRandomResult());
        }
        team.setResults(raceResults);
    }
}
