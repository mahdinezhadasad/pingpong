package com.example.pingpong.domain;


import com.example.pingpong.domain.ports.MessagePort;
import com.example.pingpong.domain.ports.MessageStrategy;

import java.io.IOException;
import java.util.logging.Logger;


public class Player implements Runnable {
    private final String name;
    private final MessagePort outgoing;
    private final MessagePort incoming;
    private final int maxMessages;
    private final MessageStrategy messageStrategy;

    public Player(String name, MessagePort outgoing, MessagePort incoming, int maxMessages, MessageStrategy messageStrategy) {
        this.name = name;
        this.outgoing = outgoing;
        this.incoming = incoming;
        this.maxMessages = maxMessages;
        this.messageStrategy = messageStrategy;
    }
    private static final Logger logger = Logger.getLogger(Player.class.getName());

    @Override
    public void run() {
        try {
            if ("Initiator".equals(name)) {
                for (int i = 1; i <= maxMessages; i++) {
                    String msg = name + "->" + i;
                    System.out.println(name + " sending: " + msg);
                    outgoing.send(msg);
                    String reply = incoming.receive();
                    logger.info(name + " received: " + reply);
                }
            } else {
                for (int i = 1; i <= maxMessages; i++) {
                    String incomingMsg = incoming.receive();
                   logger.info(name + " received: " + incomingMsg);
                    String response = messageStrategy.respondTo(incomingMsg, i);
                    System.out.println(name + " replying: " + response);
                    outgoing.send(response);

                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
