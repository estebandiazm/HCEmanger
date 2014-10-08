package com.hcemanager.models.acts;

/**
 * An Act representing the movement of a monetary amount between two accounts.
 *
 * Discussion: Financial transactions always occur between two accounts (debit and credit), but there may be
 * circumstances where one or both accounts are implied or inherited from the containing model.
 *In the "order" mood, this represents a request for a transaction to be initiated.
 *In the "event" mood, this represents the posting of a transaction to an account.
 *
 * Examples: Cost of a service; Charge for a service; Payment of an invoice
 *
 * @author daniel.
 */
public class FinancialTransaction extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private String amt;
    private String idFinancialTransaction;
    private double creditExchangeRateQuantity;
    private double debitExchangeRateQuantity;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public FinancialTransaction() {
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public double getCreditExchangeRateQuantity() {
        return creditExchangeRateQuantity;
    }

    public void setCreditExchangeRateQuantity(double creditExchangeRateQuantity) {
        this.creditExchangeRateQuantity = creditExchangeRateQuantity;
    }

    public double getDebitExchangeRateQuantity() {
        return debitExchangeRateQuantity;
    }

    public void setDebitExchangeRateQuantity(double debitExchangeRateQuantity) {
        this.debitExchangeRateQuantity = debitExchangeRateQuantity;
    }

    public String getIdFinancialTransaction() {
        return idFinancialTransaction;
    }

    public void setIdFinancialTransaction(String idFinancialTransaction) {
        this.idFinancialTransaction = idFinancialTransaction;
    }
}
