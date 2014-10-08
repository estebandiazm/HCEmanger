package com.hcemanager.views.doctorModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * @author daniel
 */
public class HabitsPanel extends Panel{

    private PreviousDataPanel previousDataPanel;
    private CurrentDataPanel currentDataPanel;

    /**
     * This constructor build the Habits Panel
     * @param medicalAppointments the previous medical appointments
     */
    public HabitsPanel (List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        super("Hábitos");
        setIcon(FontAwesome.HEART_O);

        VerticalLayout layout = new VerticalLayout();
        previousDataPanel = new PreviousDataPanel(getPreviousHabits(medicalAppointments,medicalAppointmentCurrent));
        currentDataPanel = new CurrentDataPanel();
        currentDataPanel.getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                updateDataHabits(medicalAppointmentCurrent);
                Notification.show("Hábitos guardados éxitosamente", Notification.Type.HUMANIZED_MESSAGE);
            }
        });

        layout.addComponent(previousDataPanel);
        layout.addComponent(currentDataPanel);

        this.setContent(layout);
        this.setCSS();
    }

    private void updateDataHabits(MedicalAppointment medicalAppointmentCurrent){
        medicalAppointmentCurrent.setHabits(currentDataPanel.getCurrentDataTextArea().getValue());
        try {
            SpringServices.getMedicalAppointmentDAO().updateMedicalAppointment(medicalAppointmentCurrent);
        } catch (MedicalAppointmentExistsException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method get the habits in previous medical appointments
     * @param medicalAppointments
     * @return
     */
    private String getPreviousHabits(List<MedicalAppointment> medicalAppointments,MedicalAppointment medicalAppointmentCurrent){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
            if (medicalAppointment.getIdConsult()!=medicalAppointmentCurrent.getIdConsult()){
                data+="Fecha: "+medicalAppointment.getDate()+"\n";
                data+=medicalAppointment.getHabits()+"\n \n";
            }
        }
        return data;
    }


    public PreviousDataPanel getPreviousDataPanel() {
        return previousDataPanel;
    }

    public void setPreviousDataPanel(PreviousDataPanel previousDataPanel) {
        this.previousDataPanel = previousDataPanel;
    }

    public CurrentDataPanel getCurrentDataPanel() {
        return currentDataPanel;
    }

    public void setCurrentDataPanel(CurrentDataPanel currentDataPanel) {
        this.currentDataPanel = currentDataPanel;
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add("#habitsPanel{border-radius:0px !important; height:32em !important;}");
    }
}
