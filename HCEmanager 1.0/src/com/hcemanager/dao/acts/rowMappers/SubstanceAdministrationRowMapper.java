package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.SubstanceAdministration;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class SubstanceAdministrationRowMapper implements ParameterizedRowMapper<SubstanceAdministration> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * Make a SubstanceAdministration mapper
     * @param resultSet argument
     * @param i index
     * @return a SubstanceAdministration object
     * @throws java.sql.SQLException
     */
    @Override
    public SubstanceAdministration mapRow(ResultSet resultSet, int i) throws SQLException {

        SubstanceAdministration substanceAdministration = new SubstanceAdministration();
        Act act = actRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // SubstanceAdministration arguments
        // -------------------------------------------------------------------------------------------------------------

        substanceAdministration.setIdSubstanceAdministration(resultSet.getString("idSubstanceAdministration"));
        substanceAdministration.setMaxDoseQuantity(resultSet.getString("maxDoseQuantity"));
        substanceAdministration.setDoseCheckQuantity(resultSet.getString("doseCheckQuantity"));
        substanceAdministration.setRateQuantity(resultSet.getString("rateQuantity"));
        substanceAdministration.setDoseQuantity(resultSet.getString("doseQuantity"));

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        substanceAdministration.setIndependentInd(act.isIndependentInd());
        substanceAdministration.setInterruptibleInd(act.isInterruptibleInd());
        substanceAdministration.setNegationInd(act.isNegationInd());
        substanceAdministration.setEffectiveTime(act.getEffectiveTime());
        substanceAdministration.setActivityTime(act.getActivityTime());
        substanceAdministration.setAvailabilityTime(act.getAvailabilityTime());
        substanceAdministration.setRepeatNumber(act.getRepeatNumber());
        substanceAdministration.setDerivationExpr(act.getDerivationExpr());
        substanceAdministration.setId(act.getId());
        substanceAdministration.setText(act.getText());
        substanceAdministration.setTitle(act.getTitle());

        return substanceAdministration;
    }
}
