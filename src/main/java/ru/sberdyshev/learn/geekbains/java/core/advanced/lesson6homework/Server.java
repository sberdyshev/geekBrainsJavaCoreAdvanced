package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson6homework;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    final private int PORT = 8189;

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server has started");
            socket = serverSocket.accept();
            System.out.println("Client has connected");
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            while(true) {
                String line = scanner.nextLine();
                if ("end".equals(line)) {
                    break;
                }
                printWriter.print("Echo: " + line);
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
