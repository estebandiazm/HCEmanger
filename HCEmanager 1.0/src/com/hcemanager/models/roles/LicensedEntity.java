package com.hcemanager.models.roles;

/**
 * A Role of an Entity (player) that is accredited with licenses or qualifications (diplomas) certifying that this
 * Entity may properly perform specific functions.
 *
 * Examples:
 * 1.) A paramedical training diploma
 * 2.) The certification of equipment
 * 3.) A license to a PersonDAO or Organization to provide health services.
 *
 * Constraints: The scoper is the Organization that issues the credential.
 *
 * @author daniel.
 */
public class LicensedEntity extends Role {

    private String recertificationTime;
    private String idLicensedEntity;


    public LicensedEntity() {
    }

    public String getRecertificationTime() {
        return recertificationTime;
    }

    public void setRecertificationTime(String recertificationTime) {
        this.recertificationTime = recertificationTime;
    }

    public String getIdLicensedEntity() {
        return idLicensedEntity;
    }

    public void setIdLicensedEntity(String idLicensedEntity) {
        this.idLicensedEntity = idLicensedEntity;
    }
}