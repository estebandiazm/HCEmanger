package com.hcemanager.views.generalUI;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.exceptions.users.UserExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.users.User;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class ChangePasswordWindow extends Window implements Button.ClickListener{

    private TextField usernameTextField;
    private PasswordField oldPasswordField;
    private PasswordField newPasswordField;
    private Button changeButton;

    public ChangePasswordWindow(){

        setContent(buildChangePasswordPanel());
        setCaption("Cambio de contraseña");
        setWidth(34.0f,Unit.PERCENTAGE);
        setModal(true);
        setResizable(false);
        setCSS();
    }

    public Panel buildChangePasswordPanel(){
        Panel changePasswordPanel = new Panel();
        changePasswordPanel.setWidth(100.0f,Unit.PERCENTAGE);
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100.0f,Unit.PERCENTAGE);
        layout.setStyleName("change-password-layout");
        FormLayout formLayout = new FormLayout();
        formLayout.setStyleName("change-pass-layout");

        usernameTextField = new TextField("Username");
        usernameTextField.setWidth(76.0f,Unit.PERCENTAGE);
        oldPasswordField = new PasswordField("Contraseña antigua");
        oldPasswordField.setWidth(76.0f,Unit.PERCENTAGE);
        newPasswordField = new PasswordField("Contraseña nueva");
        newPasswordField.setWidth(76.0f,Unit.PERCENTAGE);
        changeButton = new Button("Cambiar", FontAwesome.CHECK);
        changeButton.setStyleName("chabge-button");
        changeButton.addClickListener(this);
        changeButton.setWidth(76.0f,Unit.PERCENTAGE);

        formLayout.addComponent(usernameTextField);
        formLayout.addComponent(oldPasswordField);
        formLayout.addComponent(newPasswordField);
        formLayout.addComponent(changeButton);

        layout.addComponent(formLayout);

        changePasswordPanel.setContent(layout);

        return changePasswordPanel;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String event = clickEvent.getButton().getCaption();
        String idUser = usernameTextField.getValue();
        if (event.equals(changeButton.getCaption())){
            User user = null;
            try {
                user= SpringServices.getUserDAO().getUser(idUser);
            } catch (UserNotExistsException e) {
                e.printStackTrace();
            } catch (CodeNotExistsException e) {
                e.printStackTrace();
            }
            if (user.getPassword().equals(oldPasswordField.getValue())){
                user.setPassword(newPasswordField.getValue());
                try {
                    SpringServices.getUserDAO().updateUser(user);
                    close();
                } catch (UserExistsException e) {
                    e.printStackTrace();
                } catch (EntityExistsException e) {
                    e.printStackTrace();
                } catch (LivingSubjectExistsException e) {
                    e.printStackTrace();
                } catch (CodeExistsException e) {
                    e.printStackTrace();
                } catch (LivingSubjectNotExistsException e) {
                    e.printStackTrace();
                } catch (PersonExistException e) {
                    e.printStackTrace();
                } catch (CodeNotExistsException e) {
                    e.printStackTrace();
                } catch (PersonNotExistException e) {
                    e.printStackTrace();
                } catch (EntityNotExistsException e) {
                    e.printStackTrace();
                } catch (UserNotExistsException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }else{
                Notification.show("Contraseña incorrecta", Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    private void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".change-pass-layout{margin: 8% !important;}");
    }
}
