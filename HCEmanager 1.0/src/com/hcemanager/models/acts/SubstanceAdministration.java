package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

/**
 * The act of introducing or otherwise applying a substance to the subject.
 *
 * Discussion: The effect of the substance is typically established on a biochemical basis, however, that is not a
 * requirement. For example, radiotherapy can largely be described in the same way, especially if it is a systemic
 * therapy such as radio-iodine. This class also includes the application of chemical treatments to an area.
 *
 * Examples: Chemotherapy protocol; Drug prescription; Vaccination record
 *
 * @author daniel.
 */
public class SubstanceAdministration extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private Code routeCode;
    private Code approachSiteCode;
    private Code administrationUnitCode;
    private String doseQuantity;
    private String rateQuantity;
    private String doseCheckQuantity;
    private String maxDoseQuantity;
    private String idSubstanceAdministration;


    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public SubstanceAdministration() {
    }

    public Code getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(Code routeCode) {
        this.routeCode = routeCode;
    }

    public Code getApproachSiteCode() {
        return approachSiteCode;
    }

    public void setApproachSiteCode(Code approachSiteCode) {
        this.approachSiteCode = approachSiteCode;
    }

    public Code getAdministrationUnitCode() {
        return administrationUnitCode;
    }

    public void setAdministrationUnitCode(Code administrationUnitCode) {
        this.administrationUnitCode = administrationUnitCode;
    }

    public String getDoseQuantity() {
        return doseQuantity;
    }

    public void setDoseQuantity(String doseQuantity) {
        this.doseQuantity = doseQuantity;
    }

    public String getRateQuantity() {
        return rateQuantity;
    }

    public void setRateQuantity(String rateQuantity) {
        this.rateQuantity = rateQuantity;
    }

    public String getDoseCheckQuantity() {
        return doseCheckQuantity;
    }

    public void setDoseCheckQuantity(String doseCheckQuantity) {
        this.doseCheckQuantity = doseCheckQuantity;
    }

    public String getMaxDoseQuantity() {
        return maxDoseQuantity;
    }

    public void setMaxDoseQuantity(String maxDoseQuantity) {
        this.maxDoseQuantity = maxDoseQuantity;
    }

    public String getIdSubstanceAdministration() {
        return idSubstanceAdministration;
    }

    public void setIdSubstanceAdministration(String idSubstanceAdministration) {
        this.idSubstanceAdministration = idSubstanceAdministration;
    }
}
