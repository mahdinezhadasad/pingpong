package com.example.pingpong.domain;


import com.example.pingpong.domain.ports.MessagePort;
import com.example.pingpong.domain.ports.MessageStrategy;
import org.junit.Test;


import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    static class FakeMessagePort implements MessagePort {
        private final Queue<String> sentMessages = new LinkedList<>();
        private final Queue<String> messagesToReceive = new LinkedList<>();

        @Override
        public void send(String message) {
            sentMessages.add(message);
        }

        @Override
        public String receive() {
            return messagesToReceive.poll();
        }

        public void addIncoming(String msg) {
            messagesToReceive.add(msg);
        }

        public Queue<String> getSentMessages() {
            return sentMessages;
        }
    }

    @Test
    public void testSimpleMessageStrategy() {
        MessageStrategy strategy = new SimpleMessageStrategy("Responder");
        String result = strategy.respondTo("Ping", 1);
        assertEquals("Ping [Responder#1]", result);
    }

    @Test
    public void testInitiatorSendsMessages() {
        FakeMessagePort outgoing = new FakeMessagePort();
        FakeMessagePort incoming = new FakeMessagePort();

        // Add 3 fake replies
        incoming.addIncoming("Reply1");
        incoming.addIncoming("Reply2");
        incoming.addIncoming("Reply3");

        Player initiator = new Player("Initiator", outgoing, incoming, 3, null);
        initiator.run();

        assertEquals(3, outgoing.getSentMessages().size());
        assertTrue(outgoing.getSentMessages().peek().startsWith("Initiator->1"));
    }

    @Test
    public void testResponderRepliesUsingStrategy() {
        FakeMessagePort outgoing = new FakeMessagePort();
        FakeMessagePort incoming = new FakeMessagePort();

        incoming.addIncoming("Hello1");
        incoming.addIncoming("Hello2");

        MessageStrategy strategy = new SimpleMessageStrategy("Responder");

        Player responder = new Player("Responder", outgoing, incoming, 2, strategy);
        responder.run();

        assertEquals("Hello1 [Responder#1]", outgoing.getSentMessages().poll());
        assertEquals("Hello2 [Responder#2]", outgoing.getSentMessages().poll());
    }
}