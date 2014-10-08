package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class ActRowMapper implements ParameterizedRowMapper<Act> {

    /**
     * This method make an act mapper
     * @param resultSet argument
     * @param i index
     * @return act
     * @throws SQLException
     */
    @Override
    public Act mapRow(ResultSet resultSet, int i) throws SQLException {

        Act act = new Act();
        act.setActivityTime(resultSet.getString("activityTime"));
        act.setAvailabilityTime(resultSet.getString("availabilityTime"));
        act.setDerivationExpr(resultSet.getString("derivationExpr"));
        act.setEffectiveTime(resultSet.getString("effectiveTime"));
        act.setId(resultSet.getString("idAct"));
        act.setIndependentInd(resultSet.getBoolean("independentInd"));
        act.setInterruptibleInd(resultSet.getBoolean("interruptibleInd"));
        act.setNegationInd(resultSet.getBoolean("negationInd"));
        act.setRepeatNumber(resultSet.getString("repeatNumber"));
        act.setText(resultSet.getString("text"));
        act.setTitle(resultSet.getString("title"));
        return act;
    }
}
