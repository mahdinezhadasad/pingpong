package com.example.pingpong.domain;

import com.example.pingpong.domain.ports.MessageStrategy;

public class SimpleMessageStrategy implements MessageStrategy {

    private final String responderName;

    public SimpleMessageStrategy(String responderName) {
        this.responderName = responderName;
    }

    @Override
    public String respondTo(String incoming, int count) {
        return incoming + " [" + responderName + "#" + count + "]";
    }
}