package com.acme.youTask.web.controller;

import com.acme.youTask.business.TaskService;
import com.acme.youTask.business.UserService;
import com.acme.youTask.domain.User;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Named
@SessionScoped
public class UserController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    Messages messages;

    @Inject
    UserService userService;

    @Inject
    TaskService taskService;

    // User("xXx", "vin", "diesel", "triple@xXx.com")
    private User user;


// -------------- Initialization ------------------------------------------------------

    @PostConstruct
    public void init() {
//        user = userService.loadByUsername("xXx");
        this.user = new User();
    }


// -------------- Actions -------------------------------------------------------------
    public void saveUser() {
        userService.saveUser(user);
        messages.info(new BundleKey("messages", "com.acme.youTask.msg.user.saved" + user.getLogin()));
    }

    public void removeUser() {
        if (userService.userExists(user.getId())) {
            userService.removeUser(user);
            messages.info(new BundleKey("messages", "com.acme.youTask.msg.user.removeSuc" + user.getLogin()));
        } else {
            messages.error(new BundleKey("messages", "com.acme.youTask.msg.user.removeFail" + user.getLogin()));
        }

    }

    public void updateUser() {
        if (userService.userExists(user.getId())) {
            userService.mergeUser(user);
            messages.info(new BundleKey("messages", "com.acme.youTask.msg.user.updateSuc" + user.getLogin()));
        } else {
            messages.error(new BundleKey("messages", "com.acme.youTask.msg.user.updateFail" + user.getLogin()));
        }
    }


// -------------- Controller Helper ---------------------------------------------------
    public List<User> getUsers() {
        List<User> users = userService.loadAll();

        return users;
    }


// -------------- Getter/Setter -------------------------------------------------------

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
