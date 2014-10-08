package com.hcemanager.views.generalUI;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import javax.swing.text.Style;
import java.io.File;


/**
 * @author daniel on 24/09/14.
 */
public class Footer extends Panel {

    public final String BASE_PATH;
    private Label danielBellonDescription;
    private Label danielBellon;
    private Image danielImage;
    private Label estebanDiazDescription;
    private Label estebanDiaz;
    private Image estebanImage;
    private Label omarMorenoDescription;
    private Label omarMoreno;
    private Image omarImage;
    private Image hceTeam;

    /**
     * Constructor that builds the home footer
     */
    public Footer(){

        this.setStyleName("footer");
        BASE_PATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        horizontalLayout.setStyleName("authors-description-layout");
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100.0f,Unit.PERCENTAGE);
        layout.setStyleName("footer-layout");


        danielBellonDescription = new Label("Estudiante de Ingeniería de Sistemas y Computación de la Universidad Pedagógica" +
                " y Tecnológica de Colombia");
        danielBellonDescription.setStyleName("author-description");
        estebanDiazDescription = new Label("Estudiante de Ingeniería de Sistemas y Computación de la Universidad Pedagógica" +
                " y Tecnológica de Colombia");
        estebanDiazDescription.setStyleName("author-description");
        omarMorenoDescription = new Label("Ingeniero de Sistemas y Computación de la Universidad Pedagógica y Tecnológica " +
                "de Colombia," +
                "director del proyecto");
        omarMorenoDescription.setStyleName("author-description");


        danielImage = new Image();
        danielImage.setStyleName("developer-image");
        buildImage(danielImage, "Daniel.jpg");
        estebanImage = new Image();
        estebanImage.setStyleName("developer-image");
        buildImage(estebanImage, "Esteban.jpg");
        omarImage = new Image();
        omarImage.setStyleName("director-image");
        buildImage(omarImage, "Omar.jpg");

        hceTeam = new Image();
        hceTeam.setStyleName("hce-team-image");
        buildImage(hceTeam, "hce_manager.png");

        danielBellon = new Label("Daniel Bellón");
        danielBellon.setStyleName("author-name");
        estebanDiaz = new Label("Esteban Díaz");
        estebanDiaz.setStyleName("author-name");
        omarMoreno = new Label("Omar Moreno");
        omarMoreno.setStyleName("author-name");

        Label copyright = new Label("© Copyright 2014, HCEmanager team");
        copyright.setStyleName("copyright");
        VerticalLayout copyrightLayout = new VerticalLayout();
        copyrightLayout.setWidth(100.0f,Unit.PERCENTAGE);
        copyrightLayout.setStyleName("copyright-layout");

        copyrightLayout.addComponent(copyright);

        horizontalLayout.addComponent(new VerticalLayout(danielImage,danielBellon,danielBellonDescription));
        horizontalLayout.addComponent(new VerticalLayout(omarImage,omarMoreno,omarMorenoDescription));
        horizontalLayout.addComponent(new VerticalLayout(estebanImage,estebanDiaz,estebanDiazDescription));

        layout.addComponent(horizontalLayout);
        layout.addComponent(hceTeam);
        layout.addComponent(copyrightLayout);

        this.setContent(layout);
        this.setCSS();
    }

    /**
     * Constructor that builds an user footer
     * @param userType the user type
     */
    public Footer(String userType){

        this.setStyleName("footer-user");
        BASE_PATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100.0f,Unit.PERCENTAGE);
        layout.setStyleName("footer-layout");

        switch (userType){
            case NavigationPanel.ADMIN_USER:
                buildAdminFooter(layout);
                break;
            case NavigationPanel.DOCTOR_USER:
                buildDoctorFooter(layout);
                break;
            case NavigationPanel.PATIENT_USER:
                buildPatientFooter(layout);
                break;
        }

        Label copyright = new Label("© Copyright 2014, HCEmanager team");
        copyright.setStyleName("copyright");
        VerticalLayout copyrightLayout = new VerticalLayout();
        copyrightLayout.setWidth(100.0f,Unit.PERCENTAGE);
        copyrightLayout.setStyleName("copyright-layout");
        copyrightLayout.addComponent(copyright);

        hceTeam = new Image();
        hceTeam.setStyleName("hce-team-image");
        buildImage(hceTeam, "hce_manager.png");

        layout.addComponent(hceTeam);
        layout.addComponent(copyrightLayout);

        setContent(layout);
        setCSS();
    }

    private void buildAdminFooter(Layout layout){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        horizontalLayout.setStyleName("authors-description-layout");

        layout.addComponent(horizontalLayout);
    }

    private void buildDoctorFooter(Layout layout){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        horizontalLayout.setStyleName("authors-description-layout");

        Label consultManagerTitle = new Label("Gestión de consultas");
        consultManagerTitle.setStyleName("user-footer-title");
        Label hceManagerTitle = new Label("Gestión de Historias clínicas");
        hceManagerTitle.setStyleName("user-footer-title");

        Label consultManagerDescription = new Label("La información generada en cada una de sus consultas será gestionada " +
                "fácilmente y se añadira automaticamente a la historia clínica del paciente al que esté tratando.  ");
        consultManagerDescription.setStyleName("user-footer-description");
        Label hceManagerDescription = new Label("HCE manager le permite manejar la información asociada a la historia " +
                "clínica de sus pacientes de manera ordenada, brindándole todas las herramientas para anexar imágenes " +
                "diagnósticas y generar información interoperable que puede descargar y visualizar posteriormente.");
        hceManagerDescription.setStyleName("user-footer-description");


        horizontalLayout.addComponent(new VerticalLayout(consultManagerTitle,consultManagerDescription));
        horizontalLayout.addComponent(new VerticalLayout(hceManagerTitle,hceManagerDescription));
        layout.addComponent(horizontalLayout);
    }

    private void buildPatientFooter(Layout layout){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        horizontalLayout.setStyleName("authors-description-layout");
        layout.addComponent(horizontalLayout);
    }

    public Label getDanielBellonDescription() {
        return danielBellonDescription;
    }

    public void setDanielBellonDescription(Label danielBellonDescription) {
        this.danielBellonDescription = danielBellonDescription;
    }

    public Label getDanielBellon() {
        return danielBellon;
    }

    public void setDanielBellon(Label danielBellon) {
        this.danielBellon = danielBellon;
    }

    public Label getEstebanDiazDescription() {
        return estebanDiazDescription;
    }

    public void setEstebanDiazDescription(Label estebanDiazDescription) {
        this.estebanDiazDescription = estebanDiazDescription;
    }

    public Label getEstebanDiaz() {
        return estebanDiaz;
    }

    public void setEstebanDiaz(Label estebanDiaz) {
        this.estebanDiaz = estebanDiaz;
    }

    public Label getOmarMorenoDescription() {
        return omarMorenoDescription;
    }

    public void setOmarMorenoDescription(Label omarMorenoDescription) {
        this.omarMorenoDescription = omarMorenoDescription;
    }

    public Label getOmarMoreno() {
        return omarMoreno;
    }

    public void setOmarMoreno(Label omarMoreno) {
        this.omarMoreno = omarMoreno;
    }

    public Image getDanielImage() {
        return danielImage;
    }

    public void setDanielImage(Image danielImage) {
        this.danielImage = danielImage;
    }

    public Image getEstebanImage() {
        return estebanImage;
    }

    public void setEstebanImage(Image estebanImage) {
        this.estebanImage = estebanImage;
    }

    public Image getOmarImage() {
        return omarImage;
    }

    public void setOmarImage(Image omarImage) {
        this.omarImage = omarImage;
    }

    /**
     * This method build a Vaadin image
     * @param image the image component to build
     * @param imageName the image file name
     */
    public void buildImage(Image image,String imageName){
        FileResource imageFile = new FileResource(new File(BASE_PATH+"/WEB-INF/images/"+imageName));
        System.out.println("path:"+BASE_PATH);
        image.setSource(imageFile);
    }

    /**
     * This method set the footer style sheet
     */
    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".footer{background:rgb(74,94,189)!important; border-radius: 0px !important; border:none !important;" +
                "height:40em !important;}" +
                ".footer-user{background:rgb(74,94,189) !important;border-radius: 0px !important; border:none !important;}"+
                ".author-name{color:white !important; font-weight:bold !important; width: auto !important;" +
                "margin-left: 25% !important;margin-bottom: 5%;margin-right: auto !important;font-size: 1.5em;}" +
                ".user-footer-title{color:white !important; font-weight:bold !important; width: auto !important;" +
                "margin-left: 25% !important;margin-bottom: 5%;margin-right: auto !important;font-size: 1.5em;}" +
                ".author-description{color:white !important; text-align:justify !important;" +
                " width: 80% !important; margin-left:5% !important}" +
                ".authors-description-layout{margin-left:7% !important; margin-top:5% !important; width:90% !important;}" +
                ".director-image{width: 50% !important;margin-left: 20% !important; margin-bottom: 8% !important;" +
                "border: solid 4px white !important;border-radius: 100px !important;" +
                "box-shadow: 11px 11px 10px 0px rgba(0,0,0,0.1) !important;}" +
                ".user-footer-description{color:white !important; text-align:justify !important;" +
                "width: 80% !important; margin-left:5% !important}" +
                ".developer-image{width: 35% !important;margin-left: 26% !important;margin-right: auto;" +
                "margin-top: 8% !important;margin-bottom: 8% !important;border-radius: 80px!important;" +
                "border: solid 4px white !important;box-shadow: 11px 11px 10px 0px rgba(0,0,0,0.1) !important;}" +
                ".hce-team-image{width:14% !important;margin-top: 5% !important;margin-left: 44% !important; margin-bottom:3% !important;}" +
                ".copyright{width: 100%;color: rgb(74,94,189);margin-top: 1.5%;margin-left: 41%;}" +
                ".copyright-layout{width: 100%;background: white;height: 4em;}" +
                ".v-verticallayout-copyright-layout{margin-top:0.8% !important;}");
    }
}
