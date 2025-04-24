package com.example.pingpong.adapters.cli;

import com.example.pingpong.domain.JsonMessageStrategy;
import com.example.pingpong.domain.Player;
import com.example.pingpong.domain.SocketMessageAdapter;
import com.example.pingpong.domain.ports.MessagePort;

import java.net.ServerSocket;
import java.net.Socket;

public class ResponderMain {
    public static void main(String[] args) {
        int rounds = args.length > 0 ? Integer.parseInt(args[0]) : 10;

        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("Responder waiting for Initiator...");

            Socket socket = serverSocket.accept();
            System.out.println("Responder connected.");

            MessagePort port = new SocketMessageAdapter(socket);
            Player responder = new Player("Responder", port, port, rounds, new JsonMessageStrategy("Responder"));
            responder.run();

        } catch (Exception e) {
            e.printStackTrace();
        }}
}