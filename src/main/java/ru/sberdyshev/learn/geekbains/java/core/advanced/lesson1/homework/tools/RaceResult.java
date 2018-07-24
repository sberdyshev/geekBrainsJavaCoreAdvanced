package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson1.homework.tools;

import java.util.Random;

/**
 * @author sberdyshev
 */
public enum RaceResult {
    NOTSTARTED("has not started"), STARTED("started"), FINISHED("finished");
    private String prinatableVersion;

    RaceResult(String prinatableVersion) {
        this.prinatableVersion = prinatableVersion;
    }

    public static RaceResult getDefaultResult() {
        return RaceResult.NOTSTARTED;
    }

    public static RaceResult getRandomResult() {
        Random random = new Random();
        RaceResult[] raceResultArray = RaceResult.values();
        RaceResult result = raceResultArray[random.nextInt(raceResultArray.length)];
        return result;
    }

    public String getPrinatableVersion() {
        return prinatableVersion;
    }

}
