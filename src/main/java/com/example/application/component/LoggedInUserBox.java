package com.example.application.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

public class LoggedInUserBox extends Div implements ConfigurableStyles{
    private Span username;

    public LoggedInUserBox(String username) {
        this.username = new Span(username);

        configureStyles();
        add(username);
    }

    @Override
    public void configureStyles() {
        addClassName("logged-in-user-box");
        username.addClassName("logged-in-user-box-message");
    }
}
