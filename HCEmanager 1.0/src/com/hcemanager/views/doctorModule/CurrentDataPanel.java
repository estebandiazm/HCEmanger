package com.hcemanager.views.doctorModule;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

/**
 * @author daniel
 */
public class CurrentDataPanel extends Panel {

    private TextArea currentDataTextArea;
    private Button saveButton;

    public CurrentDataPanel(){
        super("Datos actuales");
        setStyleName("currentPanel");
        setHeight(100.0f, Unit.PERCENTAGE);


        currentDataTextArea = new TextArea();
        currentDataTextArea.setId("current-text-area");
        currentDataTextArea.setWidth(100.0f,Unit.PERCENTAGE);

        saveButton = new Button("Guardar", FontAwesome.SAVE);
        saveButton.setId("current-save-button");
        saveButton.setWidth(35.0f,Unit.PERCENTAGE);

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100.0f,Unit.PERCENTAGE);
        layout.setMargin(true);
        layout.addComponent(currentDataTextArea);
        layout.addComponent(saveButton);
        setContent(layout);
        setSizeFull();
        setCSS();
    }

    public TextArea getCurrentDataTextArea() {
        return currentDataTextArea;
    }

    public void setCurrentDataTextArea(TextArea currentDataTextArea) {
        this.currentDataTextArea = currentDataTextArea;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".currentPanel{border-radius:0px !important; margin-left:3% !important; margin-bottom:3%;" +
                "width:94% !important;}" +
                "#current-text-area{height:10em !important;}" +
                ".v-textarea{border-radius: 0px !important;}" +
                "#current-save-button{margin-top:2% !important;}");
    }

}
