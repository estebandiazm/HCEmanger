package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.WorkingList;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class WorkingListRowMapper implements ParameterizedRowMapper<WorkingList> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * Make a WorkingList mapper
     * @param resultSet argument
     * @param i index
     * @return a WorkingList object
     * @throws java.sql.SQLException
     */
    @Override
    public WorkingList mapRow(ResultSet resultSet, int i) throws SQLException {

        WorkingList workingList = new WorkingList();
        Act act = actRowMapper.mapRow(resultSet,i);

        // -------------------------------------------------------------------------------------------------------------
        // WorkingList arguments
        // -------------------------------------------------------------------------------------------------------------

        workingList.setIdWorkingList(resultSet.getString("idWorkingList"));

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        workingList.setIndependentInd(act.isIndependentInd());
        workingList.setInterruptibleInd(act.isInterruptibleInd());
        workingList.setNegationInd(act.isNegationInd());
        workingList.setEffectiveTime(act.getEffectiveTime());
        workingList.setActivityTime(act.getActivityTime());
        workingList.setAvailabilityTime(act.getAvailabilityTime());
        workingList.setRepeatNumber(act.getRepeatNumber());
        workingList.setDerivationExpr(act.getDerivationExpr());
        workingList.setId(act.getId());
        workingList.setText(act.getText());
        workingList.setTitle(act.getTitle());

        return workingList;
    }
}
