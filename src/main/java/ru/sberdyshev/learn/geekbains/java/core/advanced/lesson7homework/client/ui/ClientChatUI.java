package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.ui;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.Client;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils.Constants;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author SBerdyshev
 */
public class ClientChatUI {
    private JPanel chatPanel;
    private JTextArea chatTextArea;
    private JButton sendButton;
    private JTextField inputTextField;
    private JButton exitButton;
    private JScrollPane chatScrollPane;
    private JFrame clientChatFrame;
    private Client client;

    public ClientChatUI() {
        setUpUI();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void setUpUI() {
        setUpChatPanel();
        setUpChatScrollPane();
        setUpSendButton();
        setUpInputTextField();
        setUpExitButton();
        setUpFrame();
    }

    private void setUpChatPanel() {
        chatPanel = new JPanel();
        chatPanel.setLayout(new GridBagLayout());
        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
    }

    private void setUpChatScrollPane() {
        chatScrollPane = new JScrollPane();
        GridBagConstraints chatScrollPaneGridBagConstraints = new GridBagConstraints();
        chatScrollPaneGridBagConstraints.gridx = 0;
        chatScrollPaneGridBagConstraints.gridy = 0;
        chatScrollPaneGridBagConstraints.gridwidth = 3;
        chatScrollPaneGridBagConstraints.weighty = 1.0;
        chatScrollPaneGridBagConstraints.fill = GridBagConstraints.BOTH;
        chatPanel.add(chatScrollPane, chatScrollPaneGridBagConstraints);
        chatScrollPane.setViewportView(chatTextArea);
    }

    private void setUpSendButton() {
        sendButton = new JButton();
        sendButton.setText(Constants.LABEL_SEND_BUTTON);
        GridBagConstraints chatScrollPaneGridBagConstraints = new GridBagConstraints();
        chatScrollPaneGridBagConstraints.gridx = 0;
        chatScrollPaneGridBagConstraints.gridy = 1;
        chatScrollPaneGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(sendButton, chatScrollPaneGridBagConstraints);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String sendText = inputTextField.getText();
                final boolean isTextEntered = !sendText.trim().isEmpty();
                if (isTextEntered) {
                    sendMessage(sendText);
                    final String sentTextInChatFormat = client.getNickName() + Constants.TECHNICAL_MESSAGE_AFTER_USER_NAME + sendText + Constants.TECHNICAL_MESSAGE_END_LINE;
                    chatTextArea.append(sentTextInChatFormat);
                }
            }
        });
    }

    private void setUpInputTextField() {
        inputTextField = new JTextField();
        GridBagConstraints inputTextFieldGridBagConstraints = new GridBagConstraints();
        inputTextFieldGridBagConstraints.gridx = 1;
        inputTextFieldGridBagConstraints.gridy = 1;
        inputTextFieldGridBagConstraints.weightx = 1.0;
        inputTextFieldGridBagConstraints.anchor = GridBagConstraints.WEST;
        inputTextFieldGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(inputTextField, inputTextFieldGridBagConstraints);
    }

    private void setUpExitButton() {
        exitButton = new JButton();
        exitButton.setText(Constants.LABEL_EXIT_BUTTON);
        GridBagConstraints exitButtonFieldGridBagConstraints = new GridBagConstraints();
        exitButtonFieldGridBagConstraints.gridx = 2;
        exitButtonFieldGridBagConstraints.gridy = 1;
        exitButtonFieldGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(exitButton, exitButtonFieldGridBagConstraints);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitFromChat();
            }
        });
    }

    private void setUpFrame() {
        clientChatFrame = new JFrame();
        clientChatFrame.add(chatPanel);
        clientChatFrame.setTitle(Constants.TITLE_CLIENT_CHAT_UI);
        clientChatFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        clientChatFrame.setBounds(Constants.COORDINATE_START_X, Constants.COORDINATE_START_Y, Constants.COORDINATE_WIDTH_X, Constants.COORDINATE_WIDTH_Y);
    }

    public void setVisible(boolean visibility) {
        clientChatFrame.setVisible(visibility);
    }

    public void reset() {
        chatTextArea.setText(Constants.TECHNICAL_MESSAGE_EMPTY_STRING);
        inputTextField.setText(Constants.TECHNICAL_MESSAGE_EMPTY_STRING);
    }

    public void startChatListeners() {
        startReciever();
    }

    public void startReciever() {
        final Scanner remoteScanner = new Scanner(client.getInputStream());
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    String recievedMessage = remoteScanner.nextLine();
                    if (recievedMessage.contains(Constants.COMMAND_BROADCAST_MESSAGE_START)) {
                        //got Broadcast msg
                        final String message = Message.getDataFromLine(recievedMessage, Constants.COMMAND_BROADCAST_MESSAGE_START, Constants.COMMAND_BROADCAST_MESSAGE_END);
                        final String sourceUser = Message.getDataFromLine(recievedMessage, Constants.COMMAND_SOURCE_USER_START, Constants.COMMAND_SOURCE_USER_END);
                        chatTextArea.append(sourceUser + Constants.TECHNICAL_MESSAGE_AFTER_USER_NAME + message + Constants.TECHNICAL_MESSAGE_END_LINE);
                    }
                    if (recievedMessage.contains(Constants.COMMAND_PRIVATE_MESSAGE_START)) {
                        //got Private msg
                        final String message = Message.getDataFromLine(recievedMessage, Constants.COMMAND_PRIVATE_MESSAGE_START, Constants.COMMAND_PRIVATE_MESSAGE_END);
                        final String sourceUser = Message.getDataFromLine(recievedMessage, Constants.COMMAND_SOURCE_USER_START, Constants.COMMAND_SOURCE_USER_END);
                        chatTextArea.append(sourceUser + Constants.TECHNICAL_MESSAGE_TO_USER_NAME + Constants.TECHNICAL_MESSAGE_YOU + Constants.TECHNICAL_MESSAGE_AFTER_USER_NAME + message + Constants.TECHNICAL_MESSAGE_END_LINE);
                    }
                }
            }
        }).start();

    }

    private void exitFromChat() {
        final String clientName = client.getNickName();
        final String preparedMessage = Constants.COMMAND_CLIENT_DISCONNECTED + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_SOURCE_USER_START + Constants.TECHNICAL_MESSAGE_SEPARATOR + clientName + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_SOURCE_USER_END;
        send(preparedMessage);
        client.closeObjects();
        System.exit(Constants.TECHNICAL_MESSAGE_DEFAULT_SYSTEM_EXIT);
    }

    private void sendMessage(final String requestLine) {
        final String clientName = client.getNickName();
        if (requestLine.contains(Constants.CLIENT_COMMAND_PRIVATE_MESSAGE)) {
            //private message
            final String requestMessage = extractRequestMessage(requestLine);
            final String targetUser = extractTargetUser(requestLine);
            final String preparedMessage = Constants.COMMAND_PRIVATE_MESSAGE_START + Constants.TECHNICAL_MESSAGE_SEPARATOR + requestMessage + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_PRIVATE_MESSAGE_END + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_SOURCE_USER_START + Constants.TECHNICAL_MESSAGE_SEPARATOR + clientName + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_SOURCE_USER_END + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_TARGET_USER_START + Constants.TECHNICAL_MESSAGE_SEPARATOR + targetUser + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_TARGET_USER_END;
            send(preparedMessage);
        } else {
            final String preparedMessage = Constants.COMMAND_BROADCAST_MESSAGE_START + Constants.TECHNICAL_MESSAGE_SEPARATOR + requestLine + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_BROADCAST_MESSAGE_END + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_SOURCE_USER_START + Constants.TECHNICAL_MESSAGE_SEPARATOR + clientName + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_SOURCE_USER_END;
            send(preparedMessage);
        }
        inputTextField.setText(Constants.TECHNICAL_MESSAGE_EMPTY_STRING);
    }

    private String extractTargetUser(String requestLine) {
        final int positionOfStartCommand = requestLine.indexOf(Constants.TECHNICAL_MESSAGE_SEPARATOR,0) + 1;
        final int positionOfEndCommand = requestLine.indexOf(Constants.TECHNICAL_MESSAGE_SEPARATOR, 3);
        final boolean isCommandPositionsDifferencePositive = (positionOfEndCommand > positionOfStartCommand);
        String targetUser = null;
        if (isCommandPositionsDifferencePositive) {
            targetUser = requestLine.substring(positionOfStartCommand, positionOfEndCommand);
        }
        return targetUser;
    }

    private String extractRequestMessage(String requestLine) {
        final int positionOfStartCommand = requestLine.indexOf(Constants.TECHNICAL_MESSAGE_SEPARATOR, 3) + 1;
        final boolean isCommandPositionsDifferencePositive = (positionOfStartCommand > 0);
        String requestMessage = null;
        if (isCommandPositionsDifferencePositive) {
            requestMessage = requestLine.substring(positionOfStartCommand);
        }
        return requestMessage;
    }

    private void send(final String preparedMessage) {
        final PrintWriter remotePrintWriter = new PrintWriter(client.getOutputStream());
        remotePrintWriter.println(preparedMessage);
        remotePrintWriter.flush();
    }
}
