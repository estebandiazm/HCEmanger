package com.hcemanager.utils;

import com.hcemanager.models.users.User;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.vaadin.server.VaadinService;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che2.io.DicomInputStream;


/**
 * @author Daniel Bellon & Juan Diaz
 */
public class Dicom {

    private DicomObject dicomObject;
    private DicomInputStream dicomInputStream;
    private File file;
    private String path;

    public Dicom(File file) {
        this.file=file;
        this.path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/dicom/";
        try {
            readDicom();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDicom() throws IOException {


        dicomInputStream = new DicomInputStream(file);
        dicomObject = dicomInputStream.readDicomObject();


    }

    public String showDicom() throws IOException {
        /*
        CADA UNNO DE LOS TAGS CORRESPONDE A CADA UNO DE LOS DATOS QUE RESIDEN
        EN LA IMAGEN DICOM, UNA VEZ MANIPULADO AL IGUAL QUE CUALQUIER ARCHIVO DEBE SER CERRADO.
        */

        String data ="";

        data += "Tipo de imágen: "+dicomObject.getString(Tag.ImageType)+"\n";
        data += "Nombre institución: "+dicomObject.getString(Tag.InstitutionName)+"\n";
        data += "Funcionamiento físico: "+dicomObject.getString(Tag.PerformingPhysicianName)+"\n";
        data += "Nombre del paciente: "+dicomObject.getString(Tag.PatientName)+"\n";
        data += "Identificacion del paciente: "+dicomObject.getString(Tag.PatientID)+"\n";
        data += "Rango: " +dicomObject.getString(Tag.MilitaryRank)+"\n";
        data += "Fecha: " +dicomObject.getString(Tag.TimeDomainFiltering)+"\n";

        dicomInputStream.close();

        return data;
    }

    public File covertDicomToJpg(String name, File file){
        BufferedImage dicomJpg = null;
        ImageIO.scanForPlugins();
        Iterator<ImageReader> inter = ImageIO.getImageReadersByFormatName("DICOM");
        System.out.println(inter.hasNext());
        ImageReader reader = (ImageReader) inter.next();
        DicomImageReadParam param = (DicomImageReadParam) reader.getDefaultReadParam();
        File dicomJpgFile = null;
        try{
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, false);
            dicomJpg = reader.read(0, param);
            iis.close();

            if (dicomJpg==null){
                System.out.println("\n Error: no se puede leer la imagen dicom");

            }


            path += name+".jpg";
            dicomJpgFile = new File(path);
            OutputStream output = new BufferedOutputStream(new FileOutputStream(dicomJpgFile));
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
            encoder.encode(dicomJpg);
            output.close();
        } catch (IOException e) {
            System.out.println("\nError: couldn't read dicom image!" + e.getMessage());

        }
        return dicomJpgFile;

    }
}
