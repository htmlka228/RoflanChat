package com.example.application.eventhandler;

import com.example.application.broadcast.UserLoggedInBroadcaster;
import com.example.application.model.RoflanUser;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthEventHandler {
    @EventListener
    public void onSuccessfulLoggedIn(InteractiveAuthenticationSuccessEvent event) {
        UserLoggedInBroadcaster.broadcast((RoflanUser) event.getAuthentication().getPrincipal());
    }
}
