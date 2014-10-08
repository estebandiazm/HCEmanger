package com.hcemanager.models.entities;

import com.hcemanager.models.codes.Code;

/**
 * A physical thing, group of physical things or an organization capable of participating in Acts, while in a role.
 *
 * @author daniel.
 */
public class Entity {

    protected Code classCode;
    protected Code determinerCode;
    protected Code code;
    protected Code statusCode;
    protected Code riskCode;
    protected Code handlingCode;
    protected String id;
    protected String name;
    protected String quantity;
    protected String description;
    protected String existenceTime;
    protected String telecom;

    public Entity() {
    }

    public Code getClassCode() {
        return classCode;
    }

    public void setClassCode(Code classCode) {
        this.classCode = classCode;
    }

    public Code getDeterminerCode() {
        return determinerCode;
    }

    public void setDeterminerCode(Code determinerCode) {
        this.determinerCode = determinerCode;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Code getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Code statusCode) {
        this.statusCode = statusCode;
    }

    public Code getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(Code riskCode) {
        this.riskCode = riskCode;
    }

    public Code getHandlingCode() {
        return handlingCode;
    }

    public void setHandlingCode(Code handlingCode) {
        this.handlingCode = handlingCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExistenceTime() {
        return existenceTime;
    }

    public void setExistenceTime(String existenceTime) {
        this.existenceTime = existenceTime;
    }

    public String getTelecom() {
        return telecom;
    }

    public void setTelecom(String telecom) {
        this.telecom = telecom;
    }
}
