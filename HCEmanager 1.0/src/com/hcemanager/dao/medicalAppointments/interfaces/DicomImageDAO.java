package com.hcemanager.dao.medicalAppointments.interfaces;

import com.hcemanager.exceptions.medicalAppointments.DicomImageCannotDeletedException;
import com.hcemanager.exceptions.medicalAppointments.DicomImageExistsException;
import com.hcemanager.exceptions.medicalAppointments.DicomImageNotExistsException;
import com.hcemanager.models.medicalAppointments.DicomImage;

import java.util.List;

public interface DicomImageDAO {

    public void insertDicomImage(DicomImage dicomImage) throws DicomImageExistsException;
    public void updateDicomImage(DicomImage dicomImage) throws DicomImageExistsException;
    public void deleteDicomImage(int id) throws DicomImageCannotDeletedException, DicomImageNotExistsException;
    public DicomImage getDicomImage(int id) throws DicomImageNotExistsException;
    public List<DicomImage> getDicomImages() throws DicomImageNotExistsException;
    public List<DicomImage> getDicomImagesByuser(String idUser) throws DicomImageNotExistsException;
}
