package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.FinancialContract;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class FinancialContractRowMapper implements ParameterizedRowMapper<FinancialContract> {

    ActRowMapper actRowMapper = new ActRowMapper();
    /**
     * Make a financial contract mapper
     * @param resultSet argument
     * @param i index
     * @return financial contract
     * @throws java.sql.SQLException
     */
    @Override
    public FinancialContract mapRow(ResultSet resultSet, int i) throws SQLException {

        FinancialContract financialContract = new FinancialContract();
        Act act = actRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // FinancialContract arguments
        // -------------------------------------------------------------------------------------------------------------

        financialContract.setIdFinancialContract(resultSet.getString("idFinancialContract"));

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        financialContract.setIndependentInd(act.isIndependentInd());
        financialContract.setInterruptibleInd(act.isInterruptibleInd());
        financialContract.setNegationInd(act.isNegationInd());
        financialContract.setEffectiveTime(act.getEffectiveTime());
        financialContract.setActivityTime(act.getActivityTime());
        financialContract.setAvailabilityTime(act.getAvailabilityTime());
        financialContract.setRepeatNumber(act.getRepeatNumber());
        financialContract.setDerivationExpr(act.getDerivationExpr());
        financialContract.setId(act.getId());
        financialContract.setText(act.getText());
        financialContract.setTitle(act.getTitle());

        return financialContract;
    }
}
