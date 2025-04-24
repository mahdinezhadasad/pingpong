package com.example.pingpong.domain;

import com.example.pingpong.domain.ports.MessagePort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketMessageAdapter implements MessagePort {

    private final BufferedReader reader;
    private final PrintWriter writer;

    public SocketMessageAdapter(Socket socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void send(String message) {
        writer.println(message);
    }

    @Override
    public String receive() throws IOException {
        return reader.readLine();
    }
}