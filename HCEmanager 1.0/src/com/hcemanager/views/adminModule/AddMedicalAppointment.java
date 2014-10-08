package com.hcemanager.views.adminModule;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class AddMedicalAppointment extends Panel {

    private TextField patientTextField;
    private TextField doctorTextField;
    private PopupDateField datePopupDateField;

    public AddMedicalAppointment() {
        VerticalLayout verticalLayout = new VerticalLayout();
        FormLayout formLayout = new FormLayout();

        patientTextField = new TextField("Identificación del paciente");
        doctorTextField = new TextField("Identificaicón del médico");
        datePopupDateField = new PopupDateField("Fecha de la consulta");
        datePopupDateField.setResolution(Resolution.MINUTE);

        formLayout.addComponent(patientTextField);
        formLayout.addComponent(doctorTextField);
        formLayout.addComponent(datePopupDateField);

        verticalLayout.addComponent(formLayout);
        verticalLayout.setComponentAlignment(formLayout,Alignment.TOP_CENTER);
        setContent(verticalLayout);

    }
}
