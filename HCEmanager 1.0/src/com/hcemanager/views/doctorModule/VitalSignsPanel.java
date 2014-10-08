package com.hcemanager.views.doctorModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class VitalSignsPanel extends Panel{

    private TextArea previewsQueryTextArea;
    private TextField weightTextArea;
    private TextField sizeTextArea;
    private TextField temperatureTextArea;
    private TextField pulseTextArea;
    private TextField bloodPressureTextArea;
    private Button saveButton;

    public VitalSignsPanel (List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        super("Signos vitales");
        setIcon(FontAwesome.SIGNAL);
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addComponent(new PreviousDataPanel(getPreviousVitalSings(medicalAppointments,medicalAppointmentCurrent)));
        verticalLayout.addComponent(buildCurrentQueryPanel());
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                updateDataVitalSigns(medicalAppointmentCurrent);
                Notification.show("Signos vitales guardados éxitosamente",Notification.Type.HUMANIZED_MESSAGE);
            }
        });
        setContent(verticalLayout);
        setCSS();

    }

    private String getPreviousVitalSings(List<MedicalAppointment> medicalAppointments, MedicalAppointment medicalAppointmentCurrent){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
            if (medicalAppointment.getIdConsult()!=medicalAppointmentCurrent.getIdConsult()){
                data+="Fecha: "+medicalAppointment.getDate()+"\n";
                data+="Peso: "+medicalAppointment.getWeight()+"\n";
                data+="Temperatura: "+medicalAppointment.getTemperature()+"\n";
                data+="T/A: "+medicalAppointment.getBloodPressure()+"\n";
                data+="Talla: "+medicalAppointment.getSize()+"\n";
                data+="Pulso: "+medicalAppointment.getPulse()+"\n";
                data+="\n \n";
            }
        }
        return data;
    }

    private void updateDataVitalSigns(MedicalAppointment medicalAppointment){
        medicalAppointment.setWeight(weightTextArea.getValue());
        medicalAppointment.setSize(sizeTextArea.getValue());
        medicalAppointment.setTemperature(temperatureTextArea.getValue());
        medicalAppointment.setPulse(pulseTextArea.getValue());
        medicalAppointment.setBloodPressure(bloodPressureTextArea.getValue());
        try {
            SpringServices.getMedicalAppointmentDAO().updateMedicalAppointment(medicalAppointment);
        } catch (MedicalAppointmentExistsException e) {
            e.printStackTrace();
        }
    }

    public Panel buildCurrentQueryPanel(){
        Panel currentQueryPanel = new Panel("Datos actuales");
        currentQueryPanel.setId("current-query-panel");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setId("current-vital-signs-panel");
        horizontalLayout.setWidth(100.0f, Unit.PERCENTAGE);
        FormLayout formLayout1 = new FormLayout();
        formLayout1.setMargin(true);
        FormLayout formLayout2 = new FormLayout();
        formLayout2.setMargin(true);
        FormLayout formLayout3 = new FormLayout();
        formLayout3.setMargin(true);

        weightTextArea = new TextField("Peso");
        weightTextArea.setWidth(90.0f,Unit.PERCENTAGE);
        sizeTextArea = new TextField("Talla");
        sizeTextArea.setWidth(90.0f,Unit.PERCENTAGE);
        temperatureTextArea = new TextField("Temperatura");
        temperatureTextArea.setWidth(90.0f,Unit.PERCENTAGE);
        pulseTextArea = new TextField("Pulso");
        pulseTextArea.setWidth(90.0f,Unit.PERCENTAGE);
        bloodPressureTextArea = new TextField("T/A");
        bloodPressureTextArea.setWidth(75.0f,Unit.PERCENTAGE);
        saveButton = new Button("Guardar",FontAwesome.SAVE);
        saveButton.setWidth(75.0f,Unit.PERCENTAGE);



        formLayout1.addComponent(weightTextArea);
        formLayout1.addComponent(sizeTextArea);
        formLayout2.addComponent(temperatureTextArea);
        formLayout2.addComponent(pulseTextArea);
        formLayout3.addComponent(bloodPressureTextArea);
        formLayout3.addComponent(saveButton);


        horizontalLayout.addComponent(formLayout1);
        horizontalLayout.addComponent(formLayout2);
        horizontalLayout.addComponent(formLayout3);

        currentQueryPanel.setContent(horizontalLayout);

        return currentQueryPanel;
    }


    public TextArea getPreviewsQueryTextArea() {
        return previewsQueryTextArea;
    }

    public void setPreviewsQueryTextArea(TextArea previewsQueryTextArea) {
        this.previewsQueryTextArea = previewsQueryTextArea;
    }

    public TextField getWeightTextArea() {
        return weightTextArea;
    }

    public void setWeightTextArea(TextField weightTextArea) {
        this.weightTextArea = weightTextArea;
    }

    public TextField getSizeTextArea() {
        return sizeTextArea;
    }

    public void setSizeTextArea(TextField sizeTextArea) {
        this.sizeTextArea = sizeTextArea;
    }

    public TextField getTemperatureTextArea() {
        return temperatureTextArea;
    }

    public void setTemperatureTextArea(TextField temperatureTextArea) {
        this.temperatureTextArea = temperatureTextArea;
    }

    public TextField getPulseTextArea() {
        return pulseTextArea;
    }

    public void setPulseTextArea(TextField pulseTextArea) {
        this.pulseTextArea = pulseTextArea;
    }

    public TextField getBloodPressureTextArea() {
        return bloodPressureTextArea;
    }

    public void setBloodPressureTextArea(TextField bloodPressureTextArea) {
        this.bloodPressureTextArea = bloodPressureTextArea;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public void setCSS(){
        Page.Styles styles  = Page.getCurrent().getStyles();
        styles.add("#vitalSignsPanel{border-radius:0px !important; height:32em !important;}" +
                "#current-query-panel{margin-left:3% !important;" +
                "border-radius:0px !important;" +
                "width:94% !important;}" +
                ".v-panel-caption-details-panel{ font-size:1.4em !important;}" +
                ".v-panel-caption-details-panel > .v-icon{ margin-left:3% !important;}" +
                "#current-vital-signs-panel{margin:3% !important;}");
    }
}
