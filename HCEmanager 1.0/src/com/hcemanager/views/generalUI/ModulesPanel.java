package com.hcemanager.views.generalUI;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;


/**
 * @author daniel on 26/09/14.
 */
public class ModulesPanel extends HorizontalLayout {

    private VerticalLayout doctorModule;
    private VerticalLayout patientModule;
    private Label doctorTitle;
    private Label patientTitle;
    private Label doctorDescription;
    private Label patientDescription;

    /**
     * The constructor that builds the modules panel
     */
    public ModulesPanel(){

        setWidth(100.0f,Unit.PERCENTAGE);
        setStyleName("modules");
        doctorModule = new VerticalLayout();
        doctorModule.setStyleName("doctor-module-layout");

        patientModule = new VerticalLayout();
        patientModule.setStyleName("patient-module-layout");

        doctorTitle = new Label("Módulo de personal médico");
        doctorTitle.setStyleName("doctor-module-title");

        patientTitle = new Label("Módulo de pacientes");
        patientTitle.setStyleName("patient-module-title");

        doctorDescription = new Label("Total control y registro de las acciones que el personal médico realiza en la " +
                "plataforma, gestione consultas, historias clínicas e imágenes diagnósticas con el módulo de personal médico," +
                " mantenga su información estandarizada y permita que HCE manager interactúe con sus aplicaciones de manera " +
                "transparente, exporte y descargue sus Historias Clínicas. ");
        doctorDescription.setId("doctor-module-description");

        patientDescription = new Label("Permita que sus pacientes puedan tener acceso al registro de los procedimientos" +
                " sanitarios que le competen, a sus imágenes diagnósticas y a su Historia Clínica, descentralice sus " +
                "procesos clínicos y brinde una experiencia de usuario enriquecedora a sus pacientes.  ");
        patientDescription.setId("patient-module-description");

        doctorModule.addComponent(doctorTitle);
        doctorModule.addComponent(doctorDescription);
        patientModule.addComponent(patientTitle);
        patientModule.addComponent(patientDescription);

        this.addComponent(doctorModule);
        this.addComponent(patientModule);
        setCSS();
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".modules{background:white !important;}" +
                ".doctor-module-layout{margin-top:10% !important; margin-left:32% !important;margin-bottom:10% !important;" +
                "width:65% !important;}" +
                ".patient-module-layout{margin-top:10% !important; margin-bottom: 10% !important;margin-left: 3% !important;" +
                "width:65% !important;}" +
                "#patient-module-description, #doctor-module-description{text-align:justify;margin-top: 10% !important;}" +
                ".v-slot-doctor-module-title, .v-slot-patient-module-title{color: rgb(84,170,255) !important;" +
                "font-size: 1.9em !important;font-weight: bold !important; text-align:center !important;}" +
                ".doctor-module-title, .patient-module-title{display:inline !important; margin-left:3% !important;}" +
                ".v-caption-doctor-module-title, .v-caption-patient-module-title{display:inline !important; font-size: 1em !important;}" +
                ".v-slot-doctor-module > .v-widget{text-align:center !important;}" +
                "@keyframes \"agrandar\" {\n" +
                " from {\n" +
                "    -webkit-transform: scale(1.0);   \t   \t\n" +
                "   \t-ms-transform: scale(1.0);\n" +
                "   \ttransform: scale(1.0);\n" +
                " }\n" +
                " to {\n" +
                "    -webkit-transform: scale(1.3);      \n" +
                "   \t-ms-transform: scale(1.3);\n" +
                "   \ttransform: scale(1.3);\n" +
                " }\n" +
                "\n" +
                "}\n" +
                "\n" +
                "@-webkit-keyframes \"agrandar\" {\n" +
                " from {\n" +
                "   -webkit-transform: scale(1.0);\n" +
                "   transform: scale(1.0);\n" +
                " }\n" +
                " to {\n" +
                "   -webkit-transform: scale(1.3);\n" +
                "   transform: scale(1.3);\n" +
                " }\n" +
                "\n" +
                "}\n" +
                "\n" +
                ".doctor-module-title:hover, .patient-module-title:hover{\n" +
                "\t-webkit-animation-name: 'agrandar';\t\n" +
                "\tanimation-name: 'agrandar';\n" +
                "\tanimation-duration: 0.8s;\n" +
                "\t-webkit-animation-duration: 0.8s;\t\n" +
                "\n" +
                "\t-webkit-animation-iteration-count: 2;\n" +
                "\tanimation-iteration-count: 2;\n" +
                "\n" +
                "\t-webkit-animation-timing-function: ease;\n" +
                "\tanimation-timing-function: ease;\n" +
                "\t\n" +
                "\t-webkit-transform: scale(1.3);      \n" +
                "   \t-ms-transform: scale(1.3);\n" +
                "   \ttransform: scale(1.3);\n" +
                "}");
    }
}
