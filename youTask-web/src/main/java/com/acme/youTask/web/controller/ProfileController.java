package com.acme.youTask.web.controller;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 */
@Named
@SessionScoped
public class ProfileController extends UserController {

    private static final long serialVersionUID = 1L;

    @Inject
    private Messages messages;

//    @NotNull
//    private String confirmPassword;

    private boolean editable;

    // -------------- Initialization ------------------------------------------------------
    @PostConstruct
    public void init() {
        editable = false;
    }

// -------------- Actions -------------------------------------------------------------
    public void enableEditMode() {
        setEditable(true);
        messages.info(new BundleKey("messages", "com.acme.youTask.msg.profile.editEnabled"));
    }

    public void disableEditMode() {
        setEditable(false);
        messages.info(new BundleKey("messages", "com.acme.youTask.msg.profile.editDisabled"));
    }

// -------------- Getter / Setter -----------------------------------------------------
    public boolean getEditable() {
        return this.editable;
    }

    public void setEditable(final boolean editable) {
        this.editable = editable;
    }
}
