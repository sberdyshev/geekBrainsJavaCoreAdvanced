package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson6homework;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    final private int SERVER_PORT = 8189;
    final private String SERVER_ADDRESS = "127.0.0.1";

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Client has connected");
            final Scanner remoteScanner = new Scanner(socket.getInputStream());
            final PrintWriter remotePrintWriter = new PrintWriter(socket.getOutputStream());
            final Scanner localScanner = new Scanner(System.in);
            final PrintWriter localPrintWriter = new PrintWriter(System.out);
            new Thread(new Runnable() {
                public void run() {
                    while(true) {
                        String line = remoteScanner.nextLine();
                        if ("end".equals(line)) {
                            break;
                        }
                        localPrintWriter.print("Echo: " + line);
                        localPrintWriter.flush();
                    }
                }
            }).start();


            new Thread(new Runnable() {
                public void run() {
                    while(true) {
                        String line = localScanner.nextLine();
                        if ("end".equals(line)) {
                            break;
                        }
                        remotePrintWriter.print("Echo: " + line);
                        remotePrintWriter.flush();
                    }
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
