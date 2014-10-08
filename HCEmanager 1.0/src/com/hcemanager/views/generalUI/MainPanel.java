package com.hcemanager.views.generalUI;

import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Page.*;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.io.File;

/**
 * @author daniel, esteban.
 */
public class MainPanel extends HorizontalLayout {

    public final String BASE_PATH;
    public static final String  ADMIN_USER="admin";
    public static final String  DOCTOR_USER="doctor";
    public static final String  PATIENT_USER="patient";
    private Image appLogo;
    private MenuBar optionsMenu;
    final Label selection = new Label("");
    private String username;

    public MenuBar.Command optionsMenuCommand = new MenuBar.Command(){
        public void menuSelected(MenuBar.MenuItem selectedItem){
            selection.setValue("Item seleccionado: "+selectedItem.getText());
            if (selectedItem.getText().equals("Iniciar sesión")){
                getUI().getCurrent().addWindow(new LoginWindow());
            }
            if (selectedItem.getText().equals(" Cambiar contraseña")){
                getUI().getCurrent().addWindow(new ChangePasswordWindow());
            }
            if (selectedItem.getText().equals(" Editar información")){
                getUI().getCurrent().addWindow(new ChangeDataUserWindow(username));
            }
            if (selectedItem.getText().equals(" Cerrar sesión")){
                VerticalLayout layout = (VerticalLayout) getUI().getCurrent().getContent();
                layout.removeAllComponents();
                layout.addComponent(new MainPanel());
                layout.addComponent(new Carousel());
                layout.addComponent(new ModulesPanel());
                layout.addComponent(new Footer());
            }

        }
    };

    /**
     * Default MainPanel constructor
     * @param userName the username
     */
    public MainPanel(String userName,String userType) {
        this.username=userName;
        BASE_PATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

        this.setWidth(100.0f,Unit.PERCENTAGE);
        this.setHeight(10.0f, Unit.PERCENTAGE);
        this.setStyleName("main-panel");

        buildLogo();
        buildMenu(userName,userType);

        setCSS();
    }

    public MainPanel(){
        BASE_PATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

        this.setWidth(100.0f,Unit.PERCENTAGE);
        this.setHeight(10.0f, Unit.PERCENTAGE);
        this.setStyleName("main-panel");

        buildLogo();
        buildMenu();

        setCSS();
    }
    public String getBASE_PATH() {
        return BASE_PATH;
    }

    public Image getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(Image appLogo) {
        this.appLogo = appLogo;
    }

    public MenuBar getOptionsMenu() {
        return optionsMenu;
    }

    public void setOptionsMenu(MenuBar optionsMenu) {
        this.optionsMenu = optionsMenu;
    }

    public Label getSelection() {
        return selection;
    }

    public MenuBar.Command getOptionsMenuCommand() {
        return optionsMenuCommand;
    }

    public void setOptionsMenuCommand(MenuBar.Command optionsMenuCommand) {
        this.optionsMenuCommand = optionsMenuCommand;
    }

    /**
     * This method make the app logo
     */
    private void buildLogo(){
        FileResource logoFile = new FileResource(new File(BASE_PATH+"/WEB-INF/images/hce_manager.png"));
        System.out.println("path:"+BASE_PATH);
        appLogo = new Image();
        appLogo.setSource(logoFile);
        appLogo.setStyleName("app-logo");
        this.addComponent(appLogo);
    }

    /**
     * Make the options menu
     */
    private void buildMenu(String username,String userType){

        optionsMenu = new MenuBar();
        optionsMenu.setStyleName("options-menu");

        MenuBar.MenuItem user = null;

        switch (userType){
            case ADMIN_USER:
            case PATIENT_USER:
                user = optionsMenu.addItem(" "+username,FontAwesome.USER,null);
                break;
            case DOCTOR_USER:
                 user = optionsMenu.addItem(" "+username, FontAwesome.USER_MD, null);
                break;
        }

        try {
            user.addItem(" Cambiar contraseña",FontAwesome.KEY,optionsMenuCommand);
            user.addItem(" Editar información",FontAwesome.EDIT,optionsMenuCommand);
            user.addItem(" Cerrar sesión",FontAwesome.SIGN_OUT,optionsMenuCommand);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        this.addComponent(optionsMenu);
    }

    public void buildMenu(){
        optionsMenu = new MenuBar();
        optionsMenu.setStyleName("options-menu");
        MenuBar.MenuItem login = optionsMenu.addItem("Iniciar sesión",FontAwesome.SIGN_IN,optionsMenuCommand);
        this.addComponent(optionsMenu);
    }

    /**
     * Build the panel style sheet
     */
    private void setCSS(){
        Styles styles = Page.getCurrent().getStyles();
        styles.add(".main-panel{background:rgb(74,94,189);}" +
                ".app-logo{width:35%; margin:6%; margin-left:17%}" +
                ".options-menu{float:right; margin-right:17%; margin-top:5%;box-shadow: 0 0 0 0 !important;}" +
                ".v-menubar{background:rgb(74,94,189) !important; color:white !important; position:static !important;" +
                "border-radius:0 !important; border: none !important;}" +
                ".valo .v-menubar > .v-menubar-menuitem{border:none !important; background:rgb(74,94,189) !important;}" +
                ".valo .v-menubar > .v-menubar-menuitem:first-child{border-radius: 0 !important;}" +
                ".valo .v-menubar > .v-menubar-menuitem:last-child{border-radius: 0 !important;}" +
                ".v-menubar-submenu{margin:0;}" +
                ".v-menubar-popup{border-radius:0 !important; background: rgba(255,255,255,0.83) !important;}");

    }
}