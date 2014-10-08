package com.hcemanager.views.generalUI;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.io.File;

/**
 * @author daniel on 17/09/14.
 */

@Theme("valo")
@Title("HCE manager")
public class Main extends UI {
    @Override
    public void init(VaadinRequest request) {

        createDirectory();
        MainPanel mainPanel = new MainPanel();
        Carousel carousel = new Carousel();
        VerticalLayout layout = new VerticalLayout();
        ModulesPanel modules = new ModulesPanel();
        Footer footer = new Footer();

        setContent(layout);
        layout.addComponent(mainPanel);
        layout.addComponent(carousel);
        layout.addComponent(modules);
        layout.addComponent(footer);
        setCSS();
    }

    /**
     * create the folders necessaries for the handling of external files.
     */
    public void createDirectory(){
        final String BASE_PATH=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        File dicomFolder = new File(BASE_PATH+"/WEB-INF/dicom/");
        File xmlFolder = new File(BASE_PATH+"/WEB-INF/xml/");
        if (!dicomFolder.exists()){
            dicomFolder.mkdir();
        }
        if (!xmlFolder.exists()){
            xmlFolder.mkdir();
        }
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".v-loading-indicator{background:white !important;}");
    }
}
