package com.hcemanager.views.adminModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.hcemanager.models.users.User;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class MedicalAppointmentDetail extends Panel implements Button.ClickListener {

    private TextField patientTextField;
    private TextField doctorTextField;
    private PopupDateField datePopupDateField;
    private Button saveButton;
    private Button editButton;
    private Button updateButton;
    private Window window;
    private MedicalAppointment medicalAppointment;

    /**
     * The constructor that builds th Medical appointment details panel
     * @param window the panel container
     */
    public MedicalAppointmentDetail(Window window) {

        this.window = window;
        VerticalLayout verticalLayout = new VerticalLayout();
        FormLayout formLayout = new FormLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setWidth(76.0f, Unit.PERCENTAGE);

        patientTextField = new TextField("Nombre completo del paciente");
        patientTextField.setStyleName("medical-appointment-form-entry");

        doctorTextField = new TextField("Nombre completo del médico");
        doctorTextField.setStyleName("medical-appointment-form-entry");

        datePopupDateField = new PopupDateField("Fecha de la consulta");
        datePopupDateField.setResolution(Resolution.MINUTE);
        datePopupDateField.setId("medical-appointment-date");

        saveButton = new Button("Guardar", FontAwesome.SAVE);
        saveButton.setId("save-medical-appointment");
        saveButton.addClickListener(this);

        editButton = new Button("Editar",FontAwesome.EDIT);
        editButton.setStyleName("view-search-button");
        editButton.setId("edit-button-mad");
        editButton.setVisible(false);
        editButton.addClickListener(this);

        updateButton = new Button("Actualizar",FontAwesome.SAVE);
        updateButton.setStyleName("view-search-button");
        updateButton.setId("update-button-mad");
        updateButton.setEnabled(false);
        updateButton.setVisible(false);
        updateButton.addClickListener(this);

        formLayout.addComponent(patientTextField);
        formLayout.addComponent(doctorTextField);
        formLayout.addComponent(datePopupDateField);
        formLayout.addComponent(saveButton);
        formLayout.setId("medical-appointment-layout");

        buttonsLayout.addComponent(updateButton);
        buttonsLayout.addComponent(editButton);
        buttonsLayout.setId("medical-appointment-buttons");

        verticalLayout.addComponent(formLayout);
        verticalLayout.addComponent(buttonsLayout);

        this.setStyleName("medical-appointment-detail-panel");
        this.setContent(verticalLayout);
        this.setCSS();

    }

    /**
     * The constructor that builds a panel whit particular information
     * @param medicalAppointment a MedicalAppointment Object
     * @param window the panel container
     */
    public MedicalAppointmentDetail(MedicalAppointment medicalAppointment,Window window){

        this(window);
        this.medicalAppointment = medicalAppointment;
        patientTextField.setValue(medicalAppointment.getPatient().getIdPerson());
        doctorTextField.setValue(medicalAppointment.getDoctor().getIdPerson());

        try {
            datePopupDateField.setValue(toDate(medicalAppointment.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        saveButton.setVisible(false);
        updateButton.setVisible(true);
        editButton.setVisible(true);
        areItemsEnable(false);

    }

    /**
     * This method enable or disable the ui items
     * @param state the boolean that says if the components are enable
     */
    private void areItemsEnable(boolean state){
        patientTextField.setEnabled(state);
        doctorTextField.setEnabled(state);
        datePopupDateField.setEnabled(state);
    }

    /**
     *
     * @param userName the user
     * @return a User object
     * @throws UserNotExistsException
     * @throws CodeNotExistsException
     */
    private User getUser(String userName) throws UserNotExistsException, CodeNotExistsException {
        List<User> userList = SpringServices.getUserDAO().getUsers();
        User user = null;

        for(User element : userList){
            if(element.getName().equalsIgnoreCase(userName))
                user= element;
        }
        return user;
    }

    /**
     * This method builds a medical appointment from the panel UI components
     * @return a MedicalAppointment object
     */
    private MedicalAppointment getMedicalAppointment() throws UserNotExistsException, CodeNotExistsException {

        MedicalAppointment medicalAppointment = new MedicalAppointment();
        medicalAppointment.setPatient(getUser(patientTextField.getValue()));
        medicalAppointment.setDoctor(getUser(doctorTextField.getValue()));
        medicalAppointment.setDate(toString(datePopupDateField.getValue()));

        return medicalAppointment;
    }


    /**
     * Insert a medical appointment from the ui values
     * @throws MedicalAppointmentExistsException
     * @throws UserNotExistsException
     * @throws CodeNotExistsException
     */
    private void addMedicalAppointment() throws MedicalAppointmentExistsException, UserNotExistsException,
            CodeNotExistsException {
        SpringServices.getMedicalAppointmentDAO().insertMedicalAppointment(getMedicalAppointment());
    }

    /**
     * Update a medical appointment from the ui values
     * @throws UserNotExistsException
     * @throws CodeNotExistsException
     * @throws MedicalAppointmentExistsException
     */
    private void updateMedicalAppointment() throws UserNotExistsException, CodeNotExistsException,
            MedicalAppointmentExistsException {
        MedicalAppointment auxMedicalAppointment = getMedicalAppointment();
        auxMedicalAppointment.setIdConsult(medicalAppointment.getIdConsult());
        SpringServices.getMedicalAppointmentDAO().updateMedicalAppointment(auxMedicalAppointment);
    }


    /**
     * This method takes a String whit a date time information to parse from a Date object
     * @param date a string to parse
     * @return a Date object
     * @throws ParseException
     */
    private Date toDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return format.parse(date);
    }

    /**
     * This method takes a Date object to parse from a String whit its infromation
     * @param date a Date object to parse
     * @return a String whit a date time information
     */
    private String toString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * This method sets the panel style sheets
     */
    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add("#medical-appointment-layout{margin:10% !important;}" +
                "#medical-appointment-date{width:68% !important;}" +
                "#save-medical-appointment{width:68% !important;}" +
                "#medical-appointment-buttons{margin-top: -5% !important; margin-left: 13% !important;" +
                "margin-bottom: 10% !important;}" +
                ".view-search-button{width:100% !important;}" +
                "#edit-button-mad{float:right !important;}" +
                ".medical-appointment-form-entry{width:68% !important;}");
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String event = clickEvent.getButton().getCaption();

        if (event.equals("Guardar")){
            try {
                addMedicalAppointment();
                window.close();
            } catch (MedicalAppointmentExistsException e) {
                e.printStackTrace();
                Notification.show("Ya existe una cita médica con la misma información", Notification.Type.ERROR_MESSAGE);
            } catch (UserNotExistsException e) {
                e.printStackTrace();
                Notification.show("El usuario no existe", Notification.Type.ERROR_MESSAGE);
            } catch (CodeNotExistsException e) {
                e.printStackTrace();
                Notification.show("El código no existe", Notification.Type.ERROR_MESSAGE);
            }
        }else if (event.equals("Editar")){
            updateButton.setEnabled(true);
            editButton.setEnabled(false);
            areItemsEnable(true);
        }else if (event.equals("Actualizar")){
            try {
                updateMedicalAppointment();
                window.close();
            } catch (UserNotExistsException e) {
                e.printStackTrace();
                Notification.show("Usuario no existe", Notification.Type.ERROR_MESSAGE);
            } catch (CodeNotExistsException e) {
                e.printStackTrace();
                Notification.show("Código no existe", Notification.Type.ERROR_MESSAGE);
            } catch (MedicalAppointmentExistsException e) {
                e.printStackTrace();
                Notification.show("La cita ya existe", Notification.Type.ERROR_MESSAGE);
            }
        }
    }
}
