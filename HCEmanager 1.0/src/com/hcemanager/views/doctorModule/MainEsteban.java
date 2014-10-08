package com.hcemanager.views.doctorModule;

import com.hcemanager.views.generalUI.MainPanel;
import com.hcemanager.views.generalUI.NavigationPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author daniel
 */

@Theme("valo")
@Title("Test Esteban")
public class MainEsteban extends UI {

    private VerticalLayout layout;
    @Override
    public void init(VaadinRequest request) {
        layout = new VerticalLayout();
        setContent(layout);


        MainPanel mainPanel = new MainPanel("Juan Esteban",MainPanel.ADMIN_USER);

//        MedicalAppointmentsPanel medicalAppointmentsPanel = new MedicalAppointmentsPanel();
//        HealthRecordPanel healthRecordPanel = new HealthRecordPanel();

//        VitalSignsPanel vitalSignsPanel = new VitalSignsPanel();

//        FamilyHistoryPanel familyHistoryPanel = new FamilyHistoryPanel();
        NavigationPanel navigationPanel= new NavigationPanel(NavigationPanel.ADMIN_USER,"Juan Esteban");
//        ConsultMenuPanel consultMenuPanel=new ConsultMenuPanel();

        layout.addComponent(mainPanel);
        layout.addComponent(navigationPanel);


    }

    public VerticalLayout getLayout() {
        return layout;
    }

    public void setLayout(VerticalLayout layout) {
        this.layout = layout;
    }
}
