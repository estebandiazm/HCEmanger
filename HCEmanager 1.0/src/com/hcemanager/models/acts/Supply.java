package com.hcemanager.models.acts;

/**
 * An act that involves provision of a material by one entity to another.
 *
 * Discussion: The product is associated with the Supply Act via Participation.typeCode="product". With general Supply
 * Acts, the precise identification of the MaterialDAO (manufacturer, serial numbers, etc.) is important. Most of the detailed
 * information about the Supply should be represented using the MaterialDAO class. If delivery needs to be scheduled, tracked,
 * and billed separately, one can associate a Transportation Act with the Supply Act. Pharmacy dispense services are
 * represented as Supply Acts, associated with a SubstanceAdministration Act. The SubstanceAdministration class represents
 * the administration of medication, while dispensing is supply.
 *
 * Examples: Ordering bed sheets; Dispensing of a drug; Issuing medical supplies from storage
 *
 * @author daniel.
 */
public class Supply extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    protected String quantity;
    protected String expectedUseTime;
    protected String idSupply;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public Supply() {
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getExpectedUseTime() {
        return expectedUseTime;
    }

    public void setExpectedUseTime(String expectedUseTime) {
        this.expectedUseTime = expectedUseTime;
    }

    public String getIdSupply() {
        return idSupply;
    }

    public void setIdSupply(String idSupply) {
        this.idSupply = idSupply;
    }
}
