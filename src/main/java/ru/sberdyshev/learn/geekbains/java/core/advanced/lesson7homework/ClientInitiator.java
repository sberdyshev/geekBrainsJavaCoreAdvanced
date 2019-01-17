package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.Client;

/**
 * @author SBerdyshev
 */
public class ClientInitiator {
    public static void main(String[] args) {
        //TODO check args, get args from String[] args
        Client client = new Client("127.0.0.1",8989);
        client.start();
    }
}
