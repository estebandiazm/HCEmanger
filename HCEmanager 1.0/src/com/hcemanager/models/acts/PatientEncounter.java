package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

/**
 * An interaction between a patient and care provider(s) for the purpose of providing healthcare-related service(s).
 * Healthcare services include health assessment.
 *
 * Examples: outpatient visit to multiple departments, home health support (including physical therapy), inpatient
 * hospital stay, emergency room visit, field visit (e.g., traffic accident), office visit, occupational therapy,
 * telephone call.
 *
 * @author daniel.
 */
public class PatientEncounter extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private Code administrationReferralSourceCode;
    private Code dischargeDispositionCode;
    private Code specialCourtesiesCode;
    private Code specialArrangementCode;
    private boolean preAdmitTestInd;
    private String lengthOfStayQuantity;
    private String idPatientEncounter;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public PatientEncounter() {
    }

    public Code getAdministrationReferralSourceCode() {
        return administrationReferralSourceCode;
    }

    public void setAdministrationReferralSourceCode(Code administrationReferralSourceCode) {
        this.administrationReferralSourceCode = administrationReferralSourceCode;
    }

    public Code getDischargeDispositionCode() {
        return dischargeDispositionCode;
    }

    public void setDischargeDispositionCode(Code dischargeDispositionCode) {
        this.dischargeDispositionCode = dischargeDispositionCode;
    }

    public Code getSpecialCourtesiesCode() {
        return specialCourtesiesCode;
    }

    public void setSpecialCourtesiesCode(Code specialCourtesiesCode) {
        this.specialCourtesiesCode = specialCourtesiesCode;
    }

    public Code getSpecialArrangementCode() {
        return specialArrangementCode;
    }

    public void setSpecialArrangementCode(Code specialArrangementCode) {
        this.specialArrangementCode = specialArrangementCode;
    }

    public boolean isPreAdmitTestInd() {
        return preAdmitTestInd;
    }

    public void setPreAdmitTestInd(boolean preAdmitTestInd) {
        this.preAdmitTestInd = preAdmitTestInd;
    }

    public String getLengthOfStayQuantity() {
        return lengthOfStayQuantity;
    }

    public void setLengthOfStayQuantity(String lengthOfStayQuantity) {
        this.lengthOfStayQuantity = lengthOfStayQuantity;
    }

    public String getIdPatientEncounter() {
        return idPatientEncounter;
    }

    public void setIdPatientEncounter(String idPatientEncounter) {
        this.idPatientEncounter = idPatientEncounter;
    }
}
