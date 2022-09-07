package com.example.application.broadcast;

import com.example.application.model.RoflanMessage;
import com.vaadin.flow.shared.Registration;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Broadcaster {
    static Executor executor = Executors.newSingleThreadExecutor();

    static LinkedList<Consumer<RoflanMessage>> listeners = new LinkedList<>();

    public static synchronized Registration register(
            Consumer<RoflanMessage> listener) {
        listeners.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(RoflanMessage message) {
        for (Consumer<RoflanMessage> listener : listeners) {
            executor.execute(() -> listener.accept(message));
        }
    }
}
