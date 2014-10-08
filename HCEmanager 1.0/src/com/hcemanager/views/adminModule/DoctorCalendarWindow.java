package com.hcemanager.views.adminModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.vaadin.ui.Window;

/**
 * @author daniel on 1/10/14.
 */
public class DoctorCalendarWindow extends Window {

    public DoctorCalendarWindow(String doctorId){
        setModal(true);
        setResizable(false);

        try {
            setCaption("Itinerario de citas de  "+
                    SpringServices.getUserDAO().getUserByIdPerson(doctorId).getName());
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        } catch (UserNotExistsException e) {
            e.printStackTrace();
        }
        setWidth(45.0f,Unit.PERCENTAGE);
        setContent(new DoctorCalendarPanel(doctorId));
    }
}
