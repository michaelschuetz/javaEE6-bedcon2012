package com.acme.youTask.web.controller;

import com.acme.youTask.business.UserService;
import com.acme.youTask.domain.User;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 *
 */
@Named
@SessionScoped
public class LoginController implements Serializable {

    private String loginName;
    private String password;
    private boolean loggedIn;

    @Inject
    UserService userService;

    @Inject
    ProfileController profileCtrl;

    @Inject
    Messages messages;

// -------------- Initialization ------------------------------------------------------

    @PostConstruct
    public void init() {
        // init user example
        User user = userService.loadByUsername("xXx");

        this.loginName = user.getLogin();
        this.password = user.getPassword();
    }

// -------------- Actions -------------------------------------------------------------

    public String login() {
        User user = this.userService.loadByUsername(this.loginName);
        String forward;

        if (user.getPassword().equals(this.password)) {
            forward = "succeed";
            messages.info(new BundleKey("messages", "com.acme.youTask.msg.login.succeed"));

            this.loggedIn = true;
            this.profileCtrl.setUser(user);
        } else {
            forward = null;
            messages.error(new BundleKey("messages", "com.acme.youTask.msg.login.failed"));
            this.loggedIn = false;
        }

        return forward;
    }

    public void logout() {
        this.loggedIn = false;
        this.profileCtrl.setUser(new User());
    }

// -------------- Getter/Setter -------------------------------------------------------

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
