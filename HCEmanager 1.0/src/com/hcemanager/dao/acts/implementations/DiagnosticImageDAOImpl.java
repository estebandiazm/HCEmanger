package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.interfaces.DiagnosticImageDAO;
import com.hcemanager.dao.acts.interfaces.ObservationDAO;
import com.hcemanager.dao.acts.rowMappers.DiagnosticImageRowMapper;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.DiagnosticImage;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class DiagnosticImageDAOImpl extends JdbcDaoSupport implements DiagnosticImageDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into DiagnosticImage(idDiagnosticImage,Observation_idObservation,image)" +
            " value (?,?,?);";
    public static final String DELETE = "delete from DiagnosticImage where idDiagnosticImage=?";
    public static final String SELECT_DIAGNOSTIC_IMAGE = "select * from DiagnosticImage inner join Observation " +
            "inner join Act where Observation_idObservation= idObservation and Act_idAc= idAct and idDiagnostic=?";
    public static final String SELECT_DIAGNOSTIC_IMAGES = "select *from DiagnosticImage inner join Observation " +
            "inner join Act where Observation_idObservation= idObservation and Act_idAct=idAct";
    public static final String INSERT_CODE = "insert into DiagnosticImage_has_Codes (DiagnosticImage_idDiagnosticImage,Codes_idCodes,type)" +
            " values (?,?,?)";
    public static final String UPDATE_CODE = "update DiagnosticImage_has_Codes set Codes_idCodes = ? " +
            "where DiagnosticImage_idDiagnosticImage=? and type=?";
    public static final String DELETE_CODES = "delete from DiagnosticImage_has_codes where DiagnosticImage_idDiagnosticImage=?";
    public static final String SELECT_CODES = "select * from DiagnosticImage_has_Codes inner join Codes where Codes_idCodes=idCodes " +
            "and DiagnosticImage_idDiagnosticImage=?";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a diagnostic image in the db
     * @param diagnosticImage a DiagnosticImage object
     * @throws DiagnosticImageExistsException
     */
    @Override
    public void insertDiagnosticImage(DiagnosticImage diagnosticImage) throws DiagnosticImageExistsException, ObservationExistsException, ActExistsException, CodeExistsException {
        try {
            SpringServices.getObservationDAO().insertObservation(diagnosticImage);

            getJdbcTemplate().update(INSERT,
                    diagnosticImage.getIdDiagnosticImage(),
                    diagnosticImage.getIdObservation());
        }catch (DataIntegrityViolationException e){
            throw new DiagnosticImageExistsException(e);
        }
        try {
            getJdbcTemplate().update(INSERT_CODE,
                    diagnosticImage.getIdDiagnosticImage(),
                    diagnosticImage.getSubjectOrientationCode().getId(),
                    "subjectOrientationCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update a diagnostic image in the db
     * @param diagnosticImage a DiagnosticImage object
     * @throws DeviceTaskExistsException
     * @throws DiagnosticImageNotExistsException
     */
    @Override
    public void updateDiagnosticImage(DiagnosticImage diagnosticImage) throws DeviceTaskExistsException, DiagnosticImageNotExistsException, ObservationExistsException, ObservationNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        SpringServices.getObservationDAO().updateObservation(diagnosticImage);
        updateCodes(diagnosticImage);
    }

    /**
     * Delete a diagnostic image from the db
     * @param id the diagnostic image id
     * @throws DiagnosticImageNotExistsException
     * @throws DiagnosticImageCannotBeDeleted
     */
    @Override
    public void deleteDiagnosticImage(String id) throws DiagnosticImageNotExistsException, DiagnosticImageCannotBeDeleted, ObservationNotExistsException, ObservationCannotBeDeleteException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new DiagnosticImageNotExistsException();
            }

            DiagnosticImage diagnosticImage = getDiagnosticImage(id);
            SpringServices.getObservationDAO().deleteObservation(diagnosticImage.getIdObservation());

        }catch(DataIntegrityViolationException e){
            throw new DiagnosticImageCannotBeDeleted(e);
        }

    }

    /**
     * Get a diagnostic image by id
     * @param id the diagnostic image id
     * @return a diagnostic image
     * @throws DiagnosticImageNotExistsException
     */
    @Override
    public DiagnosticImage getDiagnosticImage(String id) throws DiagnosticImageNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {

            DiagnosticImage diagnosticImage = getJdbcTemplate().queryForObject(SELECT_DIAGNOSTIC_IMAGE,
                    new DiagnosticImageRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            ObservationDAO observationDAO = SpringServices.getObservationDAO();
            actDAO.setActCodes(diagnosticImage,actDAO.getActCodes(diagnosticImage.getId()));
            observationDAO.setObservationCodes(diagnosticImage,observationDAO.getObservationCodes(diagnosticImage.getIdObservation()));
            setDiagnosticImagesCodes(diagnosticImage,getDiagnosticImageCodes(id));
            return diagnosticImage;

        }catch (EmptyResultDataAccessException e){
            throw new DiagnosticImageNotExistsException(e);
        }
    }

    /**
     * Get all the diagnostic images in the db
     * @return a list with all diagnostic images
     * @throws DiagnosticImageNotExistsException
     */
    @Override
    public List<DiagnosticImage> getDiagnosticImages() throws DiagnosticImageNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {

            List<DiagnosticImage> diagnosticImages= getJdbcTemplate().query(SELECT_DIAGNOSTIC_IMAGES,
                    new DiagnosticImageRowMapper());

            for (DiagnosticImage diagnosticImage:diagnosticImages){
                ActDAO actDAO = SpringServices.getActDAO();
                ObservationDAO observationDAO = SpringServices.getObservationDAO();
                actDAO.setActCodes(diagnosticImage,actDAO.getActCodes(diagnosticImage.getId()));
                observationDAO.setObservationCodes(diagnosticImage,observationDAO.getObservationCodes(diagnosticImage.getIdObservation()));
                setDiagnosticImagesCodes(diagnosticImage, getDiagnosticImageCodes(diagnosticImage.getIdDiagnosticImage()));
            }
            return diagnosticImages;

        }catch (EmptyResultDataAccessException e){
            throw new DiagnosticImageNotExistsException(e);
        }
    }

    /**
     * Get the diagnostic image codes
     * @param id the diagnostic image id
     * @return a list of diagnostic image codes
     * @throws CodeNotExistsException
     */
    public List<Code> getDiagnosticImageCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set the diagnostic image codes
     * @param diagnosticImagesCodes a DiagnosticImage object
     * @param codes a list with its codes
     * @throws CodeIsNotValidException
     */
    public void setDiagnosticImagesCodes(DiagnosticImage diagnosticImagesCodes, List<Code> codes) throws CodeIsNotValidException {
        try {
            for (Code code:codes){
                String type= code.getType();

                if (type.equalsIgnoreCase("subjectOrientationCode")){
                    diagnosticImagesCodes.setSubjectOrientationCode(code);
                }
            }
        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Update the diagnosticImage Codes
     * @param diagnosticImage a DiagnosticImage object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(DiagnosticImage diagnosticImage) throws CodeNotExistsException, CodeExistsException {

        List<Code> oldCodes = getDiagnosticImageCodes(diagnosticImage.getIdDiagnosticImage());

        for (Code code:oldCodes){
            if (code.getType().equalsIgnoreCase("subjectOrientationCode")){
                if (!code.getId().equals(diagnosticImage.getSubjectOrientationCode().getId())){
                    try {
                        getJdbcTemplate().update(UPDATE_CODE,diagnosticImage.getSubjectOrientationCode().getId(),
                                diagnosticImage.getIdDiagnosticImage(),"subjectOrientationCode");
                    }catch (DataIntegrityViolationException e){
                        throw new CodeExistsException(e);
                    }
                }
            }
        }
    }
}
