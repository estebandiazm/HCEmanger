package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

/**
 * An Act representing a category of financial transactions that are tracked and reported together with a single balance.
 *
 * Discussion: This can be used to represent the accumulated total of billable amounts for goods or services received,
 * payments made for goods or services, and debit and credit accounts between which financial transactions flow.
 *
 * Examples: Patient accounts; Encounter accounts; Cost centers; Accounts receivable
 *
 * @author daniel.
 */
public class Account extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private String balanceAmt;
    private Code currencyCode;
    private String interestRateQuantity;
    private String allowedBalanceQuantity;
    private String idAccount;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public Account() {
    }

    public String getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(String balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public Code getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Code currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getInterestRateQuantity() {
        return interestRateQuantity;
    }

    public void setInterestRateQuantity(String interestRateQuantity) {
        this.interestRateQuantity = interestRateQuantity;
    }

    public String getAllowedBalanceQuantity() {
        return allowedBalanceQuantity;
    }

    public void setAllowedBalanceQuantity(String allowedBalanceQuantity) {
        this.allowedBalanceQuantity = allowedBalanceQuantity;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }
}
