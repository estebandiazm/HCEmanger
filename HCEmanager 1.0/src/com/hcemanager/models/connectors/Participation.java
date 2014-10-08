package com.hcemanager.models.connectors;

import com.hcemanager.models.codes.Code;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class Participation  {


    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private Code typeCode;
    private Code functionCode;
    private Code contextControlCode;
    private Code modeCode;
    private Code awarenessCode;
    private Code signatureCode;
    private Code substitutionConditionCode;
    private String idParticipation;
    private String idAct;
    private String idRole;
    private Boolean performInd;
    private String sequenceNumber;
    private Boolean negationInd;
    private String noteText;
    private String time;
    private String signatureText;

    //------------------------------------------------------------------------------------------------------------------
    //Getters and setters
    //------------------------------------------------------------------------------------------------------------------

    public String getIdParticipation() {
        return idParticipation;
    }

    public void setIdParticipation(String idParticipation) {
        this.idParticipation = idParticipation;
    }

    public Code getTypeCode() {return typeCode;}

    public void setTypeCode(Code typeCode) {this.typeCode = typeCode;}

    public Code getFunctionCode() {return functionCode;}

    public void setFunctionCode(Code functionCode) {this.functionCode = functionCode;}

    public Code getContextControlCode() {return contextControlCode;}

    public void setContextControlCode(Code contextControlCode) {this.contextControlCode = contextControlCode;}

    public String getSequenceNumber() {return sequenceNumber;}

    public void setSequenceNumber(String sequenceNumber) {this.sequenceNumber = sequenceNumber;}

    public Boolean isNegationInd() {return getNegationInd();}

    public void setNegationInd(Boolean negationInd) {this.negationInd = negationInd;}

    public String getNoteText() {return noteText;}

    public void setNoteText(String noteText) {this.noteText = noteText;}

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public Code getModeCode() {return modeCode;}

    public void setModeCode(Code modeCode) {this.modeCode = modeCode;}

    public Code getAwarenessCode() {return awarenessCode;}

    public void setAwarenessCode(Code awarenessCode) {this.awarenessCode = awarenessCode;}

    public Code getSignatureCode() {return signatureCode;}

    public void setSignatureCode(Code signatureCode) {this.signatureCode = signatureCode;}

    public String getSignatureText() {return signatureText;}

    public void setSignatureText(String signatureText) {this.signatureText = signatureText;}

    public Boolean isPerformInd() {return getPerformInd();}

    public void setPerformInd(Boolean performInd) {this.performInd = performInd;}

    public Code getSubstitutionConditionCode() {return substitutionConditionCode;}

    public void setSubstitutionConditionCode(Code substitutionConditionCode) {this.substitutionConditionCode = substitutionConditionCode;}

    public Boolean getPerformInd() {
        return performInd;
    }

    public Boolean getNegationInd() {
        return negationInd;
    }

    public String getIdAct() {
        return idAct;
    }

    public void setIdAct(String idAct) {
        this.idAct = idAct;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }
}
