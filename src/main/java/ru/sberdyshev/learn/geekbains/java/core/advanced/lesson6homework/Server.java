package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson6homework;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson6homework.utils.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    final private int PORT = 8189;
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean hasEnded = false;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server has started");
            socket = serverSocket.accept();
            System.out.println("Client has connected");
            final Scanner remoteScanner = new Scanner(socket.getInputStream());
            final PrintWriter remotePrintWriter = new PrintWriter(socket.getOutputStream());
            final Scanner localScanner = new Scanner(System.in);
            final PrintWriter localPrintWriter = new PrintWriter(System.out);
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        String line = remoteScanner.nextLine();
                        if (hasEnded || Constants.END_COMMAND.equals(line)) {
                            break;
                        }
                        localPrintWriter.println("Echo: " + line);
                        localPrintWriter.flush();;
                    }
                }
            }).start();
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        String line = localScanner.nextLine();
                        if (Constants.END_COMMAND.equals(line)) {
                            remotePrintWriter.println(Constants.END_COMMAND);
                            hasEnded = true;
                            break;
                        }
                        remotePrintWriter.println(line);
                        remotePrintWriter.flush();
                    }
                }
            }).start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
