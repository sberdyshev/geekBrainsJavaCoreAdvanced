package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.ui;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.Client;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author SBerdyshev
 */
public class ClientAuthUI {
    private JTextField nickTextField;
    private JLabel nickLabel;
    private JButton connectButton;
    private JButton exitButton;
    private JPanel authPanel;
    private JFrame authMainFrame;
    private Client client;

    public ClientAuthUI() {
        setUpUI();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void setUpUI() {
        setUpAuthPanel();
        setUpNickLabel();
        setUpTextLabel();
        setUpConnectButton();
        setUpExitButton();
        setUpSpacer();
        setUpAuthMainFrame();
    }

    private void setUpAuthPanel() {
        authPanel = new JPanel();
        authPanel.setLayout(new GridBagLayout());
    }

    private void setUpNickLabel() {
        nickLabel = new JLabel();
        nickLabel.setFocusable(false);
        nickLabel.setText(Constants.LABEL_NICK_FIELD);
        GridBagConstraints nickLabelGridBagConstraints = new GridBagConstraints();
        nickLabelGridBagConstraints.gridx = 0;
        nickLabelGridBagConstraints.gridy = 1;
        nickLabelGridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        authPanel.add(nickLabel, nickLabelGridBagConstraints);
    }

    private void setUpTextLabel() {
        nickTextField = new JTextField();
        GridBagConstraints nickTextFieldGridBagConstraints = new GridBagConstraints();
        nickTextFieldGridBagConstraints.gridx = 1;
        nickTextFieldGridBagConstraints.gridy = 1;
        nickTextFieldGridBagConstraints.gridwidth = 2;
        nickTextFieldGridBagConstraints.weightx = 1.0;
        nickTextFieldGridBagConstraints.weighty = 0.9;
        nickTextFieldGridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        nickTextFieldGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        authPanel.add(nickTextField, nickTextFieldGridBagConstraints);
    }

    private void setUpConnectButton() {
        connectButton = new JButton();
        connectButton.setText(Constants.LABEL_CONNECT);
        GridBagConstraints connectButtonGridBagConstraints = new GridBagConstraints();
        connectButtonGridBagConstraints.gridx = 1;
        connectButtonGridBagConstraints.gridy = 3;
        connectButtonGridBagConstraints.weightx = 1.0;
        connectButtonGridBagConstraints.anchor = GridBagConstraints.SOUTH;
        connectButtonGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        authPanel.add(connectButton, connectButtonGridBagConstraints);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });
    }

    private void setUpExitButton() {
        exitButton = new JButton();
        exitButton.setText(Constants.LABEL_EXIT_BUTTON);
        GridBagConstraints exitButtonGridBagConstraints = new GridBagConstraints();
        exitButtonGridBagConstraints.gridx = 2;
        exitButtonGridBagConstraints.gridy = 3;
        exitButtonGridBagConstraints.weightx = 1.0;
        exitButtonGridBagConstraints.anchor = GridBagConstraints.SOUTH;
        exitButtonGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        authPanel.add(exitButton, exitButtonGridBagConstraints);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitFromAuthUI();
            }
        });
    }

    private void exitFromAuthUI() {
        client.closeObjects();
        System.exit(Constants.TECHNICAL_MESSAGE_DEFAULT_SYSTEM_EXIT);
    }

    private void setUpSpacer() {
        final JPanel spacer = new JPanel();
        GridBagConstraints spacerGridBagConstraints = new GridBagConstraints();
        spacerGridBagConstraints.gridx = 1;
        spacerGridBagConstraints.gridy = 2;
        spacerGridBagConstraints.weighty = 1.0;
        spacerGridBagConstraints.fill = GridBagConstraints.VERTICAL;
        authPanel.add(spacer, spacerGridBagConstraints);
    }

    private void setUpAuthMainFrame() {
        authMainFrame = new JFrame();
        authMainFrame.add(authPanel);
        authMainFrame.setTitle(Constants.TITLE_CLIENT_AUTH_UI);
        authMainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        authMainFrame.setBounds(Constants.COORDINATE_START_X, Constants.COORDINATE_START_Y, Constants.COORDINATE_WIDTH_X, Constants.COORDINATE_WIDTH_Y);
    }

    public void setVisible(final boolean visibility) {
        authMainFrame.setVisible(visibility);
    }

    public void reset() {
        nickTextField.setText(Constants.TECHNICAL_MESSAGE_EMPTY_STRING);
    }

    private void connect() {
        //connect
        final String userNickName = nickTextField.getText();
        final boolean isNickFieldNotEmpty = !userNickName.trim().isEmpty();
        if (isNickFieldNotEmpty) {
            client.initialise();
            final InputStream inputStream = client.getInputStream();
            final OutputStream outputStream = client.getOutputStream();
            final Socket socket = client.getSocket();
            final Scanner remoteScanner = new Scanner(inputStream);
            final PrintWriter remotePrintWriter = new PrintWriter(outputStream);

            final String nickName = nickTextField.getText();
            sendAuthRequest(remotePrintWriter, nickName);
            processAuthResponse(remoteScanner, nickName, remotePrintWriter, inputStream, outputStream, socket);
        }
    }

    private void sendAuthRequest(final PrintWriter remotePrintWriter, final String nickName) {
        final String authCommand = Constants.COMMAND_AUTH + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_SOURCE_USER_START + Constants.TECHNICAL_MESSAGE_SEPARATOR + nickName + Constants.TECHNICAL_MESSAGE_SEPARATOR + Constants.COMMAND_SOURCE_USER_END;
        remotePrintWriter.println(authCommand);
        remotePrintWriter.flush();
    }

    private void processAuthResponse(final Scanner remoteScanner, final String nickName, final PrintWriter remotePrintWriter, final InputStream inputStream, final OutputStream outputStream, final Socket socket) {
        final String authResponse = remoteScanner.nextLine();
        if (authResponse.contains(Constants.COMMAND_AUTH_STATUS_OK)) {
            //successful connection
            //move to the main frame
            client.setNickName(nickName);
            client.getClientAuthUI().setVisible(false);
            client.getClientChatUI().setVisible(true);
            client.getClientChatUI().setClient(client);
            client.getClientChatUI().startChatListeners();
        }
        if (authResponse.contains(Constants.COMMAND_AUTH_STATUS_REJECTED_USER_EXIST)) {
            //user exists
            //go to retry frame
            changeToRetryUI();
            remotePrintWriter.close();
            remoteScanner.close();
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException ioe) {
                HandleConnectionException();
            }
            ;
        }
        if (authResponse.contains(Constants.COMMAND_AUTH_STATUS_REJECTED_WRONG_REQUEST)) {
            //invalid request
            //go to retry frame
            changeToRetryUI();
            remotePrintWriter.close();
            remoteScanner.close();
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException ioe) {
                HandleConnectionException();
            }
        }
    }

    private void HandleConnectionException() {
        if (client != null) {
            final ClientAuthUI clientAuthUI = client.getClientAuthUI();
            final ClientChatUI clientChatUI = client.getClientChatUI();
            final ClientRetryUI clientRetryUI = client.getClientRetryUI();
            if (clientRetryUI != null) {
                clientRetryUI.setVisible(true);
                if (client.getClientAuthUI() != null) {
                    clientAuthUI.setVisible(false);
                }
                if (clientChatUI != null) {
                    clientChatUI.setVisible(false);
                }
            }
            try {
                final OutputStream outputStream = client.getOutputStream();
                final InputStream inputStream = client.getInputStream();
                final Socket socket = client.getSocket();
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            System.exit(Constants.TECHNICAL_MESSAGE_DEFAULT_SYSTEM_EXIT);
        }

    }

    private void changeToRetryUI() {
        client.getClientAuthUI().setVisible(false);
        client.getClientRetryUI().setVisible(true);
    }
}
