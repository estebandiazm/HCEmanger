package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.Act;
import com.hcemanager.models.acts.DeviceTask;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, esteban.
 */
public class DeviceTaskRowMapper implements ParameterizedRowMapper<DeviceTask> {

    ActRowMapper actRowMapper = new ActRowMapper();

    /**
     * Make a deviceTask mapper
     * @param resultSet argument
     * @param i index
     * @return deviceTask
     * @throws java.sql.SQLException
     */
    @Override
    public DeviceTask mapRow(ResultSet resultSet, int i) throws SQLException {

        Act act = actRowMapper.mapRow(resultSet,i);
        DeviceTask deviceTask = new DeviceTask();

        // -------------------------------------------------------------------------------------------------------------
        // DeviceTask arguments
        // -------------------------------------------------------------------------------------------------------------

        deviceTask.setParameterValue(resultSet.getString("parameterValue"));
        deviceTask.setIdDeviceTask(resultSet.getString("idDeviceTask"));

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        deviceTask.setIndependentInd(act.isIndependentInd());
        deviceTask.setInterruptibleInd(act.isInterruptibleInd());
        deviceTask.setNegationInd(act.isNegationInd());
        deviceTask.setEffectiveTime(act.getEffectiveTime());
        deviceTask.setActivityTime(act.getActivityTime());
        deviceTask.setAvailabilityTime(act.getAvailabilityTime());
        deviceTask.setRepeatNumber(act.getRepeatNumber());
        deviceTask.setDerivationExpr(act.getDerivationExpr());
        deviceTask.setId(act.getId());
        deviceTask.setText(act.getText());
        deviceTask.setTitle(act.getTitle());
        return deviceTask;
    }
}
