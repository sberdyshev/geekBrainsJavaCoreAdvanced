package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson7homework.utils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SBerdyshev
 */
public class ChatClient {
    private String name;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ChatClient(String name, InputStream inputStream, OutputStream outputStream) {
        this.name = name;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
