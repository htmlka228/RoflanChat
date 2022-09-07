package com.example.application.views.chat;

import com.example.application.broadcast.RoflanMessageBroadcaster;
import com.example.application.component.UIRoflanMessage;
import com.example.application.layout.ChatLayout;
import com.example.application.model.RoflanMessage;
import com.example.application.security.SecurityService;
import com.example.application.service.RoflanMessageService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;

@PageTitle("RFLN Connect")
@Route(value = "/chat", layout = ChatLayout.class)
@UIScope
@SpringComponent
@PermitAll
public class ChatView extends VerticalLayout {
    private final RoflanMessageService roflanMessageService;
    private final SecurityService securityService;
    private VerticalLayout messagesLayout = new VerticalLayout();
    private Registration broadcasterRegistration;

    public ChatView(@Autowired RoflanMessageService roflanMessageService,
                    @Autowired SecurityService securityService) {
        this.roflanMessageService = roflanMessageService;
        this.securityService = securityService;

        TextField messageInput = new TextField("Message", "Type your message here...");
        messageInput.setAutofocus(true);
        messageInput.addClassName("roflan-message-input");

        messagesLayout.addClassName("roflan-messages-area");
        messagesLayout.removeAll();
        roflanMessageService.getMessagesHistory().forEach(message -> {
            message.setLabel(message.getRoflanMessage().getUser().getUsername());
            messagesLayout.add(message);
        });


        HorizontalLayout sendMessageLayout = new HorizontalLayout();
        sendMessageLayout.addClassName("roflan-message-block");

        Image sendMessageIcon = new Image("images/sendMessage-512x512.png", "Send Message");
        sendMessageIcon.addClassName("roflan-send-message-icon");

        sendMessageIcon.addClickListener(e -> {
            String messageText = messageInput.getValue();
            if (!StringUtils.isEmpty(messageText)) {
                RoflanMessage message = RoflanMessage.builder()
                        .message(messageText)
                        .user(securityService.getAuthenticatedUser())
                        .sentTime(LocalDateTime.now())
                        .build();

                roflanMessageService.save(message);
                RoflanMessageBroadcaster.broadcast(message);
            }
        });

        sendMessageIcon.addClickShortcut(Key.ENTER);

        sendMessageLayout.add(messageInput, sendMessageIcon);

        addClassName("roflan-chat-wrapper");
        add(messagesLayout, sendMessageLayout);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        broadcasterRegistration = RoflanMessageBroadcaster.register(newMessage -> ui.access(() -> messagesLayout.add(new UIRoflanMessage(newMessage))));
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }
}
