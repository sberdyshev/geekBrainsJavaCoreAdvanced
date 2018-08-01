package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.server;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.server.ui.ServerMainUI;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils.ChatClient;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils.Constants;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SBerdyshev
 */
public class Server {
    private ServerMainUI serverMainUI;
    private ServerSocket serverSocket;
    private Socket socket;
    private int port;
    private Map<String, ChatClient> chatClientMap;

    public Server(int port) {
        this.port = port;
        chatClientMap = new HashMap<String, ChatClient>();
        serverMainUI = new ServerMainUI();
    }

    public void start() {
        serverMainUI.setVisible(true);
        try {
            serverSocket = new ServerSocket(port);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            socket = serverSocket.accept();
                            final InputStream inputStream = socket.getInputStream();
                            final OutputStream outputStream = socket.getOutputStream();
                            final Scanner remoteScanner = new Scanner(inputStream);
                            final PrintWriter remotePrintWriter = new PrintWriter(outputStream);
                            new Thread(new Runnable() {
                                public void run() {
                                    processMessagesOnServer(remoteScanner, remotePrintWriter, inputStream, outputStream);
                                }
                            }).start();
                        } catch (IOException e) {
                            //TODO handle
                            e.printStackTrace();
                        }

                    }
                }
            }).start();
        } catch (IOException e) {
            //TODO handle
            e.printStackTrace();
        }
    }

    private void processMessagesOnServer(final Scanner remoteScanner, final PrintWriter remotePrintWriter, final InputStream inputStream, final OutputStream outputStream) {
        currentClient:
        while (true) {
            final String recievedMessage = remoteScanner.nextLine();
            final boolean isAuthCommand = recievedMessage.contains(Constants.COMMAND_AUTH);
            final boolean hasSourceUser = recievedMessage.contains(Constants.COMMAND_SOURCE_USER_START);
            final boolean isBroadCastMessage = recievedMessage.contains(Constants.COMMAND_BROADCAST_MESSAGE_START);
            final boolean isPrivateMessage = recievedMessage.contains(Constants.COMMAND_PRIVATE_MESSAGE_START);
            final boolean isClientDisconnected = recievedMessage.contains(Constants.COMMAND_CLIENT_DISCONNECTED);


            if (isAuthCommand && hasSourceUser) {
                //contains user name
                final String userName = Message.getDataFromLine(recievedMessage, Constants.COMMAND_SOURCE_USER_START, Constants.COMMAND_SOURCE_USER_END);
                final boolean isUserAlreadyRegisted = chatClientMap.containsKey(userName);
                if (isUserAlreadyRegisted) {
                    //user has been already registed
                    sendMessage(remotePrintWriter, Constants.COMMAND_AUTH_STATUS_REJECTED_USER_EXIST);
                    break currentClient;
                } else {
                    //user is not registed
                    processNewUserAuthRequest(userName, inputStream, outputStream, remotePrintWriter);
                    continue currentClient;
                }
            }
            if (isAuthCommand && !hasSourceUser) {
                //auth request has no user name
                sendMessage(remotePrintWriter, Constants.COMMAND_AUTH_STATUS_REJECTED_WRONG_REQUEST);
                break currentClient;
            }

            if (isBroadCastMessage) {
                //broadcast to everybody
                processBroadcastMessage(recievedMessage);
                continue currentClient;
            }
            if (isPrivateMessage) {
                //send private message
                processPrivateMessage(recievedMessage);
                continue currentClient;
            }
            if (isClientDisconnected && hasSourceUser) {
                //client has disconnected, we know who
                final String userName = Message.getDataFromLine(recievedMessage, Constants.COMMAND_SOURCE_USER_START, Constants.COMMAND_SOURCE_USER_END);
                processClientDisconnection(userName);
                break currentClient;
            }
            if (isClientDisconnected && !hasSourceUser) {
                //клиент отключился, we don't know who
                processClientDisconnection(Constants.TECHNICAL_MESSAGE_NO_NAME);
                break currentClient;
            }

        }
    }

    private void sendBroadCastMessage(final String message, final String exceptUser) {
        for (ConcurrentHashMap.Entry<String, ChatClient> entry : chatClientMap.entrySet()) {
            if (!exceptUser.equals(entry.getKey())) {
                final ChatClient chatClient = entry.getValue();
                final OutputStream outputStream = chatClient.getOutputStream();
                final PrintWriter remotePrintWriter = new PrintWriter(outputStream);
                sendMessage(remotePrintWriter, message);
            }
        }
    }

    private void sendPrivateMessage(final String message, final String toUser) {
        //TODO Handle case, when a user can't be found. Now - we just lose the message
        for (ConcurrentHashMap.Entry<String, ChatClient> entry : chatClientMap.entrySet()) {
            if (toUser.equals(entry.getKey())) {
                final ChatClient chatClient = entry.getValue();
                final OutputStream outputStream = chatClient.getOutputStream();
                final PrintWriter remotePrintWriter = new PrintWriter(outputStream);
                sendMessage(remotePrintWriter, message);
            }
        }
    }

    private void sendMessage(final PrintWriter remotePrintWriter, final String message) {
        remotePrintWriter.println(message);
        remotePrintWriter.flush();
    }

    private void processPrivateMessage(final String recievedMessage) {
        final String sourceUserName = Message.getDataFromLine(recievedMessage, Constants.COMMAND_SOURCE_USER_START, Constants.COMMAND_SOURCE_USER_END);
        final String targetUserName = Message.getDataFromLine(recievedMessage, Constants.COMMAND_TARGET_USER_START, Constants.COMMAND_TARGET_USER_END);
        final String sentMessage = Message.getDataFromLine(recievedMessage, Constants.COMMAND_PRIVATE_MESSAGE_START, Constants.COMMAND_PRIVATE_MESSAGE_END);
        serverMainUI.writeToChat(sourceUserName + Constants.TECHNICAL_MESSAGE_TO_USER_NAME + targetUserName + Constants.TECHNICAL_MESSAGE_AFTER_USER_NAME + sentMessage);
        sendPrivateMessage(recievedMessage, targetUserName);
    }

    private void processBroadcastMessage(final String recievedMessage) {
        final String sourceUserName = Message.getDataFromLine(recievedMessage, Constants.COMMAND_SOURCE_USER_START, Constants.COMMAND_SOURCE_USER_END);
        final String broadcastedMessage = Message.getDataFromLine(recievedMessage, Constants.COMMAND_BROADCAST_MESSAGE_START, Constants.COMMAND_BROADCAST_MESSAGE_END);
        serverMainUI.writeToChat(sourceUserName + Constants.TECHNICAL_MESSAGE_AFTER_USER_NAME + broadcastedMessage);
        sendBroadCastMessage(recievedMessage, sourceUserName);
    }

    private void processNewUserAuthRequest(final String userName, final InputStream inputStream, final OutputStream outputStream, final PrintWriter remotePrintWriter) {
        //user is not registed
        final ChatClient chatClient = new ChatClient(userName, inputStream, outputStream);
        //add user to our list (map)
        chatClientMap.put(userName, chatClient);
        //send auth ok to user
        sendMessage(remotePrintWriter, Constants.COMMAND_AUTH_STATUS_OK);
        //refresh userlist
        serverMainUI.updateUserList(chatClientMap);
        //new user notification at admins interface
        final String notification = userName + Constants.TECHNICAL_MESSAGE_USER_CONNECTED;
        serverMainUI.writeToChat(notification);
        //new user notification at user interfaces
        sendBroadCastMessage(notification, userName);
    }

    private void processClientDisconnection(final String userName) {
        //remove user
        chatClientMap.remove(userName);
        //refresh user list
        serverMainUI.updateUserList(chatClientMap);
        //notification at admins interface
        final String notification = userName + Constants.TECHNICAL_MESSAGE_USER_DISCONNECTED;
        serverMainUI.writeToChat(notification);
        //notify everyone
        sendBroadCastMessage(notification, userName);
    }
}
