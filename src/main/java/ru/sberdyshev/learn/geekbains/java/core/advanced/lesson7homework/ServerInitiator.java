package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.server.Server;

/**
 * @author SBerdyshev
 */
public class ServerInitiator {
    public static void main(String[] args) {
        //TODO check args, get args from String[] args
        Server server = new Server(8989);
        server.start();
    }
}
