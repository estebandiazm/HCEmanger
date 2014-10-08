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
 * @author Daniel Bellon & Juan Diaz
 */
public class FamilyHistoryPanel extends Panel {

    private PreviousDataPanel previousDataPanel;
    private CurrentDataPanel currentDataPanel;

    public FamilyHistoryPanel(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        super("Antecedentes familiares");
        setIcon(FontAwesome.USERS);

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        previousDataPanel = new PreviousDataPanel(getPreviousFamilyHistory(medicalAppointments,medicalAppointmentCurrent));
        currentDataPanel = new CurrentDataPanel();

        currentDataPanel.getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                updateDataFamilyHistory(medicalAppointmentCurrent);
                Notification.show("Antecedentes familiares guardados éxitosamente", Notification.Type.HUMANIZED_MESSAGE);

            }
        });

        verticalLayout.addComponent(previousDataPanel);
        verticalLayout.addComponent(currentDataPanel);
        setCSS();
    }

    private void updateDataFamilyHistory(MedicalAppointment medicalAppointmentCurrent){
        medicalAppointmentCurrent.setFamilyHistory(currentDataPanel.getCurrentDataTextArea().getValue());
        try {
            SpringServices.getMedicalAppointmentDAO().updateMedicalAppointment(medicalAppointmentCurrent);
        } catch (MedicalAppointmentExistsException e) {
            e.printStackTrace();
        }
    }

    private String getPreviousFamilyHistory(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
            if (medicalAppointment.getIdConsult()!=medicalAppointmentCurrent.getIdConsult()){
                data+="Fecha: "+medicalAppointment.getDate()+"\n";
                data+=medicalAppointment.getFamilyHistory();
                data+="\n \n";
            }
        }
        return data;
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add("#familyHistoryPanel{border-radius:0px !important; height:32em !important;}");
    }
}
