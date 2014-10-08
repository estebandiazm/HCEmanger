package com.hcemanager.models.roles;

import com.hcemanager.models.codes.Code;

/**
 * A role played by a device when the device is used to administer therapeutic agents (medication and vital elements)
 * into the body, or to drain material (e.g., exudates, pus, urine, air, blood) out of the body.
 *
 * Discussion: In general, Access is a Role of a ManufacturedMaterial or DeviceDAO, something specifically manufactured
 * or created to serve that purpose, such as a catheter or cannula inserted into a compartment of the body.
 *
 * Devices in the role of an Access are typically used in intake/outflow observations, and in medication routing instructions.
 * Microbiologic observations on the material itself or on fluids coming out of a drain, are also common.
 *
 * Rationale: The Access role primarily exists in order to describe material actually deployed as an access,
 * and not so much the fresh material as it comes from the manufacturer. For example, in supply ordering a box of
 * catheters from a distributor, it is not necessary to use the Access role class, since the material attributes will
 * usually suffice to describe and identify the product for the order. But the Access role is used to communicate about
 * the maintenance, intake/outflow, and due replacement of tubes and drains.
 *
 * @author daniel.
 */
public class Access extends Role {

    private Code approachSiteCode;
    private Code targetSiteCode;
    private String idAccess;
    private String gaugeQuantity;


    public Access() {
    }

    public Code getApproachSiteCode() {
        return approachSiteCode;
    }

    public void setApproachSiteCode(Code approachSiteCode) {
        this.approachSiteCode = approachSiteCode;
    }

    public Code getTargetSiteCode() {
        return targetSiteCode;
    }

    public void setTargetSiteCode(Code targetSiteCode) {
        this.targetSiteCode = targetSiteCode;
    }

    public String getGaugeQuantity() {
        return gaugeQuantity;
    }

    public void setGaugeQuantity(String gaugeQuantity) {
        this.gaugeQuantity = gaugeQuantity;
    }

    public String getIdAccess() {
        return idAccess;
    }

    public void setIdAccess(String idAccess) {
        this.idAccess = idAccess;
    }
}
