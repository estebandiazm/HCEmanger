package com.hcemanager.views.doctorModule;

import com.hcemanager.models.medicalAppointments.DicomImage;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class DicomImageWindow extends Window {

    public DicomImageWindow(DicomImage dicomImage) {
        setWidth(50.0f,Unit.PERCENTAGE);
        setResizable(false);
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/dicom/";
        File imageDicomJpeg = new File(path+dicomImage.getIdDicomImage()+".jpg");
        try {
            FileOutputStream outputStream = new FileOutputStream(imageDicomJpeg);
            outputStream.write(dicomImage.getImage().getBytes(1,(int)dicomImage.getImage().length()));
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Embedded embedded = new Embedded(dicomImage.getIdDicomImage(),new FileResource(imageDicomJpeg));
        embedded.setStyleName("dicom");
        TextArea metadataTextArea = new TextArea("Metadatos");
        metadataTextArea.setStyleName("dicom-metadata");
        metadataTextArea.setValue(dicomImage.getMetadata());
        metadataTextArea.setEnabled(false);

        horizontalLayout.addComponent(embedded);
        horizontalLayout.addComponent(metadataTextArea);

        setContent(horizontalLayout);

        this.setModal(true);
        setCaption(dicomImage.getIdDicomImage());
        setCSS();

    }

    private void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".dicom > img{width: 60% !important;margin: 3% !important;margin-top: -4% !important;}" +
                ".dicom-metadata{margin-left: -125% !important ;margin-top: -15% !important;width: 115% !important;" +
                "height: 19.5em !important;}");
    }
}
