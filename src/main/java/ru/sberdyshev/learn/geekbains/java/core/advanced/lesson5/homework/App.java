package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson5.homework;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson5.homework.tools.ArrayTimeCounter;

public class App {
    public static void main(String[] args) {
        ArrayTimeCounter arrayTimeCounter = new ArrayTimeCounter();
        arrayTimeCounter.calculateDelayWithOneThread();
        System.out.println("Sleep start");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sleep end");
        arrayTimeCounter.calculateDelayWithTwoThreads();
    }
}
