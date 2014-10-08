package com.hcemanager.models.roles;

import com.hcemanager.models.codes.Code;

/**
 * A role played by a person who is associated with an organization (the employer, scoper)
 * to receive wages or salary.
 *
 * Discussion: The purpose of the role is to identify the type of relationship
 * the employee has to the employer rather than the nature of the work actually
 * performed (contrast with AssignedEntity).
 * @author Daniel Bellon & Juan Diaz
 */
public class Employee extends Role {

    private Code jobCode;
    private Code jobClassCode;
    private Code occupationCode;
    private Code salaryTypeCode;
    private String salaryQuantity;
    private String jobTitleName;
    private String hazardExposureText;
    private String protectiveEquipmentText;
    private String idEmployee;

    public Code getJobCode() {
        return jobCode;
    }

    public void setJobCode(Code jobCode) {
        this.jobCode = jobCode;
    }

    public Code getJobClassCode() {
        return jobClassCode;
    }

    public void setJobClassCode(Code jobClassCode) {
        this.jobClassCode = jobClassCode;
    }

    public Code getOccupationCode() {
        return occupationCode;
    }

    public void setOccupationCode(Code occupationCode) {
        this.occupationCode = occupationCode;
    }

    public Code getSalaryTypeCode() {
        return salaryTypeCode;
    }

    public void setSalaryTypeCode(Code salaryTypeCode) {
        this.salaryTypeCode = salaryTypeCode;
    }

    public String getSalaryQuantity() {
        return salaryQuantity;
    }

    public void setSalaryQuantity(String salaryQuantity) {
        this.salaryQuantity = salaryQuantity;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getHazardExposureText() {
        return hazardExposureText;
    }

    public void setHazardExposureText(String hazardExposureText) {
        this.hazardExposureText = hazardExposureText;
    }

    public String getProtectiveEquipmentText() {
        return protectiveEquipmentText;
    }

    public void setProtectiveEquipmentText(String protectiveEquipmentText) {
        this.protectiveEquipmentText = protectiveEquipmentText;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }
}
