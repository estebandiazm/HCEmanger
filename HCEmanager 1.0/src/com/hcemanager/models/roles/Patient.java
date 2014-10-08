package com.hcemanager.models.roles;

import com.hcemanager.models.codes.Code;

/**
 * A Role of a LivingSubjectDAO (player) as a recipient of health care
 * services from a healthcare provider (scoper).
 * @author daniel.
 */
public class Patient extends Role {

    private Code veryImportantPersonCode;
    private String idPatient;

    public Patient() {
    }

    public Code getVeryImportantPersonCode() {
        return veryImportantPersonCode;
    }

    public void setVeryImportantPersonCode(Code veryImportantPersonCode) {
        this.veryImportantPersonCode = veryImportantPersonCode;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }
}
