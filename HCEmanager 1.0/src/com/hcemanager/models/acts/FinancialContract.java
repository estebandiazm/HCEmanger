package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

/**
 * A contract whose valueObservation is measured in monetary terms.
 *
 * Examples: Insurance; Purchase agreement
 *
 * @author daniel.
 */
public class FinancialContract extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private Code paymentTermsCode;
    private String idFinancialContract;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public FinancialContract() {
    }

    public Code getPaymentTermsCode() {
        return paymentTermsCode;
    }

    public void setPaymentTermsCode(Code paymentTermsCode) {
        this.paymentTermsCode = paymentTermsCode;
    }

    public String getIdFinancialContract() {
        return idFinancialContract;
    }

    public void setIdFinancialContract(String idFinancialContract) {
        this.idFinancialContract = idFinancialContract;
    }
}
