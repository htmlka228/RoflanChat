package com.example.application.layout;

import com.example.application.security.SecurityService;
import com.example.application.service.RoflanUserService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ChatLayout extends AppLayout {
    private final SecurityService securityService;
    private final RoflanUserService roflanUserService;

    public ChatLayout(SecurityService securityService, RoflanUserService roflanUserService) {
        this.securityService = securityService;
        this.roflanUserService = roflanUserService;

        createHeader();
        createDrawer();
    }

    private void createHeader() {
        Button logout = new Button("logout", e -> securityService.logout());
        logout.getStyle().set("margin", "0 20px 0 auto");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logout);
        header.setClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        //TODO Active users should be displayed here
        Icon icon1 = new Icon(VaadinIcon.CIRCLE);
        Icon icon2 = new Icon(VaadinIcon.CIRCLE);
        Icon icon3 = new Icon(VaadinIcon.CIRCLE);


        addToDrawer(new VerticalLayout(
                icon1,
                icon2,
                icon3
        ));


        setDrawerOpened(true);
    }
}
