package com.hcemanager.views.generalUI;

import com.mysql.jdbc.Buffer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.io.File;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class ConfirmDownloadWindow extends Window{

    private Button confirmButton;

    public ConfirmDownloadWindow(File file) {

        VerticalLayout layout = new VerticalLayout();
        Label info= new Label("Usted descargará la historía clínica en formato XML, por favor confirme la descarga");
        confirmButton= new Button("Confirmar");
    }

}
