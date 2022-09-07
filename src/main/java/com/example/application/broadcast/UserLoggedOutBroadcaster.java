package com.example.application.broadcast;

import com.example.application.model.RoflanUser;
import com.vaadin.flow.shared.Registration;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UserLoggedOutBroadcaster {
    static Executor executor = Executors.newSingleThreadExecutor();

    static LinkedList<Consumer<RoflanUser>> listeners = new LinkedList<>();

    public static synchronized Registration register(
            Consumer<RoflanUser> listener) {
        listeners.add(listener);

        return () -> {
            synchronized (UserLoggedOutBroadcaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(RoflanUser message) {
        for (Consumer<RoflanUser> listener : listeners) {
            executor.execute(() -> listener.accept(message));
        }
    }
}
