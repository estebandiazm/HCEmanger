package com.hcemanager.views.doctorModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentNotExistsException;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.hcemanager.models.users.User;
import com.hcemanager.utils.XMLGenerator;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class HealthRecordPanel extends Panel{


    private Label labelTitle;
    private Button editButton;
    private Button downloadButton;
    private Button buttonNew;
    private TextField textFieldIdentification;
    private TextField textFieldName;
    private TextField textFieldLastName;
    private TextField comboBoxGender;
    private PopupDateField popupDateFieldBirthDate;
    private TextField textFieldAge;
    private TextField comboBoxMaritalStatus;
    private TextField textFieldProfession;
    private PopupDateField popupDateFieldCreateDate;
    private TextField comboBoxBloodType;
    private TextArea familyHistoryTextArea;
    private TextArea personalHistoryTextArea;
    private TextArea habitsTextArea;
    private FileDownloader fileDownloader;
    private List<MedicalAppointment> medicalAppointments;

    public HealthRecordPanel(User user){

        setStyleName("health-record-panel");
        getMedicalAppointments(user);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();


        verticalLayout.addComponent(buildButtonPanel());
        verticalLayout.addComponent(buildPersonalDataPanel(user));
        verticalLayout.addComponent(buildHistoryPanel());

        for(MedicalAppointment medicalAppointment:medicalAppointments)
            verticalLayout.addComponent(buildConsultPanel(medicalAppointment));

        String path = new XMLGenerator().writeXML(user);
        File file = new File(path);
        file.deleteOnExit();
        FileResource fileResource = new FileResource(file);
        fileDownloader = new FileDownloader(fileResource);
        fileDownloader.extend(downloadButton);


        setContent(verticalLayout);
        setCSS();
    }



    /**
     * This method generates the Personal data panel UI.
     */
    public Panel buildPersonalDataPanel(User user){

        //Creation Panel and your Layaouts
        Panel panelPersonalData = new Panel();
        panelPersonalData.setStyleName("emr-personal-data-panel");
        FormLayout formLayout1= new FormLayout();
        FormLayout formLayout2 = new FormLayout();
        FormLayout formLayout3 = new FormLayout();


        //initialization inputs
        textFieldIdentification = new TextField("Identificación");
        textFieldIdentification.setEnabled(false);
        textFieldIdentification.setValue(user.getIdPerson());
        textFieldIdentification.setInputPrompt("Ingrese número de identificación");
        textFieldIdentification.setMaxLength(10);

        textFieldName = new TextField("Nombre");
        textFieldName.setEnabled(false);
        textFieldName.setValue(user.getName());
        textFieldName.setInputPrompt("Ingrese su nombre");
        textFieldName.setMaxLength(20);

        comboBoxGender = new TextField("Género");
        comboBoxGender.setValue(user.getAdministrativeGenderCode().getPrintName());
        comboBoxGender.setEnabled(false);

        popupDateFieldBirthDate = new PopupDateField("Fecha de nacimiento");
        popupDateFieldBirthDate.setInputPrompt("Ingrese su fecha de nacimiento");
        popupDateFieldBirthDate.setImmediate(true);
        popupDateFieldBirthDate.setResolution(Resolution.DAY);
        popupDateFieldBirthDate.setEnabled(false);

        textFieldAge = new TextField("Edad");
        textFieldAge.setInputPrompt("Ingrese su edad");
        textFieldAge.setMaxLength(3);
        textFieldAge.setEnabled(false);
//        textFieldAge.setValue(user.);

        comboBoxMaritalStatus = new TextField("Estado civíl");
        comboBoxMaritalStatus.setValue(user.getMaritalStatusCode().getPrintName());
        comboBoxMaritalStatus.setEnabled(false);

        textFieldProfession = new TextField("Profesión");
        textFieldProfession.setInputPrompt("Ingrese su profesión");
        textFieldProfession.setMaxLength(50);

        popupDateFieldCreateDate = new PopupDateField("Fecha de creación");
        popupDateFieldCreateDate.setInputPrompt("Ingrese fecha de creación");
        popupDateFieldCreateDate.setImmediate(true);
        popupDateFieldCreateDate.setResolution(Resolution.DAY);

        comboBoxBloodType = new TextField("Tipo de sangre");
        comboBoxBloodType.setEnabled(false);
        comboBoxBloodType.setValue("Tipo de sangre"); //todo: insertar tipo de sangre
        //add components


        formLayout1.setSizeFull();
        formLayout1.setId("form-one");
        formLayout1.setSpacing(true);
        formLayout1.setMargin(true);
        formLayout1.addComponent(textFieldIdentification);
        formLayout1.addComponent(textFieldName);
        formLayout1.addComponent(comboBoxGender);

        formLayout2.setSizeFull();
        formLayout2.setId("form-two");
        formLayout2.setSpacing(true);
        formLayout2.addComponent(textFieldAge);
        formLayout2.addComponent(comboBoxMaritalStatus);
        formLayout2.addComponent(textFieldProfession);

        formLayout3.setSizeFull();
        formLayout3.setId("form-three");
        formLayout3.setSpacing(true);
        formLayout3.setMargin(true);
        formLayout3.addComponent(popupDateFieldBirthDate);
        formLayout3.addComponent(comboBoxBloodType);
        formLayout3.addComponent(popupDateFieldCreateDate);


        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.addComponent(formLayout1);
        horizontalLayout.addComponent(formLayout2);
        horizontalLayout.addComponent(formLayout3);
        panelPersonalData.setContent(horizontalLayout);

        return panelPersonalData;

    }

    /**
     * This method generates the History panel UI.
     */
    public Panel buildHistoryPanel(){

        Panel panelHistory = new Panel();
        panelHistory.setStyleName("emr-history-panel");
        FormLayout formLayout = new FormLayout();
        formLayout.setStyleName("emr-history-layout");


        //Initialization components
        personalHistoryTextArea = new TextArea("Antecedentes personales");
        personalHistoryTextArea.setSizeFull();
        personalHistoryTextArea.setEnabled(false);
        personalHistoryTextArea.setValue(getPreviousPersonalHistory(medicalAppointments));

        familyHistoryTextArea = new TextArea("Antecedentes familiares");
        familyHistoryTextArea.setSizeFull();
        familyHistoryTextArea.setEnabled(false);
        familyHistoryTextArea.setValue(getPreviousFamilyHistory(medicalAppointments));

        habitsTextArea = new TextArea("Hábitos");
        habitsTextArea.setSizeFull();
        habitsTextArea.setEnabled(false);
        habitsTextArea.setValue(getPreviousHabits(medicalAppointments));


        formLayout.setSizeFull();
        formLayout.setMargin(true);
        formLayout.addComponent(personalHistoryTextArea);
        formLayout.addComponent(familyHistoryTextArea);
        formLayout.addComponent(habitsTextArea);

        panelHistory.setContent(formLayout);

        return panelHistory;

    }

    public Panel buildButtonPanel(){

        Panel panelButton = new Panel();
        panelButton.setId("emr-buttons-panel");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setWidth("100%");

        labelTitle = new Label("Historia Clínica");
        labelTitle.setStyleName("emr-label");
        labelTitle.setIcon(FontAwesome.PASTE);

        downloadButton = new Button(" Descargar", FontAwesome.DOWNLOAD);
        downloadButton.setWidth(75.0f,Unit.PERCENTAGE);
        editButton = new Button(" Editar",FontAwesome.EDIT);
        editButton.setWidth(75.0f,Unit.PERCENTAGE);
        buttonNew = new Button(" Nueva",FontAwesome.PLUS_CIRCLE);
        buttonNew.setWidth(75.0f,Unit.PERCENTAGE);

        horizontalLayout.addComponent(labelTitle);
        horizontalLayout.addComponent(downloadButton);
        horizontalLayout.addComponent(editButton);
        horizontalLayout.addComponent(buttonNew);

        panelButton.setContent(horizontalLayout);
        return panelButton;
    }

    public Panel buildConsultPanel(MedicalAppointment medicalAppointment){
        Panel historyConsultPanel = new Panel("Consulta "+medicalAppointment.getDate());
        historyConsultPanel.setStyleName("emr-consult-panel");
        VerticalLayout layout = new VerticalLayout();

        PreviousDataPanel vitalSings = new PreviousDataPanel("Historía signos vitales");
        vitalSings.setCaption("Signos vitales");
        vitalSings.getPreviousDataTextArea().setValue(getVitalSigns(medicalAppointment));

        PreviousDataPanel reasonConsult = new PreviousDataPanel("Historía razón de la consulta");
        reasonConsult.setCaption("Razón de la consulta");
        reasonConsult.getPreviousDataTextArea().setValue(medicalAppointment.getQueryReason());
        reasonConsult.setStyleName("emr-consult");

        PreviousDataPanel physicalExamination = new PreviousDataPanel("Historía exámen físico");
        physicalExamination.setCaption("Exámen físico");
        physicalExamination.setStyleName("emr-consult");
        physicalExamination.getPreviousDataTextArea().setValue(medicalAppointment.getPhysicalExamination());

        PreviousDataPanel diagnostic = new PreviousDataPanel("Historía diagnósticos");
        diagnostic.setCaption("Diagnóstico");
        diagnostic.setStyleName("emr-consult");
        diagnostic.getPreviousDataTextArea().setValue(getDiagnostic(medicalAppointment));

        layout.addComponent(vitalSings);
        layout.addComponent(reasonConsult);
        layout.addComponent(physicalExamination);
        layout.addComponent(diagnostic);

        historyConsultPanel.setContent(layout);

        return historyConsultPanel;
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".health-record-panel{width:84% !important; margin-top:3% !important; margin-bottom:3% !important;" +
                "margin-left:8% !important; border-radius:0px !important; height:32em !important;}" +
                "#emr-buttons-panel{margin-top:3% !important; margin-left:8% !important; border:none !important;" +
                "width:85% !important; box-shadow: 0 0 0 0px rgba(255,255,255,0) !important; margin-bottom:3% !important;}" +
                ".emr-label{font-weight:bold !important; display: inline !important; font-size:1.4em !important;}" +
                ".v-caption-emr-label {display:inline !important;margin-right:5% !important;font-size:1.4em !important;}" +
                ".v-slot-emr-personal-data-panel{margin-left: 4% !important;margin-bottom: 3% !important;width: 92% !important;}" +
                ".emr-personal-data-panel{border-radius:0px !important;}" +
                "#form-one{margin:5% !important;}" +
                "#form-two{margin:5% !important; margin-left:-3% !important;}" +
                "#form-three{margin:5% !important; margin-left: -10% !important;}" +
                ".v-filterselect-input{border-radius:0px !important;}" +
                ".v-slot-emr-history-panel{margin-left:4% !important; width: 92% !important; margin-bottom: 3% !important;}" +
                ".emr-history-panel{border-radius:0px !important;}" +
                ".emr-history-layout{margin:2% !important; width:96% !important;}" +
                ".emr-consult-panel{width:93% !important; margin-left:4% !important; margin-bottom:3% !important;" +
                " border-radius:0px !important}" +
                ".emr-consult{margin-top: 0px !important;}");
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


    private String getPreviousPersonalHistory(List<MedicalAppointment> medicalAppointments){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
            data+="Fecha: "+medicalAppointment.getDate()+"\n";
            data+=medicalAppointment.getPersonalHistory();
            data+="\n \n";
        }
        return data;
    }

    private String getPreviousFamilyHistory(List<MedicalAppointment> medicalAppointments){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
                data+="Fecha: "+medicalAppointment.getDate()+"\n";
                data+=medicalAppointment.getFamilyHistory();
                data+="\n \n";
        }
        return data;
    }

    private String getPreviousHabits(List<MedicalAppointment> medicalAppointments){
        String data="";
        for (MedicalAppointment medicalAppointment:medicalAppointments){
                data+="Fecha: "+medicalAppointment.getDate()+"\n";
                data+=medicalAppointment.getHabits()+"\n \n";
        }
        return data;
    }

    private String getDiagnostic(MedicalAppointment medicalAppointment){
        return  "Grupo de enferemedad: "+medicalAppointment.getGroupDisease()+"\n"+
                "Enfermedad: "+medicalAppointment.getDisease()+"\n"+
                "Observación: "+medicalAppointment.getObservationDisease();
    }

    private String getVitalSigns(MedicalAppointment medicalAppointment){
        return "Peso: "+medicalAppointment.getWeight()+"\n"+
                "Temperatura: "+medicalAppointment.getTemperature()+"\n"+
                "T/A: "+medicalAppointment.getBloodPressure()+"\n"+
                "Talla: "+medicalAppointment.getSize()+"\n"+
                "Pulso: "+medicalAppointment.getPulse();
    }
}

