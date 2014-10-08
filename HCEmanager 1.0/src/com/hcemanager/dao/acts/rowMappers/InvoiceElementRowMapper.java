package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.InvoiceElement;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class InvoiceElementRowMapper implements ParameterizedRowMapper<InvoiceElement> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * Make an invoice element mapper
     * @param resultSet argument
     * @param i index
     * @return invoice element
     * @throws java.sql.SQLException
     */
    @Override
    public InvoiceElement mapRow(ResultSet resultSet, int i) throws SQLException {

        InvoiceElement invoiceElement = new InvoiceElement();
        Act act = actRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // InvoiceElement arguments
        // -------------------------------------------------------------------------------------------------------------

        invoiceElement.setFactorNumber(resultSet.getDouble("factorNumber"));
        invoiceElement.setPointsNumber(resultSet.getDouble("pointsNumber"));
        invoiceElement.setUnitQuantity(resultSet.getString("unitQuantity"));
        invoiceElement.setUnitPriceAmt(resultSet.getString("unitPriceAmt"));
        invoiceElement.setNetAmt(resultSet.getString("netAmt"));
        invoiceElement.setIdInvoiceElement(resultSet.getString("idInvoiceElement"));

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        invoiceElement.setIndependentInd(act.isIndependentInd());
        invoiceElement.setInterruptibleInd(act.isInterruptibleInd());
        invoiceElement.setNegationInd(act.isNegationInd());
        invoiceElement.setEffectiveTime(act.getEffectiveTime());
        invoiceElement.setActivityTime(act.getActivityTime());
        invoiceElement.setAvailabilityTime(act.getAvailabilityTime());
        invoiceElement.setRepeatNumber(act.getRepeatNumber());
        invoiceElement.setDerivationExpr(act.getDerivationExpr());
        invoiceElement.setId(act.getId());
        invoiceElement.setText(act.getText());
        invoiceElement.setTitle(act.getTitle());

        return invoiceElement;
    }
}
