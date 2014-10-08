package com.hcemanager.models.roles;

import com.hcemanager.models.codes.Code;

/**
 * A competency of the Entity playing the Role as identified, defined, guaranteed, or acknowledged by the Entity
 * that scopes the Role.
 *
 * Discussion: An Entity participates in an Act as in a particular Role. Note that a particular entity in a particular
 * role can participate in an act in many ways. Thus, a PersonDAO in the role of a practitioner can participate in a
 * patient encounter as a rounding physician or as an attending physician. The Role defines the competency of the
 * Entity irrespective of any Act, as opposed to Participation, which are limited to the scope of an Act.
 * Each role is "played" by one Entity, called the "player" and is "scoped" by another Entity, called the "scoper".
 * Thus the Role of "patient" may be played by a person and scoped by the provider organization from which the patient
 * will receive services. Similarly, the employer scopes an "employee" role.
 * The identifier of the Role identifies the Entity playing the role in that role. This identifier is assigned by the
 * scoping Entity to the player. The scoping Entity need not have issued the identifier, but it may have re-used an
 * existing identifier for the Entity to also identify the Entity in the Role with the scoper.
 * Most attributes of Role are attributes of the playing entity while in the particular role.
 *
 * @author Daniel Bellon & Juan Diaz
 */
public class Role {

    protected String id;
    protected String quantity;
    protected String positionNumber;
    protected String addr;
    protected String certificateText;
    protected String name;
    protected String telecom;
    protected String effectiveTime;
    protected boolean negationInd;
    protected Code classCode;
    protected Code code;
    protected Code confidentialityCode;
    protected Code statusCode;

    public boolean isNegationInd() {
        return negationInd;
    }

    public void setNegationInd(boolean negationInd) {
        this.negationInd = negationInd;
    }

    public Code getClassCode() {
        return classCode;
    }

    public void setClassCode(Code classCode) {
        this.classCode = classCode;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Code getConfidentialityCode() {
        return confidentialityCode;
    }

    public void setConfidentialityCode(Code confidentialityCode) {
        this.confidentialityCode = confidentialityCode;
    }

    public Code getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Code statusCode) {
        this.statusCode = statusCode;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(String positionNumber) {
        this.positionNumber = positionNumber;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCertificateText() {
        return certificateText;
    }

    public void setCertificateText(String certificateText) {
        this.certificateText = certificateText;
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

    public String getTelecom() {
        return telecom;
    }

    public void setTelecom(String telecom) {
        this.telecom = telecom;
    }
}
