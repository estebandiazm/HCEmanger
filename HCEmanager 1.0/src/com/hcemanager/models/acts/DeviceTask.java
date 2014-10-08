package com.hcemanager.models.acts;

/**
 * An activity of an automated system.
 *
 * Discussion:Such activities are invoked either by an outside command or are scheduled and executed spontaneously
 * by the device (e.g., regular calibration or flushing). The command to execute the task has moodCode <= ORD; an executed
 * task (including a task in progress) has moodCode <= EVN, an automatic task on the schedule has moodCode <= APT.
 *
 * @author daniel.
 */
public class DeviceTask extends Act {

    // -----------------------------------------------------------------------------------------------------------------
    // Arguments
    // -----------------------------------------------------------------------------------------------------------------

    private String parameterValue;
    private String idDeviceTask;

    // -----------------------------------------------------------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public DeviceTask() {
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getIdDeviceTask() {
        return idDeviceTask;
    }

    public void setIdDeviceTask(String idDeviceTask) {
        this.idDeviceTask = idDeviceTask;
    }
}
