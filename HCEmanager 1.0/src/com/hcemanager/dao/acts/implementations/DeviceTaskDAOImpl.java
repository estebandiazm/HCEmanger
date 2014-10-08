package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.interfaces.DeviceTaskDAO;
import com.hcemanager.dao.acts.rowMappers.DeviceTaskRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.DeviceTask;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class DeviceTaskDAOImpl extends JdbcDaoSupport implements DeviceTaskDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into DeviceTask(idDeviceTask,parameterValue,Act_idAct values (?,?,?))";
    public static final String UPDATE = "update DeviceTask set parameterValue=? where idDeviceTask=? ";
    public static final String DELETE = "delete from DeviceTask where idDeviceTask=?";
    public static final String SELECT_DEVICE_TASK = "select * from DeviceTask inner join Act where Act_idAct= idAct and idDeviceTask=?";
    public static final String SELECT_DEVICE_TASKS = "select * from DeviceTask inner join Act where Act_idAct = idAct";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a deviceTask in the db
     * @param deviceTask an DeviceTask object
     * @throws DeviceTaskExistsException
     */
    @Override
    public void insertDeviceTask(DeviceTask deviceTask) throws DeviceTaskExistsException, ActExistsException, CodeExistsException {
        try {

            SpringServices.getActDAO().insertAct(deviceTask);

            getJdbcTemplate().update(INSERT,
                    deviceTask.getIdDeviceTask(),
                    deviceTask.getParameterValue(),
                    deviceTask.getId());

        }catch (DataIntegrityViolationException e){
            throw new DeviceTaskExistsException(e);
        }
    }

    /**
     * Update a device task by id
     * @param deviceTask an DeviceTask object
     * @throws DeviceTaskNotExistsException
     * @throws DeviceTaskExistsException
     */
    @Override
    public void updateDeviceTask(DeviceTask deviceTask) throws DeviceTaskNotExistsException, DeviceTaskExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
       if (isDifferent(deviceTask)){
           try {

               SpringServices.getActDAO().updateAct(deviceTask);

               int rows = getJdbcTemplate().update(UPDATE,
                       deviceTask.getParameterValue(),
                       deviceTask.getIdDeviceTask());

               if (rows==0){
                   throw new DeviceTaskNotExistsException();
               }

           }catch (DataIntegrityViolationException e){
               throw new DeviceTaskExistsException(e);
           }
       }else {
           SpringServices.getActDAO().updateAct(deviceTask);
       }
    }

    /**
     * Delete a device task by id
     * @param id the device task id
     * @throws DeviceTaskNotExistsException
     * @throws DeviceTaskCannotBeDeleted
     */
    @Override
    public void deleteDeviceTask(String id) throws DeviceTaskNotExistsException, DeviceTaskCannotBeDeleted, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {

        try {

            DeviceTask deviceTask= getDeviceTask(id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new DeviceTaskNotExistsException();
            }

            SpringServices.getActDAO().deleteAct(deviceTask.getId());

        }catch(DataIntegrityViolationException e){
            throw new DeviceTaskCannotBeDeleted(e);
        }
    }

    /**
     * Get a device task from the db
     * @param id the device task id
     * @return a device task
     * @throws DeviceTaskNotExistsException
     */
    @Override
    public DeviceTask getDeviceTask(String id) throws DeviceTaskNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            DeviceTask deviceTask = getJdbcTemplate().queryForObject(SELECT_DEVICE_TASK,new DeviceTaskRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(deviceTask,actDAO.getActCodes(deviceTask.getId()));
            return deviceTask;

        }catch (EmptyResultDataAccessException e){
            throw new DeviceTaskNotExistsException(e);
        }
    }

    /**
     * Get all deviceTasks from the db
     * @return a list with device tasks
     * @throws DeviceTaskNotExistsException
     */
    @Override
    public List<DeviceTask> getDevicesTasks() throws DeviceTaskNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<DeviceTask> deviceTasks = getJdbcTemplate().query(SELECT_DEVICE_TASKS, new DeviceTaskRowMapper());

            for (DeviceTask deviceTask:deviceTasks){
                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(deviceTask,actDAO.getActCodes(deviceTask.getId()));
            }
            return deviceTasks;

        }catch (EmptyResultDataAccessException e){
            throw new DeviceTaskNotExistsException(e);
        }
    }

    /**
     *
     * @param deviceTask a DeviceTask object
     * @return true if the device task is different
     * @throws CodeIsNotValidException
     * @throws DeviceTaskNotExistsException
     * @throws CodeNotExistsException
     */
    private boolean isDifferent(DeviceTask deviceTask) throws CodeIsNotValidException, DeviceTaskNotExistsException, CodeNotExistsException {
        DeviceTask oldDeviceTask = getDeviceTask(deviceTask.getIdDeviceTask());
        int differentAttrs=0;

        if (!oldDeviceTask.getParameterValue().equalsIgnoreCase(deviceTask.getParameterValue()))
            differentAttrs++;

        return differentAttrs != 0;
    }
}
