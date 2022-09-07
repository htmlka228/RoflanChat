package com.example.application.component;

import com.example.application.model.RoflanMessage;
import com.vaadin.flow.component.textfield.TextField;

public class UIRoflanMessage extends TextField {
    private RoflanMessage roflanMessage;

    public UIRoflanMessage(RoflanMessage roflanMessage) {
        this.roflanMessage = roflanMessage;
        super.setValue(roflanMessage.getMessage());
        super.setLabel(roflanMessage.getUser().getUsername());
        configureMessage();
    }

    @Override
    public String getValue() {
        return roflanMessage.getMessage();
    }

    @Override
    public void setValue(String value) {
        if (roflanMessage == null) {
            roflanMessage = new RoflanMessage();
        }

        roflanMessage.setMessage(value);
        super.setValue(value);
    }

    public RoflanMessage getRoflanMessage() {
        return roflanMessage;
    }

    public void setRoflanMessage(RoflanMessage roflanMessage) {
        this.roflanMessage = roflanMessage;
        super.setValue(roflanMessage.getMessage());
    }

    public void configureMessage() {
        setReadOnly(true);
        addClassName("roflan-message");
    }
}
