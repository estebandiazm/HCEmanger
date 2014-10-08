package com.hcemanager.views.generalUI;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.users.User;
import com.hcemanager.views.adminModule.MedicalAppointmentDetail;
import com.hcemanager.views.adminModule.MedicalAppointmentsCRUDPanel;
import com.hcemanager.views.adminModule.UsersPanel;
import com.hcemanager.views.doctorModule.ConsultPanel;
import com.hcemanager.views.doctorModule.HealthRecordPanel;
import com.hcemanager.views.doctorModule.ImagesPanel;
import com.hcemanager.views.doctorModule.MedicalAppointmentsPanel;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class NavigationPanel extends Panel {

    public static final String  ADMIN_USER="admin";
    public static final String  DOCTOR_USER="doctor";
    public static final String  PATIENT_USER="patient";

    private UsersPanel doctorsUserPanel;
    private UsersPanel patientsUserPanel;
    private MedicalAppointmentsPanel medicalAppointmentsPanel;
    private MedicalAppointmentsCRUDPanel medicalAppointmentsCRUDPanel;
    private ConsultPanel consultPanel;
    private ImagesPanel imagesPanel;
    private HealthRecordPanel healthRecordPanel;
    private MedicalAppointmentDetail addMedicalAppointment;
    private List<User> usersList;

    public NavigationPanel(String type,String username){

        setStyleName("navigation-panel");
        TabSheet tabsSheet = new TabSheet();
        tabsSheet.setStyleName("actions");
        tabsSheet.setHeight(100.0f, Unit.PERCENTAGE);

        try {
           usersList = SpringServices.getUserDAO().getUsers();
        } catch (UserNotExistsException e) {
            e.printStackTrace();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }

        doctorsUserPanel = new UsersPanel(UsersPanel.DOCTORS,usersList);
        patientsUserPanel = new UsersPanel(UsersPanel.PATIENTS,usersList);
        medicalAppointmentsPanel = new MedicalAppointmentsPanel(username);

        medicalAppointmentsCRUDPanel = new MedicalAppointmentsCRUDPanel();

        setContent(tabsSheet);
        switch (type) {
            case ADMIN_USER:
                tabsSheet.addTab(doctorsUserPanel, " Personal médico", FontAwesome.USER_MD);
                tabsSheet.addTab(patientsUserPanel, " Pacientes", FontAwesome.USER);
                tabsSheet.addTab(medicalAppointmentsCRUDPanel," Citas médicas", FontAwesome.CALENDAR);
                break;

            case DOCTOR_USER:
                tabsSheet.addTab(medicalAppointmentsPanel, " Citas", FontAwesome.USERS);
                break;
            case PATIENT_USER:
                imagesPanel = new ImagesPanel(getUser(username));
                healthRecordPanel = new HealthRecordPanel(getUser(username));
                tabsSheet.addTab(imagesPanel, " Imagenes", FontAwesome.UPLOAD);
                tabsSheet.addTab(healthRecordPanel, "Historia clínica", FontAwesome.PASTE);
                break;
        }
        applyCSS();
    }

    private User getUser (String username){
        User user = null;
        try {
            user = SpringServices.getUserDAO().getUser(username);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }
        return user;
    }



    public void applyCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".v-tabsheet-tabcontainer{background:rgb(85,170,255);}" +
                ".v-tabsheet-tabs{margin-left:8%;}" +
                ".v-captiontext{color:white; font-size:1.5em; margin-right:3em;}" +
                ".v-panel-navigation-panel{border:none !important;}" +
                ".v-tabsheet-tabitem-selected{ border-top:solid 5px; border-top-color:white;}" +
                ".valo .v-tabsheet-tabitemcell .v-tabsheet-tabitem-selected .v-caption.v-caption {border-bottom:none;" +
                "color:white;}" +
                ".valo .v-tabsheet-tabitemcell .v-tabsheet-tabitem .v-caption.v-caption {color:white;}" );
    }
}
