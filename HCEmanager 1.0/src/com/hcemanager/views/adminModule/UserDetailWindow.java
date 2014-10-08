package com.hcemanager.views.adminModule;

import com.hcemanager.models.users.User;
import com.vaadin.ui.Window;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class UserDetailWindow extends Window {

    public UserDetailWindow(String type){
        setModal(true);
        setCaption("Nuevo Usuario");
        setResizable(false);
        setContent(new UserDetailPanel(this,type));
    }

    public UserDetailWindow(User user){
        setModal(true);
        setCaption("Usuario: "+user.getName());
        setContent(new UserDetailPanel(user,this));
        setResizable(false);
    }
}
