package com.example.pingpong.domain.ports;

public interface MessageStrategy {
    String respondTo(String incoming, int count);
}