package com.hcemanager.views.doctorModule;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.awt.*;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class ConsultMenuPanel extends Panel{

    private Button vitalSingsButton;
    private Button personalHistoryButton;
    private Button familyHistoryButton;
    private Button habitsButton;
    private Button queryReasonButton;
    private Button physicalExaminationButton;
    private Button diagnosticButton;
    private Button imageButton;

    public ConsultMenuPanel (){
        VerticalLayout verticalLayout = new VerticalLayout();

        vitalSingsButton = new Button(" Signos \n vitales", FontAwesome.SIGNAL);
        vitalSingsButton.setSizeFull();
        vitalSingsButton.setStyleName("vital-signs-button");
        vitalSingsButton.setId("vital-signs-button");
        personalHistoryButton = new Button(" Antecedentes \n personales",FontAwesome.USER);
        personalHistoryButton.setSizeFull();
        personalHistoryButton.setStyleName("personal-history-button");
        familyHistoryButton = new Button(" Antecedentes \n familiares",FontAwesome.USERS);
        familyHistoryButton.setSizeFull();
        familyHistoryButton.setStyleName("family-history-button");
        habitsButton = new Button(" Hábitos",FontAwesome.HEART_O);
        habitsButton.setSizeFull();
        habitsButton.setStyleName("habits-button");
        queryReasonButton = new Button(" Motívo de \n consulta",FontAwesome.QUESTION_CIRCLE);
        queryReasonButton.setSizeFull();
        queryReasonButton.setStyleName("query-reason-button");
        physicalExaminationButton = new Button(" Examen \n físico", FontAwesome.BAR_CHART_O);
        physicalExaminationButton.setSizeFull();
        physicalExaminationButton.setStyleName("physical-exam-button");
        diagnosticButton = new Button("Diagnóstico",FontAwesome.PENCIL_SQUARE_O);
        diagnosticButton.setSizeFull();
        diagnosticButton.setStyleName("diagnostic-button");
        imageButton = new Button("Imágenes diagnósticas",FontAwesome.IMAGE);
        imageButton.setSizeFull();
        imageButton.setStyleName("image-button");

        verticalLayout.addComponent(vitalSingsButton);
        verticalLayout.addComponent(personalHistoryButton);
        verticalLayout.addComponent(familyHistoryButton);
        verticalLayout.addComponent(habitsButton);
        verticalLayout.addComponent(queryReasonButton);
        verticalLayout.addComponent(physicalExaminationButton);
        verticalLayout.addComponent(diagnosticButton);
        verticalLayout.addComponent(imageButton);
        verticalLayout.setSizeFull();

        setContent(verticalLayout);
        setCSS();
    }

    public Button getVitalSingsButton() {
        return vitalSingsButton;
    }

    public void setVitalSingsButton(Button vitalSingsButton) {
        this.vitalSingsButton = vitalSingsButton;
    }

    public Button getPersonalHistoryButton() {
        return personalHistoryButton;
    }

    public void setPersonalHistoryButton(Button personalHistoryButton) {
        this.personalHistoryButton = personalHistoryButton;
    }

    public Button getFamilyHistoryButton() {
        return familyHistoryButton;
    }

    public void setFamilyHistoryButton(Button familyHistoryButton) {
        this.familyHistoryButton = familyHistoryButton;
    }

    public Button getHabitsButton() {
        return habitsButton;
    }

    public void setHabitsButton(Button habitsButton) {
        this.habitsButton = habitsButton;
    }

    public Button getQueryReasonButton() {
        return queryReasonButton;
    }

    public void setQueryReasonButton(Button queryReasonButton) {
        this.queryReasonButton = queryReasonButton;
    }

    public Button getPhysicalExaminationButton() {
        return physicalExaminationButton;
    }

    public void setPhysicalExaminationButton(Button physicalExaminationButton) {
        this.physicalExaminationButton = physicalExaminationButton;
    }

    public Button getDiagnosticButton() {
        return diagnosticButton;
    }

    public void setDiagnosticButton(Button diagnosticButton) {
        this.diagnosticButton = diagnosticButton;
    }

    public Button getImageButton() {
        return imageButton;
    }

    public void setImageButton(Button imageButton) {
        this.imageButton = imageButton;
    }

    public void setCSS(){
       Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".vital-signs-button, .personal-history-button, .family-history-button," +
                ".habits-button, .query-reason-button, .physical-exam-button, .diagnostic-button," +
                ".image-button {height:4em !important;border-bottom: solid 1px white !important;}");
    }
}
