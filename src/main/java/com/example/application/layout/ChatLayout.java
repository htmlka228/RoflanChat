package com.example.application.layout;

import com.example.application.broadcast.UserLoggedInBroadcaster;
import com.example.application.broadcast.UserLoggedOutBroadcaster;
import com.example.application.component.LoggedInUserBox;
import com.example.application.security.SecurityService;
import com.example.application.service.RoflanUserService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ChatLayout extends AppLayout {
    private final SecurityService securityService;
    private final RoflanUserService roflanUserService;
    private Registration loggedInBroadcasterRegistration;
    private Registration loggedOutBroadcasterRegistration;
    private VerticalLayout loggedInUsersList = new VerticalLayout();

    public ChatLayout(SecurityService securityService, RoflanUserService roflanUserService) {
        this.securityService = securityService;
        this.roflanUserService = roflanUserService;

        createHeader();
        createDrawer();
    }

    private void createHeader() {
        //TODO Move this styling into css file if possible
        Button logout = new Button("logout", e -> securityService.logout());
        logout.getStyle().set("margin", "0 20px 0 auto");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logout);
        header.setClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        securityService.getAllAuthenticatedUsers().forEach(user -> loggedInUsersList.add(new LoggedInUserBox(user.getUsername())));

        addToDrawer(loggedInUsersList);

        setDrawerOpened(true);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();

        //TODO Come up with other solution to remove user logged in block from ui, array creation is just temporal workaround
        final LoggedInUserBox[] loggedInUserBox = new LoggedInUserBox[1];

        loggedInBroadcasterRegistration = UserLoggedInBroadcaster.register(user -> ui.access(() -> {
            loggedInUserBox[0] = new LoggedInUserBox(user.getUsername());
            loggedInUsersList.add(loggedInUserBox[0]);
        }));

        //TODO delete unused consumer methods from Broadcasts
        loggedOutBroadcasterRegistration = UserLoggedOutBroadcaster.register(user -> ui.access(() -> {
            if (loggedInUserBox[0] != null) {
                loggedInUsersList.remove(loggedInUserBox[0]);
            }
        }));
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        loggedInBroadcasterRegistration.remove();
        loggedInBroadcasterRegistration = null;
    }
}
