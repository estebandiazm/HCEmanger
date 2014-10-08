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
public class QueryReasonPanel extends Panel {

    private PreviousDataPanel previousDataPanel;
    private CurrentDataPanel currentDataPanel;

    /**
     * This constructor build the Query reason panel
     * @param medicalAppointments the previous medical appointments
     * @param medicalAppointmentCurrent medical appointment current
     */
    public QueryReasonPanel(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){

        super("Motivo de consulta");
        setIcon(FontAwesome.QUESTION_CIRCLE);

        VerticalLayout layout = new VerticalLayout();
        previousDataPanel = new PreviousDataPanel(getPreviousQueryReasons(medicalAppointments,medicalAppointmentCurrent));
        currentDataPanel = new CurrentDataPanel();
        currentDataPanel.getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                updateDataQueryReason(medicalAppointmentCurrent);
                Notification.show("Razón de la consulta guardada éxitosamenteS");
            }
        });

        layout.addComponent(previousDataPanel);
        layout.addComponent(currentDataPanel);

        this.setContent(layout);
        this.setCSS();
    }

    /**
     * This method update the data of query reason in medical appointment
     * @param medicalAppointmentCurrent to update
     */
    private void updateDataQueryReason(MedicalAppointment medicalAppointmentCurrent){
        medicalAppointmentCurrent.setQueryReason(currentDataPanel.getCurrentDataTextArea().getValue());
        try {
            SpringServices.getMedicalAppointmentDAO().updateMedicalAppointment(medicalAppointmentCurrent);
        } catch (MedicalAppointmentExistsException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method get the query reason in old medical appointments
     * @param medicalAppointments
     * @return
     */
    private String getPreviousQueryReasons(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
            if (medicalAppointment.getIdConsult()!=medicalAppointmentCurrent.getIdConsult()){
                data += "Fecha: "+medicalAppointment.getDate()+"\n";
                data += medicalAppointment.getQueryReason()+"\n \n";
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

    public Panel getCurrentDataPanel() {
        return currentDataPanel;
    }

    public void setCurrentDataPanel(CurrentDataPanel currentDataPanel) {
        this.currentDataPanel = currentDataPanel;
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add("#queryReasonPanel{border-radius:0px !important; height:32em !important;}");
    }
}
