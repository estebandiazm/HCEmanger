package com.hcemanager.views.adminModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentNotExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.hcemanager.views.generalUI.SearchWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import javax.swing.*;
import java.util.List;

/**
 * @author daniel on 29/09/14.
 */
public class MedicalAppointmentsCRUDPanel extends Panel implements Button.ClickListener{

    private Label titleLabel;
    private Table medicalAppointmentsTable;
    private Button searchButton;
    private Button newMedicalAppointmentButton;


    public MedicalAppointmentsCRUDPanel() {

        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        titleLabel = new Label("Citas médicas");
        titleLabel.setIcon(FontAwesome.CALENDAR);
        titleLabel.setStyleName("users-title-label");

        searchButton = new Button("Disponibilidad de médicos", FontAwesome.SEARCH);
        searchButton.setWidth(70.0f,Unit.PERCENTAGE);
        searchButton.addClickListener(this);
        newMedicalAppointmentButton = new Button("Nuevo",FontAwesome.PLUS);
        newMedicalAppointmentButton.setWidth(70.0f,Unit.PERCENTAGE);
        newMedicalAppointmentButton.addClickListener(this);

        medicalAppointmentsTable = new Table();
        medicalAppointmentsTable.setWidth(90.0f, Unit.PERCENTAGE);
        medicalAppointmentsTable.setHeight(26.0f, Unit.EM);
        medicalAppointmentsTable.setSelectable(true);
        medicalAppointmentsTable.setId("users-table");
        medicalAppointmentsTable.addContainerProperty("Paciente", String.class, null);
        medicalAppointmentsTable.addContainerProperty("Médico", String.class, null);
        medicalAppointmentsTable.addContainerProperty("Fecha", String.class, null);
        medicalAppointmentsTable.addContainerProperty("Detalle", Button.class, null);
        setTableData();

        horizontalLayout.setStyleName("users-horizontal-layout");
        horizontalLayout.setMargin(true);
        horizontalLayout.setWidth(100.0f,Unit.PERCENTAGE);
        horizontalLayout.addComponent(titleLabel);
        horizontalLayout.addComponent(searchButton);
        horizontalLayout.addComponent(newMedicalAppointmentButton);

        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.addComponent(medicalAppointmentsTable);

        this.setStyleName("medical-appointments-panel");
        this.setContent(verticalLayout);
    }

    /**
     *  Sets the medical appointments table information
     */
    public void setTableData(){

        try {
            List<MedicalAppointment> medicalAppointments =
                    SpringServices.getMedicalAppointmentDAO().getMedicalAppointments();

            for(int i = 0; i<medicalAppointments.size();i++){
                MedicalAppointment medicalAppointment = medicalAppointments.get(i);
                Button button = new Button("Ver");
                button.setId("view-user-button");
                button.setIcon(FontAwesome.EYE);
                button.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        MedicalAppointmentDetailWindow window=
                                new MedicalAppointmentDetailWindow(medicalAppointment);
                        getUI().addWindow(window);
                    }
                });

                medicalAppointmentsTable.addItem(new Object[]{
                        medicalAppointment.getPatient().getName(),
                        medicalAppointment.getDoctor().getName(),
                        medicalAppointment.getDate(),
                        button},
                        i);

            }
        }catch (MedicalAppointmentNotExistsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

        String event = clickEvent.getButton().getCaption();

        if(event.equals("Disponibilidad de médicos")){
            SearchWindow window = new SearchWindow(SearchWindow.SEARCH_DOCTOR_CALENDAR);
            getUI().addWindow(window);
        }else if (event.equals("Nuevo")){
            MedicalAppointmentDetailWindow window = new MedicalAppointmentDetailWindow();
            getUI().addWindow(window);
        }
    }
}
