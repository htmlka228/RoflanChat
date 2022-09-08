package com.example.application.security;

import com.example.application.service.RoflanUserService;
import com.example.application.view.login.LoginView;
import com.vaadin.flow.spring.security.VaadinSavedRequestAwareAuthenticationSuccessHandler;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private RoflanUserService roflanUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Set default security policy that permits Vaadin internal requests and
        // denies all other

        http.userDetailsService(roflanUserService)
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                    session.maximumSessions(-1);
                });
        super.configure(http);
        setLoginView(http, LoginView.class, "/logout");

        // Custom url to redirect after logging in
        http.formLogin().successHandler(getVaadinSavedRequestAwareAuthenticationSuccessHandler(http));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/api/**", "/images/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from roflan_user where username = ?")
                .authoritiesByUsernameQuery("select roflan_user.username, role.name from roflan_user_roles" +
                        " join roflan_user on roflan_user.uuid = roflan_user_roles.roflan_user_uuid" +
                        " join role on role.id = roflan_user_roles.roles_id" +
                        " where username = ?"
                )
                .rolePrefix("ROLE_")
                .passwordEncoder(bCryptPasswordEncoder());
    }

    private VaadinSavedRequestAwareAuthenticationSuccessHandler getVaadinSavedRequestAwareAuthenticationSuccessHandler(HttpSecurity http) {
        VaadinSavedRequestAwareAuthenticationSuccessHandler vaadinSavedRequestAwareAuthenticationSuccessHandler = new VaadinSavedRequestAwareAuthenticationSuccessHandler();
        vaadinSavedRequestAwareAuthenticationSuccessHandler.setDefaultTargetUrl(applyUrlMapping("/chat"));
        RequestCache requestCache = http.getSharedObject(RequestCache.class);
        if (requestCache != null) {
            vaadinSavedRequestAwareAuthenticationSuccessHandler.setRequestCache(requestCache);
        }

        return vaadinSavedRequestAwareAuthenticationSuccessHandler;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher appEventPublisher) {
        return new DefaultAuthenticationEventPublisher(appEventPublisher);
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
