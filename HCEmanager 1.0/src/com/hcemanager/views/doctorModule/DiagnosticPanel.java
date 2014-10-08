package com.hcemanager.views.doctorModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author daniel
 */
public class DiagnosticPanel extends Panel {

    private PreviousDataPanel previousDataPanel;
    private Panel currentDataPanel;
    private ComboBox diseasesGroup;
    private ComboBox disease;
    private TextArea observationTextArea;
    private Button saveButton;

    /**
     * This constructor builds the Diagnostic panel
     * @param medicalAppointments the previous medical appointment
     */
    public DiagnosticPanel(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        super("Diagnóstico");
        setIcon(FontAwesome.PENCIL_SQUARE_O);

        VerticalLayout layout = new VerticalLayout();
        previousDataPanel = new PreviousDataPanel(getPreviousDiagnostics(medicalAppointments, medicalAppointmentCurrent));
        buildCurrentPanel();

        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                updateDataDiagnostic(medicalAppointmentCurrent);
                Notification.show("Diagnóstico guardado exitosamente");
            }
        });

        layout.addComponent(previousDataPanel);
        layout.addComponent(currentDataPanel);

        this.setContent(layout);
        this.setCSS();
    }

    /**
     * This method updates the data of diagnostic in the medical appointment.
     * @param medicalAppointmentCurrent
     */
    private void updateDataDiagnostic(MedicalAppointment medicalAppointmentCurrent){
        medicalAppointmentCurrent.setDisease(disease.getValue().toString());
        medicalAppointmentCurrent.setGroupDisease(diseasesGroup.getValue().toString());
        medicalAppointmentCurrent.setObservationDisease(observationTextArea.getValue());
        try {
            SpringServices.getMedicalAppointmentDAO().updateMedicalAppointment(medicalAppointmentCurrent);
        } catch (MedicalAppointmentExistsException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method get the diagnostics in previous medical appointment.
     * @param medicalAppointments
     * @return
     */
    private String getPreviousDiagnostics(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
            if (medicalAppointment.getIdConsult() != medicalAppointmentCurrent.getIdConsult()){
                data += "Fecha: "+medicalAppointment.getDate()+"\n";
                data += "Grupo de la enfermedad: "+medicalAppointment.getGroupDisease()+"\n";
                data += "Enfermedad: "+medicalAppointment.getDisease()+"\n";
                data += "Observación: "+medicalAppointment.getObservationDisease()+"\n \n";
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

    public void setCurrentDataPanel(Panel currentDataPanel) {
        this.currentDataPanel = currentDataPanel;
    }

    public ComboBox getDiseasesGroup() {
        return diseasesGroup;
    }

    public void setDiseasesGroup(ComboBox diseasesGroup) {
        this.diseasesGroup = diseasesGroup;
    }

    public ComboBox getDisease() {
        return disease;
    }

    public void setDisease(ComboBox disease) {
        this.disease = disease;
    }

    public TextArea getObservationTextArea() {
        return observationTextArea;
    }

    public void setObservationTextArea(TextArea observationTextArea) {
        this.observationTextArea = observationTextArea;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    private void setDiseasesItems(List<String> diseases){

        diseases.forEach(this.disease::addItem);
    }

    private void setDiseasesGroupItems(List<String> diseasesGroup){
        diseasesGroup.forEach(this.diseasesGroup::addItem);
    }

    public void buildCurrentPanel(){

        VerticalLayout verticalLayout= new VerticalLayout();
        HorizontalLayout diseasesLayout = new HorizontalLayout();
        FormLayout observationLayout = new FormLayout();
        currentDataPanel = new Panel("Datos actuales");
        currentDataPanel.setStyleName("currentPanel");

        diseasesGroup = new ComboBox("Grupo de enfermedad");
        diseasesGroup.setNullSelectionAllowed(false);
        diseasesGroup.setStyleName("disease-group-combobox");
        diseasesGroup.setInputPrompt("Seleccione el grupo de enfermedad");
        setDiseasesGroupItems(new ArrayList<String>(){{add("A01");add("A02");}});

        disease = new ComboBox("Enfermedad");
        disease.setNullSelectionAllowed(false);
        disease.setStyleName("disease-combobox");
        disease.setInputPrompt("Selleccione la enfermedad");
        setDiseasesItems(new ArrayList<String>() {{
            add("Peritonitis");
            add("Fractura craneal");
        }});

        observationTextArea = new TextArea("Observación");
        observationTextArea.setStyleName("observation-text");

        saveButton = new Button("Guardar",FontAwesome.SAVE);
        saveButton.setStyleName("current-diagnostic-save-button");

        diseasesLayout.setWidth(100.0f,Unit.PERCENTAGE);
        diseasesLayout.setStyleName("current-diseases");
        diseasesLayout.addComponent(new FormLayout(diseasesGroup));
        diseasesLayout.addComponent(new FormLayout(disease));

        observationLayout.setWidth(100.0f,Unit.PERCENTAGE);
        observationLayout.setStyleName("current-observation");
        observationLayout.addComponent(observationTextArea);

        verticalLayout.addComponent(diseasesLayout);
        verticalLayout.addComponent(observationLayout);
        verticalLayout.addComponent(saveButton);
        currentDataPanel.setContent(verticalLayout);

    }
    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add("#diagnosticPanel{border-radius:0px !important; height:32em !important;}" +
                ".current-diseases{margin:3% !important;}" +
                ".current-observation{margin-left:3% !important; margin-bottom:3% !important;}" +
                ".disease-group-combobox{width:100% !important;}" +
                ".disease-combobox{width:81% !important;}" +
                ".observation-text{width:92% !important;}" +
                ".current-diagnostic-save-button{width:30% !important; margin-left:3% !important; margin-bottom:3%;}");
    }
}
