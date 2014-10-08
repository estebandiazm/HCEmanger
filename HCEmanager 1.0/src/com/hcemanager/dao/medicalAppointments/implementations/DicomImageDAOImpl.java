package com.hcemanager.dao.medicalAppointments.implementations;

import com.google.gwt.dev.util.Empty;
import com.hcemanager.dao.medicalAppointments.interfaces.DicomImageDAO;
import com.hcemanager.dao.medicalAppointments.rowMappers.DicomImageRowMapper;
import com.hcemanager.exceptions.medicalAppointments.DicomImageCannotDeletedException;
import com.hcemanager.exceptions.medicalAppointments.DicomImageExistsException;
import com.hcemanager.exceptions.medicalAppointments.DicomImageNotExistsException;
import com.hcemanager.models.medicalAppointments.DicomImage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class DicomImageDAOImpl extends JdbcDaoSupport implements DicomImageDAO {

    public static final String INSERT = "insert into DicomImage (idDicomImage,image,metadata,User_idUser) values (?,?,?,?);";
    public static final String UPDATE = "update DicomImage set image=?, metadata=?, User_idUser=? where idDicomImage=?;";
    public static final String DELETE = "delete from DicomImage where idDicomImage=?;";
    public static final String SELECT_DICOM_IMAGE = "select * from DicomImage where idDicomImage=?;";
    public static final String SELECT_DICOM_IMAGES = "select * from DicomImage;";
    public static final String SELECT_DICOM_IMAGES_BY_USER = "select * from DicomImage where User_idUser=?;";

    @Override
    public void insertDicomImage(DicomImage dicomImage) throws DicomImageExistsException {
        try{
            getJdbcTemplate().update(INSERT,
                    dicomImage.getIdDicomImage(),
                    dicomImage.getImage(),
                    dicomImage.getMetadata(),
                    dicomImage.getPatient().getIdUser());
        } catch (DataIntegrityViolationException e){
            throw new DicomImageExistsException(e);
        }
    }

    @Override
    public void updateDicomImage(DicomImage dicomImage) throws DicomImageExistsException {
        try{
            getJdbcTemplate().update(UPDATE,
                    dicomImage.getImage(),
                    dicomImage.getMetadata(),
                    dicomImage.getPatient().getIdUser(),
                    dicomImage.getIdDicomImage());
        }catch (DataIntegrityViolationException e){
            throw  new DicomImageExistsException(e);
        }
    }

    @Override
    public void deleteDicomImage(int id) throws DicomImageCannotDeletedException, DicomImageNotExistsException {
        try{
            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new DicomImageNotExistsException();
            }
        }catch (DataIntegrityViolationException e){
            throw new DicomImageCannotDeletedException(e);
        }
    }

    @Override
    public DicomImage getDicomImage(int id) throws DicomImageNotExistsException {
        try{
            DicomImage dicomImage = getJdbcTemplate().queryForObject(SELECT_DICOM_IMAGE, new DicomImageRowMapper(), id);
            return dicomImage;
        }catch (EmptyResultDataAccessException e){
            throw new DicomImageNotExistsException(e);
        }

    }

    @Override
    public List<DicomImage> getDicomImages() throws DicomImageNotExistsException {
        try{
            List<DicomImage> dicomImages = getJdbcTemplate().query(SELECT_DICOM_IMAGES, new DicomImageRowMapper());
            return dicomImages;
        }catch (EmptyResultDataAccessException e){
            throw new DicomImageNotExistsException(e);
        }
    }

    @Override
    public List<DicomImage> getDicomImagesByuser(String idUser) throws DicomImageNotExistsException {
        try{
            List<DicomImage> dicomImages = getJdbcTemplate().query(SELECT_DICOM_IMAGES_BY_USER, new DicomImageRowMapper(), idUser);
            return dicomImages;
        }catch (EmptyResultDataAccessException e){
            throw new DicomImageNotExistsException(e);
        }

    }
}
