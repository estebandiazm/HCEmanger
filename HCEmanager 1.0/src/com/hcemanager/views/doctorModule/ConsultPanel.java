package com.hcemanager.views.doctorModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentNotExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.hcemanager.models.users.User;
import com.vaadin.server.Page;
import com.vaadin.server.Page.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class ConsultPanel extends Panel implements Button.ClickListener{

    private VitalSignsPanel vitalSignsPanel;
    private PersonalHistoryPanel personalHistoryPanel;
    private FamilyHistoryPanel familyHistoryPanel;
    private HabitsPanel habitsPanel;
    private QueryReasonPanel queryReasonPanel;
    private PhysicalExaminationPanel physicalExaminationPanel;
    private DiagnosticPanel diagnosticPanel;
    private ImagesPanel imagesPanel;
    private ConsultMenuPanel consultMenuPanel;
    private HorizontalLayout horizontalLayout;
    List<MedicalAppointment> medicalAppointments;

    public ConsultPanel (User user,MedicalAppointment medicalAppointmentCurrent){

        getMedicalAppointments(user);

        horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setStyleName("consult-panel");

        consultMenuPanel = new ConsultMenuPanel();
        consultMenuPanel.setStyleName("query-menu");
        consultMenuPanel.setSizeFull();
        consultMenuPanel.setHeight("100%");

        consultMenuPanel.getVitalSingsButton().addClickListener(this);
        consultMenuPanel.getPersonalHistoryButton().addClickListener(this);
        consultMenuPanel.getFamilyHistoryButton().addClickListener(this);
        consultMenuPanel.getHabitsButton().addClickListener(this);
        consultMenuPanel.getQueryReasonButton().addClickListener(this);
        consultMenuPanel.getPhysicalExaminationButton().addClickListener(this);
        consultMenuPanel.getDiagnosticButton().addClickListener(this);
        consultMenuPanel.getImageButton().addClickListener(this);


        vitalSignsPanel = new VitalSignsPanel(medicalAppointments, medicalAppointmentCurrent);
        vitalSignsPanel.setId("vitalSignsPanel");
        vitalSignsPanel.setStyleName("details-panel");

        personalHistoryPanel = new PersonalHistoryPanel(medicalAppointments, medicalAppointmentCurrent);
        personalHistoryPanel.setId("personalHistoryPanel");
        personalHistoryPanel.setStyleName("details-panel");

        familyHistoryPanel = new FamilyHistoryPanel(medicalAppointments,medicalAppointmentCurrent);
        familyHistoryPanel.setId("familyHistoryPanel");
        familyHistoryPanel.setStyleName("details-panel");

        habitsPanel = new HabitsPanel(medicalAppointments, medicalAppointmentCurrent);
        habitsPanel.setId("habitsPanel");
        habitsPanel.setStyleName("details-panel");

        queryReasonPanel = new QueryReasonPanel(medicalAppointments, medicalAppointmentCurrent);
        queryReasonPanel.setId("queryReasonPanel");
        queryReasonPanel.setStyleName("details-panel");

        physicalExaminationPanel = new PhysicalExaminationPanel(medicalAppointments,medicalAppointmentCurrent);
        physicalExaminationPanel.setId("physicalExaminationPanel");
        physicalExaminationPanel.setStyleName("details-panel");

        diagnosticPanel = new DiagnosticPanel(medicalAppointments, medicalAppointmentCurrent);
        diagnosticPanel.setId("diagnosticPanel");
        diagnosticPanel.setStyleName("details-panel");


        imagesPanel = new ImagesPanel(user);
        imagesPanel.setId("imagesPanel");
        imagesPanel.setStyleName("details-panel");


        horizontalLayout.addComponent(consultMenuPanel);
        horizontalLayout.addComponent(vitalSignsPanel);

        setContent(horizontalLayout);
        setCSS();
    }




    private void getMedicalAppointments(User user){
        medicalAppointments = null;
        List<MedicalAppointment> medicalAppointmentsTemp = new ArrayList<MedicalAppointment>();
        try {
            medicalAppointments = SpringServices.getMedicalAppointmentDAO().getMedicalAppointments();
        } catch (MedicalAppointmentNotExistsException e) {
            e.printStackTrace();
        }
        for (MedicalAppointment medicalAppointment: medicalAppointments){
            if (medicalAppointment.getPatient().getIdUser().equals(user.getIdUser())){
                medicalAppointmentsTemp.add(medicalAppointment);
            }
        }
        medicalAppointments=medicalAppointmentsTemp;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

        String event = clickEvent.getButton().getCaption();

        if (consultMenuPanel.getVitalSingsButton().getCaption().equals(event)){
            horizontalLayout.removeComponent(horizontalLayout.getComponent(1));
            horizontalLayout.addComponent(vitalSignsPanel,1);
            setButtonBorder("vital-signs-button");
        }
        if (consultMenuPanel.getPersonalHistoryButton().getCaption().equals(event)){
            horizontalLayout.removeComponent(horizontalLayout.getComponent(1));
            horizontalLayout.addComponent(personalHistoryPanel,1);
            setButtonBorder("personal-history-button");
        }
        if (consultMenuPanel.getFamilyHistoryButton().getCaption().equals(event)){
            horizontalLayout.removeComponent(horizontalLayout.getComponent(1));
            horizontalLayout.addComponent(familyHistoryPanel,1);
            setButtonBorder("family-history-button");
        }
        if (consultMenuPanel.getHabitsButton().getCaption().equals(event)){
            horizontalLayout.removeComponent(horizontalLayout.getComponent(1));
            horizontalLayout.addComponent(habitsPanel,1);
            setButtonBorder("habits-button");
        }
        if (consultMenuPanel.getQueryReasonButton().getCaption().equals(event)){
            horizontalLayout.removeComponent(horizontalLayout.getComponent(1));
            horizontalLayout.addComponent(queryReasonPanel,1);
            setButtonBorder("query-reason-button");
        }
        if (consultMenuPanel.getPhysicalExaminationButton().getCaption().equals(event)){
            horizontalLayout.removeComponent(horizontalLayout.getComponent(1));
            horizontalLayout.addComponent(physicalExaminationPanel,1);
            setButtonBorder("physical-exam-button");
        }
        if (consultMenuPanel.getDiagnosticButton().getCaption().equals(event)){
            horizontalLayout.removeComponent(horizontalLayout.getComponent(1));
            horizontalLayout.addComponent(diagnosticPanel,1);
            setButtonBorder("diagnostic-button");
        }
        if (consultMenuPanel.getImageButton().getCaption().equals(event)){
              //TODO: Vista imagenes DICOM
            horizontalLayout.removeComponent(horizontalLayout.getComponent(1));
            horizontalLayout.addComponent(imagesPanel,1);
            setButtonBorder("image-button");
        }
    }

    /**
     * This method sets the consult menu panel style sheet
     */
    public void setCSS(){
        Styles styles = Page.getCurrent().getStyles();
        styles.add(".consult-panel{margin-top:3%; margin-left:8%; margin-bottom:3%;}" +
                ".v-slot-query-menu{width:20% !important;}" +
                ".v-slot-details-panel{width:64% !important; border-radius:0px !important; height: 32em !important; }" +
                ".v-slot-vital-signs-button{border-right:solid 7px rgb(74,94,178) !important;}");
    }

    /**
     * This method sets the right border from a particular button
     * @param buttonClass a String with the button class
     */
    public void setButtonBorder(String buttonClass){
        Styles styles = Page.getCurrent().getStyles();
        String baseCSS = ".v-slot-vital-signs-button, .v-slot-personal-history-button, .v-slot-family-history-button," +
                ".v-slot-habits-button, .v-slot-query-reason-button, .v-slot-physical-exam-button, .v-slot-diagnostic-button," +
                ".v-slot-image-button {border-right:0px !important;}";

        styles.add(baseCSS.replace(".v-slot-"+buttonClass+",","")+
                ".v-slot-"+ buttonClass +"{border-right:solid 7px rgb(74,94,178) !important;}");

    }
}
