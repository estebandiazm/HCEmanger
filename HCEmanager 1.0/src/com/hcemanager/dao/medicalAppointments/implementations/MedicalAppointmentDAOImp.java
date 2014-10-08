package com.hcemanager.dao.medicalAppointments.implementations;

import com.hcemanager.dao.medicalAppointments.interfaces.MedicalAppointmentDAO;
import com.hcemanager.dao.medicalAppointments.rowMappers.MedicalAppointmentRowMapper;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentCannotBeDeletedException;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentExistsException;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentNotExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class MedicalAppointmentDAOImp extends JdbcDaoSupport implements MedicalAppointmentDAO{

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentences
    //------------------------------------------------------------------------------------------------------------------
    public static final String INSERT = "insert into Consult (idConsult,dateConsult,weight,size,bloodPressure,temperature,pulse,familyHistory,personalHistory,habits,reasonConsult, physicalExamination,disease,groupDisease,observationDisease,User_idUser,User_idDoctor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    public static final String UPDATE = "update Consult set dateConsult=?, weight=?, size=?, bloodPressure=?, temperature=?, pulse=?, familyHistory=?, personalHistory=?, habits=?, reasonConsult=?, physicalExamination=?, disease=?, groupDisease=?, observationDisease=?, User_idUser=?,User_idDoctor=? where idConsult=?;";
    public static final String DELETE = "delete from Consult where idConsult=?;";
    public static final String SELECT_MEDICAL_APPOINTMENT = "select * from Consult where idConsult=?;";
    public static final String SELECT_MEDICAL_APPOINTMENTS = "select * from Consult;";
    public static final String SELECT_BY_USER = "select * from Consult where User_idUser=?;";


    //------------------------------------------------------------------------------------------------------------------
    //Methods implements
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method that insert a Medical Appointment
     * @param medicalAppointment
     * @throws MedicalAppointmentExistsException
     */
    @Override
    public void insertMedicalAppointment(MedicalAppointment medicalAppointment) throws MedicalAppointmentExistsException {

        try {
            getJdbcTemplate().update(INSERT,
                    medicalAppointment.getIdConsult(),
                    medicalAppointment.getDate().toString(),
                    medicalAppointment.getWeight(),
                    medicalAppointment.getSize(),
                    medicalAppointment.getBloodPressure(),
                    medicalAppointment.getTemperature(),
                    medicalAppointment.getPulse(),
                    medicalAppointment.getFamilyHistory(),
                    medicalAppointment.getPersonalHistory(),
                    medicalAppointment.getHabits(),
                    medicalAppointment.getQueryReason(),
                    medicalAppointment.getPhysicalExamination(),
                    medicalAppointment.getDisease(),
                    medicalAppointment.getGroupDisease(),
                    medicalAppointment.getObservationDisease(),
                    medicalAppointment.getPatient().getIdUser(),
                    medicalAppointment.getDoctor().getIdUser());
        }catch (DataIntegrityViolationException e){
            throw new MedicalAppointmentExistsException(e);
        }
    }

    @Override
    public void updateMedicalAppointment(MedicalAppointment medicalAppointment) throws MedicalAppointmentExistsException {

        try {
            getJdbcTemplate().update(UPDATE,
                    medicalAppointment.getDate(),
                    medicalAppointment.getWeight(),
                    medicalAppointment.getSize(),
                    medicalAppointment.getBloodPressure(),
                    medicalAppointment.getTemperature(),
                    medicalAppointment.getPulse(),
                    medicalAppointment.getFamilyHistory(),
                    medicalAppointment.getPersonalHistory(),
                    medicalAppointment.getHabits(),
                    medicalAppointment.getQueryReason(),
                    medicalAppointment.getPhysicalExamination(),
                    medicalAppointment.getDisease(),
                    medicalAppointment.getGroupDisease(),
                    medicalAppointment.getObservationDisease(),
                    medicalAppointment.getPatient().getIdUser(),
                    medicalAppointment.getDoctor().getIdUser(),
                    medicalAppointment.getIdConsult());
        } catch (DataIntegrityViolationException e){
            throw new MedicalAppointmentExistsException(e);
        }

    }

    @Override
    public void deleteMedicalAppointment(int idMedicalAppointment) throws MedicalAppointmentCannotBeDeletedException, MedicalAppointmentNotExistsException {
        try {

            int rows = getJdbcTemplate().update(DELETE, idMedicalAppointment);
            if (rows==0){
                throw new MedicalAppointmentNotExistsException();
            }
        }catch (DataIntegrityViolationException e){
            throw new MedicalAppointmentCannotBeDeletedException(e);
        }
    }

    @Override
    public MedicalAppointment getMedicalAppointment(int idMedicalAppointment) throws MedicalAppointmentNotExistsException {

        try {
            MedicalAppointment medicalAppointment = getJdbcTemplate().queryForObject(SELECT_MEDICAL_APPOINTMENT,
                    new MedicalAppointmentRowMapper(), idMedicalAppointment);
            return medicalAppointment;
        } catch (EmptyResultDataAccessException e){
            throw new MedicalAppointmentNotExistsException(e);
        }
    }

    @Override
    public List<MedicalAppointment> getMedicalAppointments() throws MedicalAppointmentNotExistsException {

        try {

            List<MedicalAppointment> medicalAppointments = getJdbcTemplate().query(SELECT_MEDICAL_APPOINTMENTS, new MedicalAppointmentRowMapper());
            return medicalAppointments;
        }catch (EmptyResultDataAccessException e){
            throw new MedicalAppointmentNotExistsException(e);
        }

    }

    @Override
    public List<MedicalAppointment> getMedicalAppointmentsByUser(String idUser) throws MedicalAppointmentNotExistsException {

        try{
            List<MedicalAppointment> medicalAppointments = getJdbcTemplate().query(SELECT_BY_USER, new MedicalAppointmentRowMapper(), idUser);
            return medicalAppointments;
        } catch (EmptyResultDataAccessException e){
            throw new MedicalAppointmentNotExistsException(e);
        }

    }
}
