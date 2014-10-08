package com.hcemanager.views.generalUI;

import com.vaadin.ui.Window;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class LoginWindow extends Window {

    public LoginWindow (){
        this.setStyleName("login-window");
        this.setCaption("Iniciar Sesión");
        this.setContent(new LoginPanel(this));
        this.setModal(true);
        this.setResizable(false);
    }
}
