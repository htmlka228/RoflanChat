package com.example.application.component;

import com.example.application.model.RoflanMessage;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

public class UIRoflanMessage extends Div implements HasLabel, ConfigurableStyles {
    private Span label;
    private Span message;
    private RoflanMessage roflanMessage;

    public UIRoflanMessage(RoflanMessage roflanMessage) {
        this.label = new Span();
        this.message = new Span();

        setRoflanMessage(roflanMessage);
        configureStyles();
        add(label, message);
    }

    @Override
    public void configureStyles() {
        addClassName("roflan-message-box");
        label.addClassName("roflan-message-box-label");
        message.addClassName("roflan-message-box-text");
    }

    public RoflanMessage getRoflanMessage() {
        return roflanMessage;
    }

    public void setRoflanMessage(RoflanMessage roflanMessage) {
        this.roflanMessage = roflanMessage;
        this.label.setText(roflanMessage.getUser().getUsername());
        this.message.setText(roflanMessage.getMessage());
    }

    public String getMessage() {
        return roflanMessage.getMessage();
    }

    public void setMessage(String value) {
        roflanMessage.setMessage(value);
        message.setText(value);
    }
}
