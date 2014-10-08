package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.DeviceTask;

import java.util.List;

/**
 * This interface define the DeviceDAO task DAO methods
 * @author daniel.
 */
public interface DeviceTaskDAO {

    public void insertDeviceTask(DeviceTask deviceTask) throws DeviceTaskExistsException, ActExistsException, CodeExistsException;
    public void updateDeviceTask(DeviceTask deviceTask) throws DeviceTaskNotExistsException, DeviceTaskExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteDeviceTask(String id) throws DeviceTaskNotExistsException, DeviceTaskCannotBeDeleted, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public DeviceTask getDeviceTask(String id) throws DeviceTaskNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<DeviceTask> getDevicesTasks() throws DeviceTaskNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
