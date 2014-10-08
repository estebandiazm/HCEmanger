package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.Procedure;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class ProcedureRowMapper implements ParameterizedRowMapper<Procedure> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * make a procedure mapper
     * @param resultSet argument
     * @param i index
     * @return a Procedure object
     * @throws java.sql.SQLException
     */
    @Override
    public Procedure mapRow(ResultSet resultSet, int i) throws SQLException {

        Procedure procedure = new Procedure();
        Act act = actRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // Procedure arguments
        // -------------------------------------------------------------------------------------------------------------

        procedure.setIdProcedure(resultSet.getString("idProcedure"));

        // -------------------------------------------------------------------------------------------------------------
        // Acts arguments
        // -------------------------------------------------------------------------------------------------------------

        procedure.setIndependentInd(act.isIndependentInd());
        procedure.setInterruptibleInd(act.isInterruptibleInd());
        procedure.setNegationInd(act.isNegationInd());
        procedure.setEffectiveTime(act.getEffectiveTime());
        procedure.setActivityTime(act.getActivityTime());
        procedure.setAvailabilityTime(act.getAvailabilityTime());
        procedure.setRepeatNumber(act.getRepeatNumber());
        procedure.setDerivationExpr(act.getDerivationExpr());
        procedure.setId(act.getId());
        procedure.setText(act.getText());
        procedure.setTitle(act.getTitle());

        return procedure;
    }
}
