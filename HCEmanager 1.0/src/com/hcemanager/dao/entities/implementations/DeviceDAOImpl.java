package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.entities.interfaces.DeviceDAO;
import com.hcemanager.dao.entities.rowMappers.DeviceRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.Device;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @author daniel, juan.
 */
public class DeviceDAOImpl extends JdbcDaoSupport implements DeviceDAO {

    public static final String INSERT = "insert into Device (idDevice,manufacturerModelName,softwareName,lastCalibrationTime, ManufacturedMaterial_idManufacturedMaterial) values (?,?,?,?,?)";
    public static final String UPDATE = "update Device set manufacturerModelName=?, softwareName=?,lastCalibrationTime=? where idDevice=?";
    public static final String DELETE = "delete from Device where idDevice=?";
    public static final String SELECT_DEVICE = "Select * from Device inner join ManufacturedMaterial inner join Material inner join Entity where ManufacturedMaterial_idManufacturedMaterial=idManufacturedMaterial and Material_idMaterial=idMaterial and Entity_idEntity=idEntity and idDevice=?";
    public static final String SELECT_DEVICES = "Select * from Device inner join ManufacturedMaterial inner join Material inner join Entity where ManufacturedMaterial_idManufacturedMaterial=idManufacturedMaterial and Material_idMaterial=idMaterial and Entity_idEntity=idEntity";
    public static final String INSERT_CODE = "insert into Device_has_Codes(Codes_idCodes, Device_idDevice,type) values (?,?,?)";
    public static final String UPDATE_CODE = "update Device_has_Codes set Codes_idCodes=? where Device_idDevice=? and type=?;";
    public static final String DELETE_CODE = "delete from Device_has_codes where Device_idDevice=?";
    public static final String SELECT_CODES = "select * from Device_has_Codes inner join Codes where Codes_idCodes=idCodes and Device_idDevice=?";

    //TODO: revisar selects.

    /**
     * Insert Device method.
     * @param device
     * @throws DeviceExistsException
     */
    @Override
    public void insertDevice(Device device) throws DeviceExistsException, ManufacturedMaterialExistsException, MaterialExistsException, EntityExistsException {
        try {

            SpringServices.getManufacturedMaterialDAO().insertManufacturedMaterial(device);

            getJdbcTemplate().update(INSERT,
                    device.getIdDevice(),
                    device.getManufacturerModelName(),
                    device.getSoftwareName(),
                    device.getLastCalibrationTime(),
                    device.getIdManufacturedMaterial());

            //Codes

            getJdbcTemplate().update(INSERT_CODE,
                    device.getLocalRemoteControlStateCode().getId(),
                    device.getIdDevice(),
                    device.getLocalRemoteControlStateCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    device.getAlertLevelCode().getId(),
                    device.getIdDevice(),
                    device.getAlertLevelCode().getType());

        }catch (DataIntegrityViolationException e){
            throw new DeviceExistsException(e);
        }

    }

    /**
     * Update Device method
     * @param device
     * @throws DeviceExistsException
     * @throws DeviceNotExistsException
     */
    @Override
    public void updateDevice(Device device) throws DeviceExistsException, DeviceNotExistsException, ManufacturedMaterialExistsException, ManufacturedMaterialNotExistsException, MaterialExistsException, MaterialNotExistsException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException {

        if (isDifferent(device)){

            try {

                SpringServices.getManufacturedMaterialDAO().updateManufacturedMaterial(device);

                int rows = getJdbcTemplate().update(UPDATE,
                        device.getManufacturerModelName(),
                        device.getSoftwareName(),
                        device.getLastCalibrationTime(),
                        device.getIdDevice());

                if (rows==0){
                    throw new DeviceNotExistsException();
                }
                updateCodes(device);
            }catch (DataIntegrityViolationException e){
                throw new DeviceExistsException(e);
            }
        } else {
            SpringServices.getManufacturedMaterialDAO().updateManufacturedMaterial(device);
            updateCodes(device);
        }

    }

    /**
     * Delete Device method.
     * @param id
     * @throws com.hcemanager.exceptions.entities.DeviceNotExistsException
     * @throws com.hcemanager.exceptions.entities.DeviceCannotBeDeletedException
     */
    @Override
    public void deleteDevice(String id) throws DeviceNotExistsException, DeviceCannotBeDeletedException, ManufacturedMaterialNotExistsException, ManufacturedMaterialCannotBeDeletedException, MaterialCannotBeDeletedException, MaterialNotExistsException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODE, id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new DeviceNotExistsException();
            }

            Device device = getDevice(id);
            SpringServices.getManufacturedMaterialDAO().deleteManufacturedMaterial(device.getIdManufacturedMaterial());


        }catch (DataIntegrityViolationException e){
            throw new DeviceCannotBeDeletedException(e);
        }

    }

    /**
     * Get one Device object.
     * @param id
     * @return Device
     * @throws DeviceNotExistsException
     */
    @Override
    public Device getDevice(String id) throws DeviceNotExistsException, CodeNotExistsException {
        try{

            Device device = getJdbcTemplate().queryForObject(SELECT_DEVICE, new DeviceRowMapper(),id);
            setDeviceCodes(device, getDeviceCodes(id));

            return device;

        }catch (EmptyResultDataAccessException e){
            throw new DeviceNotExistsException(e);
        }
    }

    /**
     * Get all Device Objects
     * @return List<Device>
     * @throws DeviceNotExistsException
     */
    @Override
    public List<Device> getDevices() throws DeviceNotExistsException, CodeNotExistsException {
        try {
            List<Device> devices = getJdbcTemplate().query(SELECT_DEVICES, new DeviceRowMapper());

            for (Device device:devices){
                setDeviceCodes(device, getDeviceCodes(device.getIdDevice()));
                devices.set(devices.indexOf(device),device);
            }

            return devices;
        }catch (EmptyResultDataAccessException e){
            throw new DeviceNotExistsException(e);
        }
    }

    /**
     * Get Codes of an Device by id
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    private List<Code> getDeviceCodes(String id) throws CodeNotExistsException {

        try{

            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;

        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set code to Device
     * @param device
     * @param codes
     * @return
     */
    private void setDeviceCodes(Device device, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getMaterialDAO().setMaterialCodes(device,
                SpringServices.getMaterialDAO().getMaterialCodes(device.getIdMaterial()));

        for (Code code:codes){

            String typeCode = code.getType();

            if (typeCode.equals("localRemoteControlStateCode")){
                device.setLocalRemoteControlStateCode(code);
            }else if (typeCode.equals("alertLevelCode")){
                device.setAlertLevelCode(code);
            }

        }
    }

    private boolean isDifferent(Device device) throws CodeNotExistsException, DeviceNotExistsException {
        Device oldDevice = getDevice(device.getIdDevice());

        int attrDifferent=0;

        if (!device.getLastCalibrationTime().equals(oldDevice.getLastCalibrationTime())){
            attrDifferent++;
        }
        if (!device.getManufacturerModelName().equals(oldDevice.getManufacturerModelName())){
            attrDifferent++;
        }
        if (!device.getSoftwareName().equals(oldDevice.getSoftwareName())){
            attrDifferent++;
        }

        return attrDifferent!=0;

    }

    private void updateCodes(Device device) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getDeviceCodes(device.getIdDevice());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase(device.getLocalRemoteControlStateCode().getType())){
                    if (!code.getId().equals(device.getLocalRemoteControlStateCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                device.getLocalRemoteControlStateCode().getId(),
                                device.getIdDevice(),
                                device.getLocalRemoteControlStateCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(device.getAlertLevelCode().getType())){
                    if (!code.getId().equals(device.getAlertLevelCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                device.getAlertLevelCode().getId(),
                                device.getIdDevice(),
                                device.getAlertLevelCode().getType());
                    }
                }

            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }

}
