package com.hcemanager.models.entities;

import com.hcemanager.models.codes.Code;

/**
 * A subtype of LivingSubjectDAO that includes all living things except the species homo sapiens.
 *
 * Examples: Cattle, birds, bacteria , plants molds and fungi, etc.
 *
 * Rationale: Living organisms other than human beings may require additional characterizing information such as genetic
 * strain identification that cannot be conveyed in the Entity.code.
 *
 * @author daniel.
 */
public class NonPersonLivingSubject extends LivingSubject {

    private String idNonPersonLivingSubject;
    private String strainText;
    private Code genderStatusCode;

    public NonPersonLivingSubject() {
    }

    public String getStrainText() {
        return strainText;
    }

    public void setStrainText(String strainText) {
        this.strainText = strainText;
    }

    public Code getGenderStatusCode() {
        return genderStatusCode;
    }

    public void setGenderStatusCode(Code genderStatusCode) {
        this.genderStatusCode = genderStatusCode;
    }

    public String getIdNonPersonLivingSubject() {
        return idNonPersonLivingSubject;
    }

    public void setIdNonPersonLivingSubject(String id) {
        this.idNonPersonLivingSubject = id;
    }
}
