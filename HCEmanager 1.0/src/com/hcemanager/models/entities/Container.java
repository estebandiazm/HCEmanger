package com.hcemanager.models.entities;

import com.hcemanager.models.codes.Code;

/**
 * A subtype of ManufacturedMaterial used to hold other Entities for purposes such as transportation or protection of
 * contents from loss or damage.
 *
 * Rationale: The specifications for this class arose from the collaboration between HL7 and the NCCLS. Many of the
 * attribute definitions are drawn from or reference the NCCLS standard. With amorphic substances (liquids, gases)
 * a container is required. However, the content of a container is always distinguishable and relatively easily separable
 * from the container, unlike the content (ingredient) of a mixture.
 *
 * Usage: A container is related to a content material through Role.classCode = CONT (content).
 *
 * @author daniel.
 */
public class Container extends ManufacturedMaterial {

    private Code capTypeCode;
    private Code separatorTypeCode;
    private String capacityQuantity;
    private String heightQuantity;
    private String diameterQuantity;
    private String barrierDeltaQuantity;
    private String bottomDeltaQuantity;
    private String idContainer;


    public Container() {
    }

    public Code getCapTypeCode() {
        return capTypeCode;
    }

    public void setCapTypeCode(Code capTypeCode) {
        this.capTypeCode = capTypeCode;
    }

    public Code getSeparatorTypeCode() {
        return separatorTypeCode;
    }

    public void setSeparatorTypeCode(Code separatorTypeCode) {
        this.separatorTypeCode = separatorTypeCode;
    }

    public String getCapacityQuantity() {
        return capacityQuantity;
    }

    public void setCapacityQuantity(String capacityQuantity) {
        this.capacityQuantity = capacityQuantity;
    }

    public String getHeightQuantity() {
        return heightQuantity;
    }

    public void setHeightQuantity(String heightQuantity) {
        this.heightQuantity = heightQuantity;
    }

    public String getDiameterQuantity() {
        return diameterQuantity;
    }

    public void setDiameterQuantity(String diameterQuantity) {
        this.diameterQuantity = diameterQuantity;
    }

    public String getBarrierDeltaQuantity() {
        return barrierDeltaQuantity;
    }

    public void setBarrierDeltaQuantity(String barrierDeltaQuantity) {
        this.barrierDeltaQuantity = barrierDeltaQuantity;
    }

    public String getBottomDeltaQuantity() {
        return bottomDeltaQuantity;
    }

    public void setBottomDeltaQuantity(String bottomDeltaQuantity) {
        this.bottomDeltaQuantity = bottomDeltaQuantity;
    }

    public String getIdContainer() {
        return idContainer;
    }

    public void setIdContainer(String idContainer) {
        this.idContainer = idContainer;
    }
}
