package com.example.application.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class LoggedInUserBox extends Div implements ConfigurableStyles{
    private Span username;
    private Icon activityCircle;

    public LoggedInUserBox(String username) {
        this.username = new Span(username);
        this.activityCircle = new Icon(VaadinIcon.CIRCLE);

        configureStyles();
        add(this.username, activityCircle);
    }

    @Override
    public void configureStyles() {
        addClassName("logged-in-user-box");
        username.addClassName("logged-in-user-box-username");
        activityCircle.addClassName("logged-in-user-box-activity-circle");
        activityCircle.setColor("#00cc00");
        activityCircle.setSize("15px");
    }
}
