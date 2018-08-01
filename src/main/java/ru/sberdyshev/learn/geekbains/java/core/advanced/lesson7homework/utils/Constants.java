package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils;

/**
 * @author SBerdyshev
 */
public class Constants {
    public static final String LABEL_EXIT_BUTTON = "Exit";
    public static final String LABEL_CONNECTION_LOST = "Connection lost...";
    public static final String LABEL_RETRY = "Retry";
    public static final String LABEL_NICK_FIELD = "Enter your nick name: ";
    public static final String LABEL_CONNECT = "Connect";
    public static final String LABEL_SEND_BUTTON = "Send";
    public static final String TITLE_SERVER_MAIN_UI = "Chat server";
    public static final String TITLE_CLIENT_AUTH_UI = "Chat authorisation";
    public static final String TITLE_CLIENT_CHAT_UI = "Chat client";
    public static final String TITLE_CLIENT_RETRY_UI = "Error";

    public static final int COORDINATE_START_X = 300;
    public static final int COORDINATE_START_Y = 300;
    public static final int COORDINATE_WIDTH_X = 400;
    public static final int COORDINATE_WIDTH_Y = 400;

    public static final String COMMAND_BROADCAST_MESSAGE_START = "/broadCastStart";
    public static final String COMMAND_BROADCAST_MESSAGE_END = "/broadCastEnd";
    public static final String COMMAND_PRIVATE_MESSAGE_START = "/privateStart";
    public static final String COMMAND_PRIVATE_MESSAGE_END = "/privateEnd";
    public static final String COMMAND_TARGET_USER_START = "/toUserStart";
    public static final String COMMAND_TARGET_USER_END = "/toUserEnd";
    public static final String COMMAND_SOURCE_USER_START = "/fromUserStart";
    public static final String COMMAND_SOURCE_USER_END = "/fromUserEnd";
    public static final String COMMAND_AUTH = "/auth";
    public static final String COMMAND_AUTH_STATUS_REJECTED_USER_EXIST = "/authRejectedUserExist";
    public static final String COMMAND_AUTH_STATUS_REJECTED_WRONG_REQUEST = "/authRejectedWrongRequest";
    public static final String COMMAND_AUTH_STATUS_OK = "/authOk";
    public static final String COMMAND_CLIENT_DISCONNECTED = "/disconnected";

    public static final String TECHNICAL_MESSAGE_END_LINE = "\n";
    public static final String TECHNICAL_MESSAGE_AFTER_USER_NAME = ": ";
    public static final String TECHNICAL_MESSAGE_TO_USER_NAME = " sends private message to ";
    public static final String TECHNICAL_MESSAGE_YOU = "you";
    public static final String TECHNICAL_MESSAGE_SEPARATOR = " ";
    public static final String TECHNICAL_MESSAGE_EMPTY_STRING = "";
    public static final String TECHNICAL_MESSAGE_USER_CONNECTED = " connected";
    public static final String TECHNICAL_MESSAGE_USER_DISCONNECTED = " disconnected";
    public static final String TECHNICAL_MESSAGE_NO_NAME = "no name";
    public static final int TECHNICAL_MESSAGE_DEFAULT_SYSTEM_EXIT = 0;

    public static final String CLIENT_COMMAND_PRIVATE_MESSAGE = "/w";

    private Constants() {}
}
