package com.hcemanager.dao.medicalAppointments.rowMappers;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class MedicalAppointmentRowMapper implements ParameterizedRowMapper<MedicalAppointment> {
    @Override
    public MedicalAppointment mapRow(ResultSet resultSet, int i) throws SQLException {
        MedicalAppointment medicalAppointment = new MedicalAppointment();

        medicalAppointment.setIdConsult(resultSet.getInt("idConsult"));
        medicalAppointment.setDate(resultSet.getString("dateConsult"));
        medicalAppointment.setWeight(resultSet.getString("weight"));
        medicalAppointment.setSize(resultSet.getString("size"));
        medicalAppointment.setBloodPressure(resultSet.getString("bloodPressure"));
        medicalAppointment.setTemperature(resultSet.getString("temperature"));
        medicalAppointment.setPulse(resultSet.getString("pulse"));
        medicalAppointment.setFamilyHistory(resultSet.getString("familyHistory"));
        medicalAppointment.setPersonalHistory(resultSet.getString("personalHistory"));
        medicalAppointment.setHabits(resultSet.getString("habits"));
        medicalAppointment.setQueryReason(resultSet.getString("reasonConsult"));
        medicalAppointment.setPhysicalExamination(resultSet.getString("physicalExamination"));
        medicalAppointment.setDisease(resultSet.getString("disease"));
        medicalAppointment.setGroupDisease(resultSet.getString("groupDisease"));
        medicalAppointment.setObservationDisease(resultSet.getString("observationDisease"));
        try {
            medicalAppointment.setPatient(SpringServices.getUserDAO().getUser(resultSet.getString("User_idUser")));
            medicalAppointment.setDoctor(SpringServices.getUserDAO().getUser(resultSet.getString("User_idDoctor")));
        } catch (UserNotExistsException e) {
            e.printStackTrace();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }

        return medicalAppointment;
    }
}
