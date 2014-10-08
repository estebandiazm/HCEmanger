package com.hcemanager.views.generalUI;

import com.vaadin.server.*;
import com.vaadin.ui.*;

import java.io.File;

/**
 * @author daniel on 25/09/14.
 */
public class Carousel extends Panel implements Button.ClickListener {

    public final String BASE_PATH;
    private Image promotion;
    private Label promotionDescription;
    private Label promotionTitle;
    private HorizontalLayout standardsLayout;
    private Button nextButton;
    private Button previousButton;
    private Flash video;
    private VerticalLayout layout;
    private VerticalLayout promotionLayout;

    public Carousel(){

        this.setStyleName("carousel-panel");

        BASE_PATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        layout = new VerticalLayout();
        layout.setWidth(100.0f,Unit.PERCENTAGE);
        layout.setStyleName("carousel-layout");

        promotionLayout= new VerticalLayout();
        promotionLayout.setWidth(100.0f,Unit.PERCENTAGE);
        promotionLayout.setStyleName("promotion-layout");


        buildPromotionOne();
        buildStandardsPanel();

        layout.addComponent(promotionLayout);
        layout.addComponent(standardsLayout);

        this.setContent(layout);

        setCSS();
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
     * this method builds the first promotion
     */
    public void buildPromotionOne(){
        promotion = new Image();
        promotion.setStyleName("promotion-1");
        buildImage(promotion, "promotion1.png");
        nextButton = new Button(FontAwesome.CHEVRON_RIGHT);
        nextButton.setStyleName("carousel-button");
        nextButton.setId("next-button");
        nextButton.addClickListener(this);

        promotionTitle = new Label("Gestión de Historias Clínicas Electrónicas");
        promotionTitle.setStyleName("promotion-title");

        promotionDescription = new Label("Gestionar información base que apoye procesos sanitarios es cada vez más fácil," +
                " HCE manager integra estándares de interoperabilidad semántica, mensajería e intercambio de información," +
                " terminologías clínicas y gestión de imágenes diagnósticas que permiten la construcción de Historias Clínicas" +
                " Electrónicas  con estructuras entendibles por plataformas heterogéneas. ");
        promotionDescription.setStyleName("promotion-description");

        promotionLayout.addComponent(promotion);
        promotionLayout.addComponent(promotionTitle);
        promotionLayout.addComponent(promotionDescription);
        promotionLayout.addComponent(nextButton);

    }

    /**
     * this method builds the second promotion
     */
    public void buildPromotionTwo(){

        promotion = new Image();
        promotion.setStyleName("promotion-2");
        buildImage(promotion, "promotion1.png");

        nextButton = new Button(FontAwesome.CHEVRON_LEFT);
        nextButton.setStyleName("carousel-button");
        nextButton.setId("prev-button");
        nextButton.addClickListener(this);

        video = new Flash(null,new ExternalResource(
                "https://www.youtube.com/v/aFuo6eaYpUI"));
        video.setParameter("allowFullScreen","true");
        video.setWidth(502.0f,Unit.PIXELS);
        video.setHeight(310.0f,Unit.PIXELS);
        video.setStyleName("video");

        promotionTitle = new Label("¿ Qué es HCE manager?");
        promotionTitle.setStyleName("promotion2-title");

        promotionDescription = new Label("HCE manager es una plataforma web para la gestión de Historias Clínicas Electrónicas" +
                " que garantiza interoperabilidad en la información y datos que se almacenen y manejen dentro de ella" +
                " gracias a la inclusión de estándares como DICOM, SNOMED-CT, CCOW, LOINC, HL7. ");
        promotionDescription.setStyleName("promotion2-description");

        promotionLayout.addComponent(promotion);
        promotionLayout.addComponent(video);
        promotionLayout.addComponent(promotionTitle);
        promotionLayout.addComponent(promotionDescription);
        promotionLayout.addComponent(nextButton);
    }

    /**
     * This method build the standards panel
     */
    public void buildStandardsPanel(){
        standardsLayout = new HorizontalLayout();
        standardsLayout.setWidth(100.0f,Unit.PERCENTAGE);
        standardsLayout.setStyleName("standards-layout");

        Label standards = new Label("Soportado por: ");
        standards.setStyleName("standards");

        Image dicomLogo = new Image();
        dicomLogo.setId("dicom-logo");
        dicomLogo.setStyleName("standard-logo");
        buildImage(dicomLogo, "dicom.jpg");

        Image snomedLogo = new Image();
        snomedLogo.setId("snomed-logo");
        snomedLogo.setStyleName("standard-logo");
        buildImage(snomedLogo, "snomed.gif");

        Image hl7Logo = new Image();
        hl7Logo.setId("hl7-logo");
        hl7Logo.setStyleName("standard-logo");
        buildImage(hl7Logo, "hl7.gif");

        Image loincLogo = new Image();
        loincLogo.setId("loinc-logo");
        loincLogo.setStyleName("standard-logo");
        buildImage(loincLogo, "loinc.png");

        standardsLayout.addComponent(standards);
        standardsLayout.addComponent(hl7Logo);
        standardsLayout.addComponent(dicomLogo);
        standardsLayout.addComponent(snomedLogo);
        standardsLayout.addComponent(loincLogo);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

        String event = clickEvent.getButton().getId();
         if (event.equals("next-button")){
             promotionLayout.removeAllComponents();
             buildPromotionTwo();
             buildStandardsPanel();
         }else if(event.equals("prev-button")){
             promotionLayout.removeAllComponents();
             buildPromotionOne();
             buildStandardsPanel();
         }
    }
    /**
     * Set the carousel style sheet
     */
    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".promotion-1{width:61% !important; margin-top:6% !important; margin-left:34% !important;}" +
                ".carousel-layout{width:100% !important;white !important}" +
                ".carousel-panel{border:none !important; border-radius:0px !important;}" +
                ".promotion-title{width:32% !important;color: rgb(84,170,255) !important;font-weight: bold;font-size: 2em;margin-left: 7%;" +
                "margin-top: -32%; text-align:center !important;}" +
                ".promotion-description{width:30% !important;text-align: justify;margin-top: -25% !important;" +
                "margin-left: 8% !important;}" +
                ".standards-layout{background:white; height: 6em !important;}" +
                "#hl7-logo{width:18% !important; margin-left:60% !important;} #dicom-logo, #snomed-logo{width:58% !important;} " +
                "#loinc-logo{width:67% !important; margin-left:-49% !important;}" +
                "#snomed-logo{margin-left:-25% !important;}"+
                ".standard-logo{margin-top: 17% !important;}" +
                ".standards{font-size: 1.3em;margin-left: 80% !important;margin-top: 18% !important;}" +
                ".carousel-button{border: none !important; color: rgb(74,94,189) !important;background:white !important;" +
                "border-radius:4px !important;" +
                "box-shadow: 8px 10px 10px 0px rgba(0,0,0,0.1) !important;}" +
                "#next-button{float: right; margin-top: -26%; margin-right: 9%;}" +
                ".standard-logo{filter: grayscale(100%);-webkit-filter: grayscale(100%);-moz-filter: grayscale(100%);" +
                "-ms-filter: grayscale(100%);-o-filter: grayscale(100%);}" +
                ".standard-logo:hover{-webkit-filter: grayscale(0%);-moz-filter: grayscale(0%);-ms-filter: grayscale(0%);" +
                "-o-filter: grayscale(0%);filter: none; -webkit-transition: all 0.7s ease;-moz-transition: all 0.7s ease;" +
                "-ms-transition: all 0.7s ease;-o-transition: all 0.7s ease;transition: all 0.7s ease;}" +
                ".promotion-2{width:61% !important; margin-top:6% !important; margin-left:3% !important;}" +
                ".video{margin-top: -31% !important; margin-left: 15.2% !important;}" +
                "#prev-button{margin-left:7%; margin-top:-26%;}" +
                ".promotion2-title{width:32% !important;color: rgb(84,170,255) !important;font-weight: bold;font-size: 2em;" +
                "margin-top: -32%; text-align:center !important;float:right !important; margin-right:9% !important;}" +
                ".promotion2-description{width:30% !important;text-align: justify;margin-top: -25% !important;" +
                " float:right !important; margin-right:10% !important;}" +
                ".promotion-layout{height:34em !important;}" +
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
                ".promotion-title:hover, .promotion2-title:hover {\n" +
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
