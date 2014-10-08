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
public class PhysicalExaminationPanel extends Panel {

    private PreviousDataPanel previousDataPanel;
    private CurrentDataPanel currentDataPanel;


    /**
     * This constructor build the Physical examination panel
     * @param medicalAppointments the previous medical appointments
     */
    public PhysicalExaminationPanel(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){

        super("Examen físico");
        setIcon(FontAwesome.BAR_CHART_O);

        VerticalLayout layout = new VerticalLayout();
        previousDataPanel = new PreviousDataPanel(getPreviousPhysicalExaminationPanel(medicalAppointments,medicalAppointmentCurrent));
        currentDataPanel = new CurrentDataPanel();
        currentDataPanel.getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                updateDataPhysicalExamination(medicalAppointmentCurrent);
                Notification.show("Examen físico guardado éxitosamente");
            }
        });

        layout.addComponent(previousDataPanel);
        layout.addComponent(currentDataPanel);

        this.setContent(layout);
        this.setCSS();
    }

    /**
     * This method updates the data of physical examination in the medical appointment
     * @param medicalAppointmentCurrent
     */
    private void updateDataPhysicalExamination(MedicalAppointment medicalAppointmentCurrent){
        medicalAppointmentCurrent.setPhysicalExamination(currentDataPanel.getCurrentDataTextArea().getValue());
        try {
            SpringServices.getMedicalAppointmentDAO().updateMedicalAppointment(medicalAppointmentCurrent);
        } catch (MedicalAppointmentExistsException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method get the physical examinations in the old medical appointments
     * @param medicalAppointments
     * @return
     */
    private String getPreviousPhysicalExaminationPanel(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
            if (medicalAppointment.getIdConsult()!=medicalAppointmentCurrent.getIdConsult()){
                data += "Fecha: "+medicalAppointment.getDate()+"\n";
                data += medicalAppointment.getPhysicalExamination()+"\n \n";
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
        styles.add("#physicalExaminationPanel{border-radius:0px !important; height:32em !important;}");
    }
}
