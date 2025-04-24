package com.example.pingpong.domain.ports;


import java.io.IOException;

public interface MessagePort {
    void send(String message) throws InterruptedException;
    String receive() throws InterruptedException, IOException;
}
