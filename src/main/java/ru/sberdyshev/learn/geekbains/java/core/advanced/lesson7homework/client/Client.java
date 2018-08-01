package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.ui.ClientAuthUI;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.ui.ClientChatUI;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.client.ui.ClientRetryUI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author SBerdyshev
 */
public class Client {
    private ClientAuthUI clientAuthUI;
    private ClientChatUI clientChatUI;
    private ClientRetryUI clientRetryUI;
    private Socket socket;
    private String connectionAddress;
    private int connectionPort;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String nickName;

    public Client(String connectionAddress, int connectionPort) {
        this.connectionAddress = connectionAddress;
        this.connectionPort = connectionPort;
        clientAuthUI = new ClientAuthUI();
        clientAuthUI.setClient(this);
        clientChatUI = new ClientChatUI();
        clientChatUI.setClient(this);
        clientRetryUI = new ClientRetryUI();
        clientRetryUI.setClient(this);
    }

    public void reset() {
        socket = null;
        inputStream = null;
        outputStream = null;
        nickName = null;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public ClientAuthUI getClientAuthUI() {
        return clientAuthUI;
    }

    public ClientChatUI getClientChatUI() {
        return clientChatUI;
    }

    public ClientRetryUI getClientRetryUI() {
        return clientRetryUI;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getConnectionAddress() {
        return connectionAddress;
    }

    public void setConnectionAddress(String connectionAddress) {
        this.connectionAddress = connectionAddress;
    }

    public int getConnectionPort() {
        return connectionPort;
    }

    public void setConnectionPort(int connectionPort) {
        this.connectionPort = connectionPort;
    }

    public void start() {
        clientAuthUI.setVisible(true);
    }

    public void initialise() {
        try {
            final Socket socket = new Socket(getConnectionAddress(), getConnectionPort());

            setSocket(socket);
            final InputStream inputStream = socket.getInputStream();
            final OutputStream outputStream = socket.getOutputStream();

            setInputStream(inputStream);
            setOutputStream(outputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            HandleConnectionException();
        }
    }

    private void HandleConnectionException() {
        if (clientAuthUI != null) {
            clientAuthUI.setVisible(false);
        }
        if (clientChatUI != null) {
            clientChatUI.setVisible(false);
        }
        if (clientRetryUI != null) {
            clientRetryUI.setVisible(true);
        }
        closeObjects();
    }

    public void closeObjects() {
        try {
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
    }
}
