package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.ui;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.Client;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author SBerdyshev
 */
public class ClientRetryUI {
    private JFrame mainClientRetryFrame;
    private JPanel retryPanel;
    private JButton retryButton;
    private JButton exitButton;
    private JLabel textLabel;
    private Client client;

    public ClientRetryUI() {
        setUpUI();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void setUpUI() {
        setUpRetryPanel();
        setUpExitButton();
        setUpTextLabel();
        setUpRetryButton();
        setUpMainClientRetryFrame();
    }

    private void setUpRetryPanel() {
        retryPanel = new JPanel();
        retryPanel.setLayout(new GridBagLayout());
    }

    private void setUpExitButton() {
        exitButton = new JButton();
        exitButton.setText(Constants.LABEL_EXIT_BUTTON);
        GridBagConstraints exitButtonGridBagConstraints = new GridBagConstraints();
        exitButtonGridBagConstraints.gridx = 1;
        exitButtonGridBagConstraints.gridy = 1;
        exitButtonGridBagConstraints.weightx = 1.0;
        exitButtonGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        retryPanel.add(exitButton, exitButtonGridBagConstraints);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitFromRetryUI();
            }
        });
    }

    private void exitFromRetryUI() {
        System.exit(Constants.TECHNICAL_MESSAGE_DEFAULT_SYSTEM_EXIT);
    }

    private void setUpTextLabel() {
        textLabel = new JLabel();
        textLabel.setFocusable(false);
        textLabel.setText(Constants.LABEL_CONNECTION_LOST);
        GridBagConstraints textLabelGridBagConstraints = new GridBagConstraints();
        textLabelGridBagConstraints.gridx = 0;
        textLabelGridBagConstraints.gridy = 0;
        textLabelGridBagConstraints.gridwidth = 2;
        textLabelGridBagConstraints.weightx = 1.0;
        textLabelGridBagConstraints.weighty = 1.0;
        retryPanel.add(textLabel, textLabelGridBagConstraints);
    }

    private void setUpRetryButton() {
        retryButton = new JButton();
        retryButton.setText(Constants.LABEL_RETRY);
        GridBagConstraints retryButtonGridBagConstraints = new GridBagConstraints();
        retryButtonGridBagConstraints.gridx = 0;
        retryButtonGridBagConstraints.gridy = 1;
        retryButtonGridBagConstraints.weightx = 1.0;
        retryButtonGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        retryPanel.add(retryButton, retryButtonGridBagConstraints);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retryConnection();
            }
        });
    }

    private void retryConnection() {
        client.closeObjects();
        client.reset();
        client.getClientChatUI().reset();
        client.getClientAuthUI().reset();
        client.getClientRetryUI().setVisible(false);
        client.getClientChatUI().setVisible(false);
        client.getClientAuthUI().setVisible(true);
    }

    private void setUpMainClientRetryFrame() {
        mainClientRetryFrame = new JFrame();
        mainClientRetryFrame.add(retryPanel);
        mainClientRetryFrame.setTitle(Constants.TITLE_CLIENT_RETRY_UI);
        mainClientRetryFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainClientRetryFrame.setBounds(Constants.COORDINATE_START_X, Constants.COORDINATE_START_Y, Constants.COORDINATE_WIDTH_X, Constants.COORDINATE_WIDTH_Y);
    }

    public void setVisible(boolean visibility) {
        mainClientRetryFrame.setVisible(visibility);
    }
}
