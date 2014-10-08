package com.hcemanager.views.doctorModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentNotExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.hcemanager.models.users.User;
import com.hcemanager.views.generalUI.SearchWindow;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Page.*;
import com.vaadin.ui.*;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class MedicalAppointmentsPanel extends Panel {

    private Table medicalAppointmentTable;
    private Label titleLabel;
    private Label iconLabel;
    private Label dateLabel;
    private Button searchButton;

    /**
     * This constructor builds the medical appointments panel
     */
    public MedicalAppointmentsPanel(String username) {

        setStyleName("medical-appointments-panel");
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Calendar date = Calendar.getInstance();


        titleLabel = new Label("Citas pendientes ");
        titleLabel.setStyleName("titleLabel");
        iconLabel = new Label();
        iconLabel.setStyleName("medical-appointment-icon");
        iconLabel.setIcon(FontAwesome.USERS);
        searchButton = new Button(" Buscar paciente", FontAwesome.SEARCH);
        searchButton.setStyleName("searchButton");
        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                SearchWindow searchWindow = new SearchWindow(SearchWindow.SEARCH_USER);
                getUI().addWindow(searchWindow);
            }
        });

        dateLabel = new Label("Fecha: " + DateFormat.getDateInstance(DateFormat.LONG).format(date.getTime()));
        dateLabel.addStyleName("dateLabel");


        horizontalLayout.addComponent(iconLabel);
        horizontalLayout.addComponent(titleLabel);
        horizontalLayout.addComponent(dateLabel);
        horizontalLayout.addComponent(searchButton);

        horizontalLayout.setSizeFull();

        medicalAppointmentTable = new Table();
        medicalAppointmentTable.setId("cites-table");
        medicalAppointmentTable.setWidth(90.0f, Unit.PERCENTAGE);
        medicalAppointmentTable.setHeight(26.0f, Unit.EM);


        medicalAppointmentTable.addContainerProperty("Hora", String.class, null);
        medicalAppointmentTable.addContainerProperty("Cedula", String.class, null);
        medicalAppointmentTable.addContainerProperty("Nombre y Apellido", String.class, null);
        medicalAppointmentTable.addContainerProperty("Consulta", Button.class, null);
        addItemsMedicalAppointmentsTable(username);



        layout.addComponent(horizontalLayout);
        layout.addComponent(medicalAppointmentTable);
        setContent(layout);

        setCSS();
    }

    public void addItemsMedicalAppointmentsTable(String username){

        Calendar today = Calendar.getInstance();
        System.out.println(today);

        try {
            List<MedicalAppointment> medicalAppointments = SpringServices.getMedicalAppointmentDAO().getMedicalAppointments();
            for (int i=0;i<medicalAppointments.size();i++){
                MedicalAppointment medicalAppointment = medicalAppointments.get(i);
                if (medicalAppointment.getDoctor().getIdUser().equals(username)
                        ){
                    User user = getPatient(medicalAppointment.getPatient().getIdUser());
                    Button seeButton = new Button(" Ver",FontAwesome.EYE);
                    seeButton.setId("see-button");
                    seeButton.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent) {
                            enabledTabs(user,medicalAppointment);
                        }
                    });
                    medicalAppointmentTable.addItem(new Object[]{
                            medicalAppointment.getDate(),
                            user.getIdPerson(),
                            user.getName(),
                            seeButton},i);
                }
            }
        } catch (MedicalAppointmentNotExistsException e) {
            e.printStackTrace();
        }
    }

    private void enabledTabs(User user,MedicalAppointment medicalAppointment){
        VerticalLayout layout= (VerticalLayout) getUI().getContent();
        TabSheet tabSheet = (TabSheet) ((Panel)layout.getComponent(1)).getContent();
        if (tabSheet.getTab(1)!=null){
            tabSheet.removeTab(tabSheet.getTab(1));
            tabSheet.removeTab(tabSheet.getTab(1));
        }
        tabSheet.addTab(new ConsultPanel(user, medicalAppointment),"Consulta: "+user.getName(), FontAwesome.COMMENT_O);
        tabSheet.addTab(new HealthRecordPanel(user), "Historia clínica: "+user.getName(), FontAwesome.PASTE);
        tabSheet.setSelectedTab(1);
    }

    private User getPatient(String idUser){
        User user = null;
        try {
            user = SpringServices.getUserDAO().getUser(idUser);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }
        return user;
    }

    private String toDay(String date){
        return date.substring(0,10);
    }

    private String toHour(String date){
        return date.substring(10,16);
    }
    public Table getMedicalAppointmentTable() {
        return medicalAppointmentTable;
    }

    public void setMedicalAppointmentTable(Table medicalAppointmentTable) {
        this.medicalAppointmentTable = medicalAppointmentTable;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(Label dateLabel) {
        this.dateLabel = dateLabel;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }

    /**
     * This method set the medical appointments panel style sheet
     */
    public void setCSS() {
        Styles styles = Page.getCurrent().getStyles();
        styles.add(".medical-appointments-panel{margin-top:3%; margin-left:8%; border-radius:0 !important; margin-bottom:3%;" +
                "width:85% !important;}" +
                ".v-button{;border-radius: 0 !important;background: rgb(85,170,255) !important;color: white !important;" +
                "border: none !important; box-shadow: 0 0 0 0 !important;}" +
                ".v-slot-medical-appointment-icon{margin-left:5% !important; margin-top:3%; width:2% !important;}"+
                ".v-slot-titleLabel{ margin-top:3%; width:33% !important; font-weight:bold; font-size: 1.4em !important;}" +
                ".v-label-titleLabel{margin-left:5% !important;}"+
                ".v-slot-dateLabel{ margin-top:3%; width:33% !important;}"+
                ".v-slot-searchButton{ margin-top:3%; width:33% !important;}"+
                ".v-caption-medical-appointment-icon {;font-size:1.4em !important;}"+
                ".searchButton{float: right; margin-right: 33%;}"+
                ".v-table-header-cell{font-weight:bold;}"+
                "#cites-table{margin-right: 5%; margin-left: 5%; margin-bottom: 5%;margin-top: 2%;}" +
                ".v-scrollable{overflow: overlay;}" +
                "#see-button{width: 60% !important ;margin-left: 20% !important; background: transparent !important;" +
                "box-shadow: 0 0 0 0 !important; color: rgb(84,170,255) !important;}");
    }

}
