package com.hcemanager.views.generalUI;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.users.User;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.io.File;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class LoginPanel extends Panel implements Button.ClickListener{


    public final String BASE_PATH;
    private TextField userTextField;
    private PasswordField passwordField;
    private Button loginButton;
    private Image logo;
    private Window window;

    public LoginPanel(Window window){

        BASE_PATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        this.window=window;
        this.setStyleName("login-panel");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setStyleName("login-layout");
        FormLayout formLayout = new FormLayout();
        formLayout.setStyleName("login-form-layout");

        logo = new Image();
        logo.setStyleName("login-logo");
        buildImage(logo, "hce_manager.png");
        userTextField = new TextField("Usuario");
        userTextField.setId("user-text-login");
        userTextField.setStyleName("login-form-component");
        passwordField = new PasswordField("Contraseña");
        passwordField.setId("password-text-login");
        passwordField.setStyleName("login-form-component");
        loginButton = new Button("Iniciar sesión", FontAwesome.SIGN_IN);
        loginButton.setId("login-button");
        loginButton.setStyleName("login-form-component");
        loginButton.addClickListener(this);

        formLayout.addComponent(userTextField);
        formLayout.addComponent(passwordField);
        formLayout.addComponent(loginButton);
        formLayout.setSizeUndefined();

        verticalLayout.addComponent(logo);
        verticalLayout.addComponent(formLayout);
        verticalLayout.setMargin(true);

        this.setContent(verticalLayout);
        this.setCSS();
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
       String event = clickEvent.getButton().getCaption();

        if (event.equals("Iniciar sesión")){
            validateLogin(userTextField.getValue(), passwordField.getValue());
        }
    }

    public void validateLogin(String username, String password){
        try {
            User user = SpringServices.getUserDAO().getUser(username);
            if (user.getPassword().equals(password)){
                Notification.show("Bienvenido "+ userTextField.getValue());
                VerticalLayout layout = (VerticalLayout) getUI().getContent();
                layout.removeAllComponents();
                layout.addComponent(new MainPanel(user.getIdUser(), user.getTypeUser()));
                layout.addComponent(new NavigationPanel(user.getTypeUser(),username));
                layout.addComponent(new Footer(user.getTypeUser()));
                window.close();
            }else {
                Notification.show("Contraseña o usuario incorrecto", Notification.Type.ERROR_MESSAGE);
            }

        } catch (UserNotExistsException e) {
            Notification.show("Usuario no existe",Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }

    }

    public void buildImage(Image image,String imageName){
        FileResource imageFile = new FileResource(new File(BASE_PATH+"/WEB-INF/images/"+imageName));
        System.out.println("path:"+BASE_PATH);
        image.setSource(imageFile);
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".login-window{width:30% !important;}" +
                ".login-panel{background: rgb(74,94,178) !important; border-top-left-radius: 0px !important;" +
                "border-top-right-radius:0px !important;}" +
                ".login-logo{width: 55% !important;margin-top: 10% !important;margin-left: 23% !important;" +
                "margin-bottom: 15% !important;}" +
                ".login-form-layout{margin-left: 10% !important; margin-bottom: 10% !important; color:white;}" +
                ".login-form-component{border-radius: 0px !important;margin-bottom: 5% !important;}" +
                "#login-button{width:100% !important; color: white; background: rgb(84,170,255) !important;" +
                "border: none !important; box-shadow: 0 0 0 0 !important;}");
    }
}
