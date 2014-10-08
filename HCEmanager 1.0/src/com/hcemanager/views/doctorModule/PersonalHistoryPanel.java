package com.hcemanager.views.doctorModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.omg.CORBA.Current;

import java.util.List;

/**
 * @author daniel
 */
public class PersonalHistoryPanel extends Panel {

    private PreviousDataPanel previousDataPanel;
    private CurrentDataPanel currentDataPanel;


    /**
     * This constructor build the Personal history panel
     * @param medicalAppointments ald consults of user
     */
    public PersonalHistoryPanel(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){

        super("Antecedentes personales");
        setIcon(FontAwesome.USER);

        VerticalLayout layout = new VerticalLayout();
        previousDataPanel = new PreviousDataPanel(getPreviousPersonalHistory(medicalAppointments, medicalAppointmentCurrent));
        currentDataPanel = new CurrentDataPanel();

        currentDataPanel.getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                updateDataPersonalHistory(medicalAppointmentCurrent);
                Notification.show("Antecedentes personales guardados con éxito",Notification.Type.HUMANIZED_MESSAGE);
            }
        });

        layout.addComponent(previousDataPanel);
        layout.addComponent(currentDataPanel);

        this.setContent(layout);
        this.setCSS();
    }

    private String getPreviousPersonalHistory(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
            if (medicalAppointment.getIdConsult()!=medicalAppointmentCurrent.getIdConsult()){
                data+="Fecha: "+medicalAppointment.getDate()+"\n";
                data+=medicalAppointment.getPersonalHistory();
                data+="\n \n";
            }
        }
        return data;
    }

    private void updateDataPersonalHistory(MedicalAppointment medicalAppointmentCurrent){
        medicalAppointmentCurrent.setPersonalHistory(currentDataPanel.getCurrentDataTextArea().getValue());
        try {
            SpringServices.getMedicalAppointmentDAO().updateMedicalAppointment(medicalAppointmentCurrent);
        } catch (MedicalAppointmentExistsException e) {
            e.printStackTrace();
        }
    }

    public PreviousDataPanel getPreviousDataPanel() {
        return previousDataPanel;
    }

    public void setPreviousDataPanel(PreviousDataPanel previousDataPanel) {
        this.previousDataPanel = previousDataPanel;
    }

    public Panel getCurrentDataPanel() {
        return currentDataPanel;
    }

    public void setCurrentDataPanel(CurrentDataPanel currentDataPanel) {
        this.currentDataPanel = currentDataPanel;
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add("#personalHistoryPanel{border-radius:0px !important; height:32em !important;}");
    }
}
