package com.hcemanager.views.adminModule;

import com.hcemanager.models.users.User;
import com.hcemanager.views.generalUI.SearchWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class UsersPanel extends Panel {

    private Label titleLabel;
    private Table usersTable;
    private Button searchButton;
    private Button newUserButton;
    public static final String DOCTORS = "doctor";
    public static final String PATIENTS = "patient";


    public UsersPanel (String title,List<User> userList){
        setStyleName("users-panel");
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        switch (title){
            case DOCTORS:
                titleLabel = new Label("Personal médico");
                titleLabel.setIcon(FontAwesome.USER_MD);
                titleLabel.setStyleName("users-title-label");
                break;
            case PATIENTS:
                titleLabel = new Label("Pacientes");
                titleLabel.setIcon(FontAwesome.USER);
                titleLabel.setStyleName("users-title-label");
                break;
        }

        searchButton = new Button("Buscar", FontAwesome.SEARCH);
        searchButton.setWidth(70.0f,Unit.PERCENTAGE);
        newUserButton = new Button("Nuevo",FontAwesome.PLUS);
        newUserButton.setWidth(70.0f,Unit.PERCENTAGE);

        newUserButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UserDetailWindow newUserWindow = new UserDetailWindow(title);
                getUI().addWindow(newUserWindow);
            }
        });

        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                SearchWindow searchWindow = new SearchWindow("User");
                getUI().addWindow(searchWindow);
            }
        });

        usersTable = new Table();
        usersTable.setWidth(90.0f,Unit.PERCENTAGE);
        usersTable.setHeight(26.0f, Unit.EM);
        usersTable.setSelectable(true);
        usersTable.setId("users-table");
        usersTable.addContainerProperty("Nombres",String.class,null);
        usersTable.addContainerProperty("Cedula",String.class,null);
        usersTable.addContainerProperty("Especialidad",String.class,null);
        usersTable.addContainerProperty("Detalle",Button.class,null);

        for (int i=0;i<userList.size();i++){
            User user = userList.get(i);
            if (user.getTypeUser().equals(title)) {
                Button button = new Button("Ver");
                button.setId("view-user-button");
                button.setIcon(FontAwesome.EYE);
                button.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        UserDetailWindow detailUserWindow= new UserDetailWindow(user);
                        getUI().getCurrent().addWindow(detailUserWindow);
                    }
                });
                usersTable.addItem(new Object[]{user.getName(), user.getIdPerson(), user.getTypeUser(), button },i);
            }
        }


        horizontalLayout.setStyleName("users-horizontal-layout");
        horizontalLayout.setMargin(true);
        horizontalLayout.setWidth(100.0f,Unit.PERCENTAGE);
        horizontalLayout.addComponent(titleLabel);
        horizontalLayout.addComponent(searchButton);
        horizontalLayout.addComponent(newUserButton);

        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.addComponent(usersTable);

        setContent(verticalLayout);
        setCSS();
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    public Table getUsersTable() {
        return usersTable;
    }

    public void setUsersTable(Table usersTable) {
        this.usersTable = usersTable;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }

    public Button getNewUserButton() {
        return newUserButton;
    }

    public void setNewUserButton(Button newUserButton) {
        this.newUserButton = newUserButton;
    }


    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".users-panel{margin-top:3% !important;" +
                "margin-left:8% !important;" +
                "border-radius: 0px !important;" +
                "margin-bottom:3% !important;" +
                "width:84% !important;}" +
                "#users-table{margin-left:5% !important;" +
                "margin-bottom:5% !important;}" +
                ".users-horizontal-layout{margin-left:5% !important;}" +
                ".v-caption-users-title-label{display:inline !important; font-size: 1.4em !important}" +
                ".users-title-label{display:inline !important; font-size:1.4em !important; " +
                "font-weight: bold !important; margin-left:5% !important;}" +
                "#view-user-button{width: 60% !important ;margin-left: 20% !important; background: transparent !important;" +
                "box-shadow: 0 0 0 0 !important; color: rgb(84,170,255) !important;}" +
                ".v-textfield, .v-filterselect{border-radius:0px !important;}");
    }
}
