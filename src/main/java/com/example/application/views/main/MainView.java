package com.example.application.views.main;

import com.example.application.views.login.LoginView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import javax.annotation.security.PermitAll;

@PageTitle("RFLN Connect")
@Route(value = "")
@AnonymousAllowed
public class MainView extends HorizontalLayout {

    public MainView() {
        H1 welcomeMessage = new H1("Welcome to Roflan Connect Community"); //TODO make static text to be configurable
        welcomeMessage.getStyle().set("color", "blue"); //TODO It should be setup in the theme

        Icon icon = new Icon(VaadinIcon.ARROW_RIGHT);
        icon.addClickListener(e -> icon.getUI().ifPresent(ui -> ui.navigate(LoginView.class)));
        icon.getStyle().set("cursor", "pointer");

        H3 loginLinkText = new H3("Go to login page");
        loginLinkText.getStyle().set("margin", "0");

        HorizontalLayout loginLinkPage = new HorizontalLayout(loginLinkText, icon);
        loginLinkPage.setAlignItems(Alignment.CENTER);

        VerticalLayout welcomeVerticalLayout = new VerticalLayout(welcomeMessage, loginLinkPage);
        welcomeVerticalLayout.getStyle().set("margin", "auto");
        welcomeVerticalLayout.setAlignItems(Alignment.CENTER);
        setHeight(80, Unit.VH); // TODO I'd like to move it into separate css file

        add(welcomeVerticalLayout);
    }

}
