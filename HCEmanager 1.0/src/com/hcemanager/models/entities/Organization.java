package com.hcemanager.models.entities;

import com.hcemanager.models.codes.Code;

/**
 * An Entity representing a formalized group of entities with a common purpose (e.g. administrative, legal, political)
 * and the infrastructure to carry out that purpose.
 *
 * Examples: Companies and institutions, a government department, an incorporated body that is responsible for administering
 * a facility, an insurance company.
 *
 * @author daniel .
 */
public class Organization extends Entity {

    private String idOrganization;
    private String addr;
    private Code standardIndustryCode;

    public Organization() {
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Code getStandardIndustryCode() {
        return standardIndustryCode;
    }

    public void setStandardIndustryCode(Code standardIndustryCode) {
        this.standardIndustryCode = standardIndustryCode;
    }

    public String getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(String id) {
        this.idOrganization = id;
    }
}
