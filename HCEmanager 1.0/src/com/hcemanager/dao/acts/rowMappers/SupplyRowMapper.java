package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.Supply;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class SupplyRowMapper implements ParameterizedRowMapper<Supply> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * Make a Supply mapper
     * @param resultSet arguments
     * @param i index
     * @return a Supply object
     * @throws java.sql.SQLException
     */
    @Override
    public Supply mapRow(ResultSet resultSet, int i) throws SQLException {

        Supply supply = new Supply();
        Act act = actRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // Supply arguments
        // -------------------------------------------------------------------------------------------------------------

        supply.setIdSupply(resultSet.getString("idSupply"));
        supply.setExpectedUseTime(resultSet.getString("expectedUseTime"));
        supply.setQuantity(resultSet.getString("quantity"));

        // -------------------------------------------------------------------------------------------------------------
        // Supply arguments
        // -------------------------------------------------------------------------------------------------------------

        supply.setIndependentInd(act.isIndependentInd());
        supply.setInterruptibleInd(act.isInterruptibleInd());
        supply.setNegationInd(act.isNegationInd());
        supply.setEffectiveTime(act.getEffectiveTime());
        supply.setActivityTime(act.getActivityTime());
        supply.setAvailabilityTime(act.getAvailabilityTime());
        supply.setRepeatNumber(act.getRepeatNumber());
        supply.setDerivationExpr(act.getDerivationExpr());
        supply.setId(act.getId());
        supply.setText(act.getText());
        supply.setTitle(act.getTitle());

        return supply;
    }
}
