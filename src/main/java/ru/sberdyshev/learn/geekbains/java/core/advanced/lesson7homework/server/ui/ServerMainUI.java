package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.server.ui;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils.ChatClient;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SBerdyshev
 */
public class ServerMainUI {
    private JFrame serverMainForm;
    private JPanel serverPanel;
    private JTextArea chatTextArea;
    private JScrollPane chatScrollPane;
    private JTextArea userListTextArea;
    private JScrollPane userListScrollPane;
    private JButton exitButton;

    public ServerMainUI() {
        setUpUI();
    }

    private void setUpUI() {
        setUpServerPanel();
        setUpChatScrollPane();
        setUpChatTextArea();
        setUpUserListTextArea();
        setUpUserListScrollPane();
        setUpChatScrollPanel();
        setUpUserListScrollPanel();
        setUpExitButton();
        setUpServerMainForm();
    }

    private void setUpServerPanel() {
        serverPanel = new JPanel();
        serverPanel.setLayout(new GridBagLayout());
    }

    private void setUpChatScrollPane() {
        chatScrollPane = new JScrollPane();
    }

    private void setUpChatTextArea() {
        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
    }

    private void setUpUserListTextArea() {
        userListTextArea = new JTextArea();
        userListTextArea.setEditable(false);
    }

    private void setUpUserListScrollPane() {
        userListScrollPane = new JScrollPane();
    }

    private void setUpChatScrollPanel() {
        chatScrollPane = new JScrollPane();
        final GridBagConstraints chatScrollPanelGridConstraints = new GridBagConstraints();
        chatScrollPanelGridConstraints.gridx = 0;
        chatScrollPanelGridConstraints.gridy = 0;
        chatScrollPanelGridConstraints.weightx = 4.0;
        chatScrollPanelGridConstraints.weighty = 1.0;
        chatScrollPanelGridConstraints.fill = GridBagConstraints.BOTH;
        serverPanel.add(chatScrollPane, chatScrollPanelGridConstraints);
        chatScrollPane.setViewportView(chatTextArea);
    }

    private void setUpUserListScrollPanel() {
        userListScrollPane = new JScrollPane();
        final GridBagConstraints userListScrollPanelGridConstraints = new GridBagConstraints();
        userListScrollPanelGridConstraints.gridx = 1;
        userListScrollPanelGridConstraints.gridy = 0;
        userListScrollPanelGridConstraints.weightx = 1.0;
        userListScrollPanelGridConstraints.weighty = 1.0;
        userListScrollPanelGridConstraints.fill = GridBagConstraints.BOTH;
        serverPanel.add(userListScrollPane, userListScrollPanelGridConstraints);
        userListScrollPane.setViewportView(userListTextArea);
    }

    private void setUpExitButton() {
        exitButton = new JButton();
        exitButton.setText(Constants.LABEL_EXIT_BUTTON);
        final GridBagConstraints exitButtonGridConstraints = new GridBagConstraints();
        exitButtonGridConstraints.gridx = 1;
        exitButtonGridConstraints.gridy = 1;
        exitButtonGridConstraints.weightx = 1.0;
        exitButtonGridConstraints.fill = GridBagConstraints.HORIZONTAL;
        serverPanel.add(exitButton, exitButtonGridConstraints);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void setUpServerMainForm() {
        serverMainForm = new JFrame();
        serverMainForm.add(serverPanel);
        serverMainForm.setTitle(Constants.TITLE_SERVER_MAIN_UI);
        serverMainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        serverMainForm.setBounds(Constants.COORDINATE_START_X, Constants.COORDINATE_START_Y, Constants.COORDINATE_WIDTH_X, Constants.COORDINATE_WIDTH_Y);
    }

    public void setVisible(final boolean visibility) {
        serverMainForm.setVisible(visibility);
    }

    public void updateUserList(final Map<String, ChatClient> chatClientMap) {
        userListTextArea.setText(Constants.TECHNICAL_MESSAGE_EMPTY_STRING);
        for (final ConcurrentHashMap.Entry<String, ChatClient> entry : chatClientMap.entrySet()) {
            userListTextArea.append(entry.getKey() + Constants.TECHNICAL_MESSAGE_END_LINE);
        }
    }

    public void writeToChat(final String message) {
        chatTextArea.append(message + Constants.TECHNICAL_MESSAGE_END_LINE);
    }
}
