package com.hcemanager.models.medicalAppointments;

import com.hcemanager.models.users.User;

import java.sql.Blob;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class DicomImage {

    private String idDicomImage;
    private Blob image;
    private String metadata;
    private User patient;

    public String getIdDicomImage() {
        return idDicomImage;
    }

    public void setIdDicomImage(String idDicomImage) {
        this.idDicomImage = idDicomImage;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }
}
