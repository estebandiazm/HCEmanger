package com.hcemanager.views.doctorModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.DicomImageExistsException;
import com.hcemanager.exceptions.medicalAppointments.DicomImageNotExistsException;
import com.hcemanager.models.medicalAppointments.DicomImage;
import com.hcemanager.models.users.User;
import com.hcemanager.utils.Dicom;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import com.vaadin.data.Property;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Window;
import org.apache.commons.io.FileUtils;


import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;


/**
 * @author Daniel Bellon & Juan Diaz
 */
public class ImagesPanel extends Panel {

    private Label infoLabel;
    private ProgressBar progress;
    private ListSelect imagesListSelect;
    private User user;


    public ImagesPanel(User user) {
        this.user = user;
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();

        infoLabel = new Label("Arrastre la imagen DICOM aquí");
        infoLabel.setStyleName("dragNdrop-label");

        imagesListSelect = new ListSelect("Seleccione la imágen");
        imagesListSelect.setStyleName("images-list");
        imagesListSelect.setHeight(27.0f,Unit.EM);
        setListSelectImages(imagesListSelect);
        imagesListSelect.setRows(10);
        imagesListSelect.setSizeFull();


        //Button upload

        LineBreakCounter lineBreakCounter = new LineBreakCounter();
        lineBreakCounter.setSlow(false);


        Upload upload = new Upload(null,lineBreakCounter);
        UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(upload,lineBreakCounter);

        upload.setImmediate(true);
        upload.setButtonCaption("Subir");
        upload.setIcon(FontAwesome.FILE_IMAGE_O);
        upload.setStyleName("upload-button");
        upload.addStartedListener(new Upload.StartedListener() {
            @Override
            public void uploadStarted(Upload.StartedEvent startedEvent) {
                if (uploadInfoWindow.getParent()==null){
                    getUI().getCurrent().addWindow(uploadInfoWindow);
                }
                uploadInfoWindow.setClosable(false);
            }
        });
        upload.addFinishedListener(new Upload.FinishedListener() {
            @Override
            public void uploadFinished(Upload.FinishedEvent finishedEvent) {
                uploadInfoWindow.setClosable(true);
            }
        });


        //Drga and Drop

        VerticalLayout dropLayout = new VerticalLayout(infoLabel);
//        dropLayout.addComponent(upload);
        dropLayout.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
        dropLayout.setWidth(480.0f, Unit.PIXELS);
        dropLayout.setHeight(400.0f,Unit.PIXELS);
        dropLayout.setStyleName("drop-area");

        ImageDropBox dropBox = new ImageDropBox(dropLayout);
        dropBox.setSizeUndefined();

        progress = new ProgressBar();
        progress.setIndeterminate(true);
        progress.setVisible(false);

        dropLayout.addComponent(progress);

        horizontalLayout.addComponent(imagesListSelect);
        horizontalLayout.addComponent(dropBox);

        setContent(horizontalLayout);
        setCSS();
    }

    /**
     * Set de list of images dicom of the user.
     * @param listSelectImages
     */
    private void setListSelectImages(ListSelect listSelectImages){
        List<DicomImage> images = getListDicomImages();
        for (DicomImage image:images){
            listSelectImages.addItem(image);
            listSelectImages.setItemCaption(image,image.getIdDicomImage());
        }
        listSelectImages.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                DicomImage dicomImage = (DicomImage) valueChangeEvent.getProperty().getValue();
                getUI().getCurrent().addWindow(new DicomImageWindow(dicomImage));
            }
        });
    }

    private List<DicomImage> getListDicomImages(){
        List<DicomImage> dicomImagesByuser = null;
        try {
            dicomImagesByuser = SpringServices.getDicomImageDAO().getDicomImagesByuser(user.getIdUser());
        } catch (DicomImageNotExistsException e) {
            e.printStackTrace();
        }

        return dicomImagesByuser;
    }

    private class ImageDropBox extends DragAndDropWrapper implements
            DropHandler {
        private static final long FILE_SIZE_LIMIT = 3 * 1024 * 1024; // 2MB

        public ImageDropBox(final Component root) {
            super(root);
            setDropHandler(this);
        }

        @Override
        public void drop(final DragAndDropEvent dropEvent) {

            // expecting this to be an html5 drag
            final WrapperTransferable tr = (WrapperTransferable) dropEvent
                    .getTransferable();
            final Html5File[] files = tr.getFiles();
            if (files != null) {
                for (final Html5File html5File : files) {
                    final String fileName = html5File.getFileName();


                    if (html5File.getFileSize() > FILE_SIZE_LIMIT) {
                        Notification
                                .show("File rejected. Max 2Mb files are accepted by Sampler",
                                        Notification.Type.WARNING_MESSAGE);
                    } else {

                        final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                        final StreamVariable streamVariable = new StreamVariable() {

                            @Override
                            public OutputStream getOutputStream() {
                                return bas;
                            }

                            @Override
                            public boolean listenProgress() {
                                return false;
                            }

                            @Override
                            public void onProgress(
                                    final StreamingProgressEvent event) {
                            }

                            @Override
                            public void streamingStarted(
                                    final StreamingStartEvent event) {
                            }

                            @Override
                            public void streamingFinished(
                                    final StreamingEndEvent event) {
                                progress.setVisible(false);
                                showFile(fileName, html5File.getType(), bas);
                            }

                            @Override
                            public void streamingFailed(
                                    final StreamingErrorEvent event) {
                                progress.setVisible(false);
                            }

                            @Override
                            public boolean isInterrupted() {
                                return false;
                            }
                        };
                        html5File.setStreamVariable(streamVariable);
                        progress.setVisible(true);
                    }
                }

            } else {
                final String text = tr.getText();
                if (text != null) {
                    showText(text);
                }
            }
        }

        private void showText(final String text) {
            showComponent(new Label(text), "Wrapped text content");
        }

        private void showFile(final String name, final String type,
                              final ByteArrayOutputStream bas) {
            // resource for serving the file contents
            final StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                @Override
                public InputStream getStream() {
                    if (bas != null) {
                        final byte[] byteArray = bas.toByteArray();
                        return new ByteArrayInputStream(byteArray);
                    }
                    return null;
                }
            };
            final StreamResource resource = new StreamResource(streamSource,
                    name);

            final String  PATH =VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/dicom/";



            File fileDicom =new File(PATH+name);

            try {
                FileOutputStream outputStream = new FileOutputStream(fileDicom);
                bas.writeTo(outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }




            // show the file contents - images only for now
//            final Embedded embedded = new Embedded(name, fileResource);
            showComponent(handlingDicom(fileDicom,name), name);
        }

        /**
         * build a horizontal layout with image dicom and metadata
         * @param fileDicom
         * @param name
         * @return
         */
        private HorizontalLayout handlingDicom(File fileDicom, String name){
            HorizontalLayout horizontalLayout = new HorizontalLayout();

            Dicom dicom = new Dicom(fileDicom);
            TextArea dicomTextArea =  new TextArea("Datos;");
            dicomTextArea.setStyleName("dicom-data");
            String dicomMetadata ="";
            try {
                dicomMetadata = dicom.showDicom();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dicomTextArea.setValue(dicomMetadata);
            File imageDicomJpeg = dicom.covertDicomToJpg(name, fileDicom);

            saveDicomImage(imageDicomJpeg, dicomMetadata,name);

            FileResource fileResource = new FileResource(imageDicomJpeg);
            Embedded embedded = new Embedded(name,fileResource);
            embedded.setWidth(50.0f,Unit.PERCENTAGE);
            embedded.setStyleName("dicom-image");

            horizontalLayout.addComponent(embedded);
            horizontalLayout.addComponent(dicomTextArea);

            return horizontalLayout;
        }

        private void saveDicomImage(File dicomJpeg, String metadata, String name){


            DicomImage dicomImage = new DicomImage();
            try {
                Blob blob = new SerialBlob(FileUtils.readFileToByteArray(dicomJpeg));
                dicomImage.setImage(blob);
                dicomImage.setMetadata(metadata);
                dicomImage.setPatient(user);
                dicomImage.setIdDicomImage(name);

                SpringServices.getDicomImageDAO().insertDicomImage(dicomImage);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SerialException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DicomImageExistsException e) {
                Notification.show("La imagen ya existe", Notification.Type.WARNING_MESSAGE);
            }


        }

        private void showComponent(  Component c, final String name) {
            final VerticalLayout layout = new VerticalLayout();
            layout.setSizeUndefined();
            layout.setMargin(true);
            Window w = new Window(name, layout);
            w.addStyleName("dropdisplaywindow");
            w.setWidth(50.0f,Unit.PERCENTAGE);
//            w.setSizeUndefined();
            w.setResizable(false);
//            c.setSizeFull();
            layout.addComponent(c);
            UI.getCurrent().addWindow(w);

        }

        @Override
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }
    }

    private static class UploadInfoWindow extends Window implements
            Upload.StartedListener, Upload.ProgressListener,
            Upload.FailedListener, Upload.SucceededListener,
            Upload.FinishedListener {
        private final Label state = new Label();
        private final Label result = new Label();
        private final Label fileName = new Label();
        private final Label textualProgress = new Label();

        private final ProgressBar progressBar = new ProgressBar();
        private final Button cancelButton;
        private final LineBreakCounter counter;

        public UploadInfoWindow(final Upload upload,
                                final LineBreakCounter lineBreakCounter) {
            super("Status");
            this.counter = lineBreakCounter;

            setWidth(350, Unit.PIXELS);

            addStyleName("upload-info");

            setResizable(false);
            setDraggable(true);

            final FormLayout l = new FormLayout();
            setContent(l);
            l.setMargin(true);

            final HorizontalLayout stateLayout = new HorizontalLayout();
            stateLayout.setSpacing(true);
            stateLayout.addComponent(state);

            cancelButton = new Button("Cancel");
            cancelButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(final Button.ClickEvent event) {
                    upload.interruptUpload();
                }
            });
            cancelButton.setVisible(false);
            cancelButton.setStyleName("small");
            stateLayout.addComponent(cancelButton);

            stateLayout.setCaption("Current state");
            state.setValue("Idle");
            l.addComponent(stateLayout);

            fileName.setCaption("File name");
            l.addComponent(fileName);

            result.setCaption("Line breaks counted");
            l.addComponent(result);

            progressBar.setCaption("Progress");
            progressBar.setVisible(false);
            l.addComponent(progressBar);

            textualProgress.setVisible(false);
            l.addComponent(textualProgress);

            upload.addStartedListener(this);
            upload.addProgressListener(this);
            upload.addFailedListener(this);
            upload.addSucceededListener(this);
            upload.addFinishedListener(this);

        }

        @Override
        public void uploadFinished(final Upload.FinishedEvent event) {
            state.setValue("Idle");
            progressBar.setVisible(false);
            textualProgress.setVisible(false);
            cancelButton.setVisible(false);
        }

        @Override
        public void uploadStarted(final Upload.StartedEvent event) {
            // this method gets called immediately after upload is started
            progressBar.setValue(0f);
            progressBar.setVisible(true);
            UI.getCurrent().setPollInterval(500);
            textualProgress.setVisible(true);
            // updates to client
            state.setValue("Uploading");
            fileName.setValue(event.getFilename());

            cancelButton.setVisible(true);
        }

        @Override
        public void updateProgress(final long readBytes,
                                   final long contentLength) {
            // this method gets called several times during the update
            progressBar.setValue(new Float(readBytes / (float) contentLength));
            textualProgress.setValue("Processed " + readBytes + " bytes of "
                    + contentLength);
            result.setValue(counter.getLineBreakCount() + " (counting...)");
        }

        @Override
        public void uploadSucceeded(final Upload.SucceededEvent event) {
            result.setValue(counter.getLineBreakCount() + " (total)");
        }

        @Override
        public void uploadFailed(final Upload.FailedEvent event) {
            result.setValue(counter.getLineBreakCount()
                    + " (counting interrupted at "
                    + Math.round(100 * progressBar.getValue()) + "%)");
        }
    }

    private static class LineBreakCounter implements Upload.Receiver {
        private int counter;
        private int total;
        private boolean sleep;

        /**
         * return an OutputStream that simply counts lineends
         */
        @Override
        public OutputStream receiveUpload(final String filename,
                                          final String MIMEType) {
            counter = 0;
            total = 0;
            return new OutputStream() {
                private static final int searchedByte = '\n';

                @Override
                public void write(final int b) throws IOException {
                    total++;
                    if (b == searchedByte) {
                        counter++;
                    }
                    if (sleep && total % 1000 == 0) {
                        try {
                            Thread.sleep(100);
                        } catch (final InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }

        public int getLineBreakCount() {
            return counter;
        }

        public void setSlow(final boolean value) {
            sleep = value;
        }

    }


    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".dragNdrop-label{font-size: 1.5em;color: rgb(223, 224, 224);font-weight: bold;" +
                "margin-left; auto !important; margin-right:auto !important; width: auto !important;}" +
                ".drop-area{width: 118% !important;border: dashed 3px rgb(223, 224, 224); border-radius: 15px !important;margin: 3%;" +
                "margin-left: 7%; margin-top:8% !important; height:27em !important;}" +
                ".v-ddwrapper{width:100% !important;}" +
                ".v-panel-details-panel{height:32em !important; border-radius:0px !important;}" +
                ".v-caption-upload-button{margin-left: 34% !important;font-size: 4em !important;margin-top: -30% !important;}" +
                ".v-slot-upload-button{width: 28% !important; margin-left: auto !important; margin-right: auto !important;" +
                "color: rgb(223, 224, 224);}" +
                ".v-slot-images-list{width: 30% !important; margin-left:4% !important;}" +
                ".v-select-select{height:27em !important;}" +
                ".dicom-image > img{width:60% !important;margin-top:-7% !important;}" +
                ".dicom-data{margin-left: -135% !important;margin-top: -25% !important;width: 135% !important;" +
                "height: 18.7em !important;}");
    }
}
