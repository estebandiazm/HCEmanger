package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Account;
import com.hcemanager.models.acts.Act;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class AccountRowMapper implements ParameterizedRowMapper<Account> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * This method make an Account mapper
     * @param resultSet argument
     * @param i index
     * @return account
     * @throws SQLException
     */
    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {

        Act act=actRowMapper.mapRow(resultSet,i);
        Account account=new Account();

        // -------------------------------------------------------------------------------------------------------------
        // Account arguments
        // -------------------------------------------------------------------------------------------------------------

        account.setAllowedBalanceQuantity(resultSet.getString("allowedBalanceQuantity"));
        account.setBalanceAmt(resultSet.getString("balanceAmt"));
        account.setIdAccount(resultSet.getString("idAccount"));
        account.setInterestRateQuantity(resultSet.getString("interestRateQuantity"));

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        account.setIndependentInd(act.isIndependentInd());
        account.setInterruptibleInd(act.isInterruptibleInd());
        account.setNegationInd(act.isNegationInd());
        account.setEffectiveTime(act.getEffectiveTime());
        account.setActivityTime(act.getActivityTime());
        account.setAvailabilityTime(act.getAvailabilityTime());
        account.setRepeatNumber(act.getRepeatNumber());
        account.setDerivationExpr(act.getDerivationExpr());
        account.setId(act.getId());
        account.setText(act.getText());
        account.setTitle(act.getTitle());

        return account;
    }
}
