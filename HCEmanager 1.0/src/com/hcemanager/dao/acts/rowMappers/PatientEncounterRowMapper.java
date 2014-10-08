package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.PatientEncounter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class PatientEncounterRowMapper implements ParameterizedRowMapper<PatientEncounter> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * Make a PatientEncounter mapper
     * @param resultSet argument
     * @param i index
     * @return a PatientEncounter object
     * @throws SQLException
     */
    @Override
    public PatientEncounter mapRow(ResultSet resultSet, int i) throws SQLException {

        PatientEncounter patientEncounter = new PatientEncounter();
        Act act = actRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // PatientEncounter arguments
        // -------------------------------------------------------------------------------------------------------------

        patientEncounter.setIdPatientEncounter(resultSet.getString("idPatientEncounter"));
        patientEncounter.setLengthOfStayQuantity(resultSet.getString("lengthOfStayQuantity"));
        patientEncounter.setPreAdmitTestInd(resultSet.getBoolean("preAdmitTestInd"));

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        patientEncounter.setIndependentInd(act.isIndependentInd());
        patientEncounter.setInterruptibleInd(act.isInterruptibleInd());
        patientEncounter.setNegationInd(act.isNegationInd());
        patientEncounter.setEffectiveTime(act.getEffectiveTime());
        patientEncounter.setActivityTime(act.getActivityTime());
        patientEncounter.setAvailabilityTime(act.getAvailabilityTime());
        patientEncounter.setRepeatNumber(act.getRepeatNumber());
        patientEncounter.setDerivationExpr(act.getDerivationExpr());
        patientEncounter.setId(act.getId());
        patientEncounter.setText(act.getText());
        patientEncounter.setTitle(act.getTitle());

        return patientEncounter;
    }
}
