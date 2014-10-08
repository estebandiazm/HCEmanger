package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.FinancialTransaction;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class FinancialTransactionRowMapper implements ParameterizedRowMapper<FinancialTransaction> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * @param resultSet argument
     * @param i index
     * @return financial transaction
     * @throws java.sql.SQLException
     */
    @Override
    public FinancialTransaction mapRow(ResultSet resultSet, int i) throws SQLException {

        FinancialTransaction financialTransaction= new FinancialTransaction();
        Act act = actRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // FinancialTransaction arguments
        // -------------------------------------------------------------------------------------------------------------

        financialTransaction.setAmt(resultSet.getString("amt"));
        financialTransaction.setIdFinancialTransaction("idFinancialTransaction");
        financialTransaction.setCreditExchangeRateQuantity(resultSet.getDouble("creditExchangeRateQuantity"));
        financialTransaction.setDebitExchangeRateQuantity(resultSet.getDouble("debitExchangeRateQuantity"));

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        financialTransaction.setIndependentInd(act.isIndependentInd());
        financialTransaction.setInterruptibleInd(act.isInterruptibleInd());
        financialTransaction.setNegationInd(act.isNegationInd());
        financialTransaction.setEffectiveTime(act.getEffectiveTime());
        financialTransaction.setActivityTime(act.getActivityTime());
        financialTransaction.setAvailabilityTime(act.getAvailabilityTime());
        financialTransaction.setRepeatNumber(act.getRepeatNumber());
        financialTransaction.setDerivationExpr(act.getDerivationExpr());
        financialTransaction.setId(act.getId());
        financialTransaction.setText(act.getText());
        financialTransaction.setTitle(act.getTitle());

        return financialTransaction;
    }
}
