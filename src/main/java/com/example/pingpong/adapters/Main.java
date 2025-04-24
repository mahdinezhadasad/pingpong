package com.example.pingpong.adapters;

import com.example.pingpong.adapters.memory.InMemoryMessageAdapter;

import com.example.pingpong.domain.JsonMessageStrategy;
import com.example.pingpong.domain.Player;
import com.example.pingpong.domain.ports.MessagePort;
import com.example.pingpong.domain.SimpleMessageStrategy;
import com.example.pingpong.domain.ports.MessageStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * CLI adapter that bootstraps the ping-pong interaction.
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {

        SynchronousQueue<String> toResponder = new SynchronousQueue<>();
        SynchronousQueue<String> toInitiator = new SynchronousQueue<>();


        MessagePort initiatorPort = new InMemoryMessageAdapter(toResponder);
        MessagePort responderPort = new InMemoryMessageAdapter(toInitiator);


     //   int rounds = 10;


        String strategyName = args.length > 0 ? args[0] : "simple";
        int rounds = args.length > 0 ? Integer.parseInt(args[0]) : 10;

        MessageStrategy responderStrategy = switch (strategyName.toLowerCase()) {
            case "json" -> new JsonMessageStrategy("Responder");
           // case "base64" -> new Base64EncodingStrategy("Responder");
            default -> new SimpleMessageStrategy("Responder");
        };


        Player initiator = new Player("Initiator", initiatorPort, responderPort, rounds, null);
        Player responder = new Player("Responder", responderPort, initiatorPort, rounds, responderStrategy);


        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(initiator);
        executor.submit(responder);


        executor.shutdown();
        boolean finished = executor.awaitTermination(10, TimeUnit.SECONDS);

        if (finished) {
            System.out.println("✅ Ping-Pong finished successfully.");
        } else {
            System.err.println("⚠️ Players did not finish in time!");
            executor.shutdownNow();
        }
    }
}
