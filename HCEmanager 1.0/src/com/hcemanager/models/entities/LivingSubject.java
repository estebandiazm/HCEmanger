package com.hcemanager.models.entities;

import com.hcemanager.models.codes.Code;

/**
 * A subtype of Entity representing an organism or complex animal, alive or not.
 *
 * Examples: A person, dog, microorganism or a plant of any taxonomic group.
 *
 * Constraints: Instances of this class encompass mammals, birds, fishes, bacteria, parasites, fungi and viruses.
 * PersonDAO is a specialization of this class.
 *
 * Rationale: This class contains "static" or "administrative" attributes of interest to medicine that differentiate
 * living organisms from other Entities.
 *
 * @author daniel.
 */
public class LivingSubject extends Entity {

    protected boolean deceasedInd;
    protected boolean multipleBirthInd;
    protected boolean organDonorInd;
    protected Code administrativeGenderCode;
    protected String birthTime;
    protected String deceasedTime;
    protected String idLivingSubject;
    protected int multipleBirthOrderNumber;

    public LivingSubject() {
    }

    public boolean isDeceasedInd() {
        return deceasedInd;
    }

    public void setDeceasedInd(boolean deceasedInd) {
        this.deceasedInd = deceasedInd;
    }

    public boolean isMultipleBirthInd() {
        return multipleBirthInd;
    }

    public void setMultipleBirthInd(boolean multipleBirthInd) {
        this.multipleBirthInd = multipleBirthInd;
    }

    public boolean isOrganDonorInd() {
        return organDonorInd;
    }

    public void setOrganDonorInd(boolean organDonorInd) {
        this.organDonorInd = organDonorInd;
    }

    public Code getAdministrativeGenderCode() {
        return administrativeGenderCode;
    }

    public void setAdministrativeGenderCode(Code administrativeGenderCode) {
        this.administrativeGenderCode = administrativeGenderCode;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public String getDeceasedTime() {
        return deceasedTime;
    }

    public void setDeceasedTime(String deceasedTime) {
        this.deceasedTime = deceasedTime;
    }

    public int getMultipleBirthOrderNumber() {
        return multipleBirthOrderNumber;
    }

    public void setMultipleBirthOrderNumber(int multipleBirthOrderNumber) {
        this.multipleBirthOrderNumber = multipleBirthOrderNumber;
    }

    public String getIdLivingSubject() {
        return idLivingSubject;
    }

    public void setIdLivingSubject(String id) {
        this.idLivingSubject = id;
    }
}




