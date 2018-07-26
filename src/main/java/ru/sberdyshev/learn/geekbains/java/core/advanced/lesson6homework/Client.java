package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson6homework;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson6homework.utils.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    final private int SERVER_PORT = 8189;
    final private String SERVER_ADDRESS = "127.0.0.1";
    private Socket socket;
    private boolean hasEnded = false;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Client has connected");
            final Scanner remoteScanner = new Scanner(socket.getInputStream());
            final PrintWriter remotePrintWriter = new PrintWriter(socket.getOutputStream(), true);
            final Scanner localScanner = new Scanner(System.in);
            final PrintWriter localPrintWriter = new PrintWriter(System.out, true);
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        String line = remoteScanner.nextLine();
                        if (hasEnded || Constants.END_COMMAND.equals(line)) {
                            break;
                        }
                        localPrintWriter.println("Echo: " + line);
                        localPrintWriter.flush();
                    }
                }
            }).start();
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        String line = localScanner.nextLine();
                        if (Constants.END_COMMAND.equals(line)) {
                            hasEnded = true;
                            remotePrintWriter.println(Constants.END_COMMAND);
                            break;
                        }
                        remotePrintWriter.println(line);
                        remotePrintWriter.flush();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
