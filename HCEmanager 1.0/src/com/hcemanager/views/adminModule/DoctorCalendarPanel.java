package com.hcemanager.views.adminModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentNotExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.vaadin.server.Page;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * @author daniel on 1/10/14.
 */
public class DoctorCalendarPanel extends Panel{

    private Table medicalAppointmentsTable;

    public DoctorCalendarPanel(String doctorId){

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setStyleName("doctor-calendar-layout");

        medicalAppointmentsTable = new Table();
        medicalAppointmentsTable.setId("doctor-calendar-table");
        medicalAppointmentsTable.setWidth(90.0f,Unit.PERCENTAGE);
        medicalAppointmentsTable.setHeight(15.0f,Unit.EM);
        medicalAppointmentsTable.addContainerProperty("Día", String.class, null);
        medicalAppointmentsTable.addContainerProperty("Hora", String.class, null);
        medicalAppointmentsTable.addContainerProperty("Paciente", String.class, null);

        try {
            setMedicalAppointmentsTable(doctorId);
        } catch (MedicalAppointmentNotExistsException e) {
            e.printStackTrace();
        }

        verticalLayout.addComponent(medicalAppointmentsTable);

        this.setContent(verticalLayout);
        this.setCSS();
    }

    private void setMedicalAppointmentsTable(String doctorId) throws MedicalAppointmentNotExistsException {

        List<MedicalAppointment>medicalAppointmentList =
                SpringServices.getMedicalAppointmentDAO().getMedicalAppointments();
        for (int i =0; i<medicalAppointmentList.size();i++){
            if(medicalAppointmentList.get(i).getDoctor().getIdPerson().equals(doctorId)){
                medicalAppointmentsTable.addItem(new Object[]{
                        toDay(medicalAppointmentList.get(i).getDate()),
                        toHour(medicalAppointmentList.get(i).getDate()),
                        medicalAppointmentList.get(i).getPatient().getName()
                },i);
            }
        }
    }

    private void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add("#doctor-calendar-table{margin:5% !important;}");
    }

    private String toDay(String date){
        return date.substring(0,10);
    }

    private String toHour(String date){
        return date.substring(10,16);
    }
}
