package com.hcemanager.models.entities;

/**
 * A subtype of MaterialDAO representing an Entity or combination of Entities transformed for a particular purpose by
 * a non-natural or manufacturing process.
 *
 * Examples: Processed food products, disposable syringes, chemistry analyzer, saline for infusion, etc.
 *
 * Discussion: This class encompasses containers, devices, software modules and facilities.
 *
 * Rationale: This class is used to further define the characteristics of Entities that are created out of other
 * Entities. These entities are identified and tracked through associations and mechanisms unique to the class,
 * such as lotName, stabilityTime and expirationTime.
 *
 * @author daniel.
 */
public class ManufacturedMaterial extends Material {

    protected String idManufacturedMaterial;
    protected String lotNumberText;
    protected String expirationTime;
    protected String stabilityTime;

    public ManufacturedMaterial() {
    }

    public String getLotNumberText() {
        return lotNumberText;
    }

    public void setLotNumberText(String lotNumberText) {
        this.lotNumberText = lotNumberText;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getStabilityTime() {
        return stabilityTime;
    }

    public void setStabilityTime(String stabilityTime) {
        this.stabilityTime = stabilityTime;
    }

    public String getIdManufacturedMaterial() {
        return idManufacturedMaterial;
    }

    public void setIdManufacturedMaterial(String id) {
        this.idManufacturedMaterial = id;
    }
}
