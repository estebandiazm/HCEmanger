package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

/**
 * Definition of InvoiceElement:
 *
 * An Act representing a statement and justification of an "amount owed".
 *
 * Discussion: This represents the 'justification' portion of an invoice. It is frequently combined with a financial
 * transaction representing the amount requested to be paid, agreed to be paid, or actually paid.
 *
 * A recursive relationship can be used to break a single InvoiceElement into constituent elements.
 * In definition mood, it represents "possible" justification for future billing. In request mood, it is a request to
 * determine the amount owed. In event mood, this class represents the determination of a specific amount owed by a
 * particular Entity.
 *
 * @author daniel.
 */
public class InvoiceElement extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private Code modifierCode;
    private double factorNumber;
    private double pointsNumber;
    private String unitQuantity;
    private String unitPriceAmt;
    private String netAmt;
    private String idInvoiceElement;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default cosntructor
     */
    public InvoiceElement() {
    }

    public Code getModifierCode() {
        return modifierCode;
    }

    public void setModifierCode(Code modifierCode) {
        this.modifierCode = modifierCode;
    }

    public double getFactorNumber() {
        return factorNumber;
    }

    public void setFactorNumber(double factorNumber) {
        this.factorNumber = factorNumber;
    }

    public double getPointsNumber() {
        return pointsNumber;
    }

    public void setPointsNumber(double pointsNumber) {
        this.pointsNumber = pointsNumber;
    }

    public String getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(String unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public String getUnitPriceAmt() {
        return unitPriceAmt;
    }

    public void setUnitPriceAmt(String unitPriceAmt) {
        this.unitPriceAmt = unitPriceAmt;
    }

    public String getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(String netAmt) {
        this.netAmt = netAmt;
    }

    public String getIdInvoiceElement() {
        return idInvoiceElement;
    }

    public void setIdInvoiceElement(String idInvoiceElement) {
        this.idInvoiceElement = idInvoiceElement;
    }
}
