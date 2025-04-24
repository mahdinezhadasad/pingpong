package com.example.pingpong.domain;

import com.example.pingpong.domain.ports.MessageStrategy;

public class JsonMessageStrategy implements MessageStrategy {

    private final String responder;

    public JsonMessageStrategy(String responder) {
        this.responder = responder;
    }

    @Override
    public String respondTo(String incoming, int count) {
        return String.format("{\"from\":\"%s\",\"received\":\"%s\",\"count\":%d}",
                responder, incoming, count);
    }
}