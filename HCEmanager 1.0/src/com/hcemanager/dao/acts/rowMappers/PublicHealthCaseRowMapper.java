package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Observation;
import com.hcemanager.models.acts.PublicHealthCase;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class PublicHealthCaseRowMapper implements ParameterizedRowMapper<PublicHealthCase> {

    ObservationRowMapper observationRowMapper = new ObservationRowMapper();

    /**
     * Make a PublicHealthCase mapper
     * @param resultSet argument
     * @param i index
     * @return a PublicHealthCase object
     * @throws java.sql.SQLException
     */
    @Override
    public PublicHealthCase mapRow(ResultSet resultSet, int i) throws SQLException {

        PublicHealthCase publicHealthCase = new PublicHealthCase();
        Observation observation = observationRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // PublicHealthCase arguments
        // -------------------------------------------------------------------------------------------------------------

        publicHealthCase.setIdPublicHealthCase(resultSet.getString("idPublicHealthCase"));

        // -------------------------------------------------------------------------------------------------------------
        // Observation arguments
        // -------------------------------------------------------------------------------------------------------------

        publicHealthCase.setIdObservation(observation.getIdObservation());
        publicHealthCase.setValueObservation(observation.getValueObservation());

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        publicHealthCase.setIndependentInd(observation.isIndependentInd());
        publicHealthCase.setInterruptibleInd(observation.isInterruptibleInd());
        publicHealthCase.setNegationInd(observation.isNegationInd());
        publicHealthCase.setEffectiveTime(observation.getEffectiveTime());
        publicHealthCase.setActivityTime(observation.getActivityTime());
        publicHealthCase.setAvailabilityTime(observation.getAvailabilityTime());
        publicHealthCase.setRepeatNumber(observation.getRepeatNumber());
        publicHealthCase.setDerivationExpr(observation.getDerivationExpr());
        publicHealthCase.setId(observation.getId());
        publicHealthCase.setText(observation.getText());
        publicHealthCase.setTitle(observation.getTitle());

        return publicHealthCase;
    }
}
