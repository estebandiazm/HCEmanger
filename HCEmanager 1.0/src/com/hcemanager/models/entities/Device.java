package com.hcemanager.models.entities;

import com.hcemanager.models.codes.Code;

/**
 * A subtype of ManufacturedMaterial used in an activity, without being substantially changed through that activity.
 * The kind of device is identified by the code attribute inherited from Entity.
 *
 * Usage: This includes durable (reusable) medical equipment as well as disposable equipment.
 *
 * @author daniel.
 */
public class Device extends ManufacturedMaterial {


    private Code localRemoteControlStateCode;
    private Code alertLevelCode;
    private String lastCalibrationTime;
    private String manufacturerModelName;
    private String softwareName;
    private String idDevice;


    public Device() {
    }

    public Code getLocalRemoteControlStateCode() {
        return localRemoteControlStateCode;
    }

    public void setLocalRemoteControlStateCode(Code localRemoteControlStateCode) {
        this.localRemoteControlStateCode = localRemoteControlStateCode;
    }

    public Code getAlertLevelCode() {
        return alertLevelCode;
    }

    public void setAlertLevelCode(Code alertLevelCode) {
        this.alertLevelCode = alertLevelCode;
    }

    public String  getLastCalibrationTime() {
        return lastCalibrationTime;
    }

    public void setLastCalibrationTime(String lastCalibrationTime) {
        this.lastCalibrationTime = lastCalibrationTime;
    }

    public String getManufacturerModelName() {
        return manufacturerModelName;
    }

    public void setManufacturerModelName(String manufacturerModelName) {
        this.manufacturerModelName = manufacturerModelName;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String id) {
        this.idDevice = id;
    }
}
