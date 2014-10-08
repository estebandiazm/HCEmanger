package com.hcemanager.views.generalUI;

import com.vaadin.ui.Window;

import java.lang.invoke.SwitchPoint;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class SearchWindow extends Window{

    public static final String SEARCH_USER = "User";
    public static final String SEARCH_DOCTOR_CALENDAR = "Doctor-calendar";

    public SearchWindow (String type){

        switch (type){
            case SEARCH_USER:
                setCaption("Buscar Usuario");
                setWidth(28.0f,Unit.PERCENTAGE);
                break;
            case SEARCH_DOCTOR_CALENDAR:
                setCaption("Calendario de personal médico");
                setWidth(37.0f,Unit.PERCENTAGE);
                break;
        }

        setModal(true);
        setResizable(false);
        setContent(new SearchPanel(this,type));
    }
}
