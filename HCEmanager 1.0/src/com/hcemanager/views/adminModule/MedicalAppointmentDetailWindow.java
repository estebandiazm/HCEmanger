package com.hcemanager.views.adminModule;

import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.hcemanager.models.users.User;
import com.vaadin.ui.Window;

/**
 * @author daniel on 30/09/14.
 */
public class MedicalAppointmentDetailWindow extends Window {

    /**
     * THe constructor that builds a simple MedicalAppointmentDetailWindow
     */
    public MedicalAppointmentDetailWindow(){
        setModal(true);
        setWidth(36.0f,Unit.PERCENTAGE);
        setCaption("Nueva cita médica");
        setResizable(false);
        setContent(new MedicalAppointmentDetail(this));
    }

    /**
     * The constructor that builds a MedicalAppointmentDetailWindow whit particular information
     * @param medicalAppointment a MedicalAppointment Object
     */
    public MedicalAppointmentDetailWindow(MedicalAppointment medicalAppointment){
        setModal(true);
        setWidth(36.0f,Unit.PERCENTAGE);
        setCaption("Nueva cita médica");
        setResizable(false);
        setContent(new MedicalAppointmentDetail(medicalAppointment,this));
    }
}
