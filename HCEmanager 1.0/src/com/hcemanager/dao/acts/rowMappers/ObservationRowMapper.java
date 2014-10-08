package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.Observation;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class ObservationRowMapper implements ParameterizedRowMapper<Observation> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * make an Observation mapper
     * @param resultSet argument
     * @param i index
     * @return observation
     * @throws java.sql.SQLException
     */
    @Override
    public Observation mapRow(ResultSet resultSet, int i) throws SQLException {

        Observation observation = new Observation();
        Act act =actRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // Observation arguments
        // -------------------------------------------------------------------------------------------------------------

        observation.setIdObservation(resultSet.getString("idObservation"));
        observation.setValueObservation(resultSet.getString("valueObservation"));

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        observation.setIndependentInd(act.isIndependentInd());
        observation.setInterruptibleInd(act.isInterruptibleInd());
        observation.setNegationInd(act.isNegationInd());
        observation.setEffectiveTime(act.getEffectiveTime());
        observation.setActivityTime(act.getActivityTime());
        observation.setAvailabilityTime(act.getAvailabilityTime());
        observation.setRepeatNumber(act.getRepeatNumber());
        observation.setDerivationExpr(act.getDerivationExpr());
        observation.setId(act.getId());
        observation.setText(act.getText());
        observation.setTitle(act.getTitle());

        return observation;
    }
}
