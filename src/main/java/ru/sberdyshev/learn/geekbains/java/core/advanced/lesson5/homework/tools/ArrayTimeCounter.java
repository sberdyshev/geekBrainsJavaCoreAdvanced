package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson5.homework.tools;

public class ArrayTimeCounter {

    private static final int SIZE = 10000000;
    private float[] array = new float[SIZE];

    public ArrayTimeCounter() {
        for (int i = 0; i < SIZE; i++) {
            array[i] = 1;
        }
    }

    public void calculateDelayWithOneThread() {
        System.out.println("Testing one thread processing");
        long a = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println(System.currentTimeMillis() - a);
        System.out.println("Testing one thread processing end");
    }

    public void calculateDelayWithTwoThreads() {
        System.out.println("Testing two thread processing");
        final long startTime = System.currentTimeMillis();
        final int halfSize = SIZE/2;
        final float[] tempArrayFirstPart = new float[halfSize];
        final float[] tempArraySecondPart = new float[halfSize];
        System.arraycopy(array, 0, tempArrayFirstPart, 0, halfSize);
        System.arraycopy(array, halfSize, tempArraySecondPart, 0, halfSize);

        Thread t1 = new Thread() {
            public void run() {
                for (int i = 0; i < halfSize; i++) {
                    tempArrayFirstPart[i] = (float) (tempArrayFirstPart[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                for (int i = 0; i < halfSize; i++) {
                    tempArraySecondPart[i] = (float) (tempArraySecondPart[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        };
        t1.start();
        t2.start();

        System.arraycopy(tempArrayFirstPart, 0, array, 0, halfSize);
        System.arraycopy(tempArraySecondPart, 0, array, halfSize, halfSize);

        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println("Testing two thread processing end");
    }
}
