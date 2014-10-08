package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Diet;
import com.hcemanager.models.acts.Supply;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class DietRowMapper implements ParameterizedRowMapper<Diet> {

    SupplyRowMapper supplyRowMapper = new SupplyRowMapper();

    /**
     * Make a diet mapper
     * @param resultSet argument
     * @param i index
     * @return diet
     * @throws java.sql.SQLException
     */
    @Override
    public Diet mapRow(ResultSet resultSet, int i) throws SQLException {

        Diet diet = new Diet();
        Supply supply = supplyRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // Diet arguments
        // -------------------------------------------------------------------------------------------------------------

        diet.setEnergyQuantity(resultSet.getString("energyQuantity"));
        diet.setCarbohydrateQuantity(resultSet.getString("carbohydrateQuantity"));
        diet.setIdDiet(resultSet.getString("idDiet"));

        // -------------------------------------------------------------------------------------------------------------
        // Supply arguments
        // -------------------------------------------------------------------------------------------------------------

        diet.setIdSupply(supply.getIdSupply());
        diet.setExpectedUseTime(supply.getExpectedUseTime());
        diet.setQuantity(supply.getQuantity());

        // -------------------------------------------------------------------------------------------------------------
        // Diet arguments
        // -------------------------------------------------------------------------------------------------------------

        diet.setIndependentInd(supply.isIndependentInd());
        diet.setInterruptibleInd(supply.isInterruptibleInd());
        diet.setNegationInd(supply.isNegationInd());
        diet.setEffectiveTime(supply.getEffectiveTime());
        diet.setActivityTime(supply.getActivityTime());
        diet.setAvailabilityTime(supply.getAvailabilityTime());
        diet.setRepeatNumber(supply.getRepeatNumber());
        diet.setDerivationExpr(supply.getDerivationExpr());
        diet.setId(supply.getId());
        diet.setText(supply.getText());
        diet.setTitle(supply.getTitle());

        return diet;
    }
}
