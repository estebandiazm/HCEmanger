package com.hcemanager.views.generalUI;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.users.User;
import com.hcemanager.views.adminModule.DoctorCalendarWindow;
import com.hcemanager.views.adminModule.UserDetailWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class SearchPanel extends Panel implements Button.ClickListener{

    private TextField identificationTextField;
    private Button searchButton;
    private Window window;

    /**
     *
     * @param window
     * @param type
     */
    public SearchPanel (Window window,String type){

        this.window=window;
        VerticalLayout verticalLayout= new VerticalLayout();
        verticalLayout.setStyleName("search-vertical-layout");

        switch (type){
            case SearchWindow.SEARCH_USER:
                identificationTextField = new TextField("Cedula");
                searchButton = new Button("Buscar", FontAwesome.SEARCH);
                searchButton.addClickListener(this);
                searchButton.setStyleName("search-user-button");
                break;
            case SearchWindow.SEARCH_DOCTOR_CALENDAR:
                identificationTextField= new TextField("Identifiación del médico");
                searchButton = new Button("Buscar", FontAwesome.SEARCH);
                searchButton.addClickListener(this);
                searchButton.setStyleName("search-doctor-calendar-button");
                break;
        }

        FormLayout formLayout = new FormLayout();
        formLayout.setStyleName("search-layout");
        formLayout.addComponent(identificationTextField);
        formLayout.addComponent(searchButton);

        verticalLayout.addComponent(formLayout);
        verticalLayout.setMargin(true);
        verticalLayout.setSizeUndefined();

        this.setContent(verticalLayout);
        this.setStyleName("search-panel");
        this.setCSS();

    }


    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String event = clickEvent.getButton().getStyleName();
        String id = identificationTextField.getValue();

        if (event.equals("search-user-button")){
            User user = null;
            try {
                user = SpringServices.getUserDAO().getUserByIdPerson(id);
                UserDetailWindow detailUserWindow = new UserDetailWindow(user);
                window.close();
                getUI().getCurrent().addWindow(detailUserWindow);
            } catch (UserNotExistsException e) {
                e.printStackTrace();
                Notification.show("El usuario no existe en la base de datos",Notification.TYPE_ERROR_MESSAGE);
            } catch (CodeNotExistsException e) {
                e.printStackTrace();
            }

        }else if (event.equals("search-doctor-calendar-button")){
            User user;
            try{
                user= SpringServices.getUserDAO().getUserByIdPerson(id);
                DoctorCalendarWindow calendarWindow = new DoctorCalendarWindow(user.getIdPerson());
                window.close();
                getUI().getCurrent().addWindow(calendarWindow);
            } catch (CodeNotExistsException e) {
                e.printStackTrace();
                Notification.show("Código no existe",Notification.TYPE_ERROR_MESSAGE);
            } catch (UserNotExistsException e) {
                e.printStackTrace();
                Notification.show("El médico no se encuentra en la base de datos",
                        Notification.TYPE_ERROR_MESSAGE);
            }
        }
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".search-layout{margin:10% !important; border:none !important;}" +
                ".search-vertical-layout{width:100% !important;}" +
                ".search-user-button, .search-doctor-calendar-button{width:100% !important;}");
    }
}
