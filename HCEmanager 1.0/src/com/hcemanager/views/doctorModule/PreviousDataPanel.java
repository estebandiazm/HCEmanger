package com.hcemanager.views.doctorModule;

import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * @author daniel
 */
public class PreviousDataPanel extends Panel {

    private TextArea previousDataTextArea;

    public PreviousDataPanel(Object object){

        super("Datos anteriores");
        setHeight(100.0f, Unit.PERCENTAGE);
        setId("previous-data-panel");

        previousDataTextArea = new TextArea();
        previousDataTextArea.setId("previous-text-area");
        previousDataTextArea.setValue(object.toString());
        previousDataTextArea.setEnabled(false);
        previousDataTextArea.setWidth(100.0f,Unit.PERCENTAGE);
        previousDataTextArea.setSizeFull();

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100.0f,Unit.PERCENTAGE);
        layout.setMargin(true);
        layout.addComponent(previousDataTextArea);

        setContent(layout);
        setSizeFull();
        setCSS();
    }

    public TextArea getPreviousDataTextArea() {
        return previousDataTextArea;
    }

    public void setPreviousDataTextArea(TextArea previousDataTextArea) {
        this.previousDataTextArea = previousDataTextArea;
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add("#previous-data-panel{border-radius:0px !important;" +
                "margin:3% !important;" +
                "width:94% !important;" +
                "height:14em !important;}" +
                "#previous-text-area{height:10em !important;}" +
                ".v-textfield{border-radius:0px !important};" );
    }

}