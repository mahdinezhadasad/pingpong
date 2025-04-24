package com.example.pingpong.adapters.memory;



import com.example.pingpong.domain.ports.MessagePort;

import java.util.concurrent.SynchronousQueue;


public class InMemoryMessageAdapter implements MessagePort {

    private final SynchronousQueue<String> queue;

    public InMemoryMessageAdapter(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void send(String message) throws InterruptedException {
        queue.put(message);
    }

    @Override
    public String receive() throws InterruptedException {
        return queue.take();
    }
}
