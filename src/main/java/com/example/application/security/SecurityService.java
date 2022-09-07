package com.example.application.security;

import com.example.application.broadcast.UserLoggedOutBroadcaster;
import com.example.application.model.RoflanUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService {
    @Autowired
    private SessionRegistry sessionRegistry;

    private static final String LOGOUT_SUCCESS_URL = "/";

    public List<RoflanUser> getAllAuthenticatedUsers() {
        return sessionRegistry.getAllPrincipals().stream()
                .map(principal -> (RoflanUser) principal)
                .collect(Collectors.toList());
    }

    public RoflanUser getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();

        if (principal instanceof RoflanUser) {
            return (RoflanUser) context.getAuthentication().getPrincipal();
        }

        // Anonymous or no authentication.
        return null;
    }

    public void logout() {
        UserLoggedOutBroadcaster.broadcast(getAuthenticatedUser());
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null);
    }
}
