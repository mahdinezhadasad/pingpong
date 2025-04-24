package com.example.pingpong.adapters.cli;

import com.example.pingpong.domain.Player;
import com.example.pingpong.domain.SocketMessageAdapter;
import com.example.pingpong.domain.ports.MessagePort;

import java.net.Socket;

public class InitiatorMain {
    public static void main(String[] args) {
        int rounds = args.length > 0 ? Integer.parseInt(args[0]) : 10;
        try (Socket socket = new Socket("localhost", 9999)) {
            System.out.println("Initiator connected to Responder.");

            MessagePort port = new SocketMessageAdapter(socket);
            Player initiator = new Player("Initiator", port, port, 10, null);
            initiator.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}