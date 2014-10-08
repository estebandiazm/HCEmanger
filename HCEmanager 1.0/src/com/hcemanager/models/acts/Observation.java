package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

/**
 * An Act of recognizing and noting information about the subject, and whose immediate and primary outcome (post-condition)
 * is new data about a subject. Observations often involve measurement or other elaborate methods of investigation,
 * but may also be simply assertive statements.
 *
 * Discussion: Structurally, many observations are name-valueObservation-pairs, where the Observation.code (inherited from Act) is
 * the name and the Observation.valueObservation is the valueObservation of the property. Such a construct is also known as a "variable"
 * (a named feature that can assume a valueObservation); hence, the Observation class is always used to hold generic name-valueObservation-pairs
 * or variables, even though the variable valuation may not be the result of an elaborate observation method. It may be a
 * simple answer to a question or it may be an assertion or setting of a parameter.
 *
 * As with all Act statements, Observation statements describe what was done, and in the case of Observations, this includes
 * a description of what was actually observed ("results" or "answers"); and those "results" or "answers" are part of
 * the observation and not split off into other objects.
 *
 * An observation may consist of component observations each having their own Observation.code and Observation.valueObservation.
 * In this case, the composite observation may not have an Observation.valueObservation for itself. For instance, a white blood
 * cell count consists of the sub-observations for the counts of the various granulocytes, lymphocytes and other normal or
 * abnormal blood cells (e.g., blasts). The overall white blood cell count Observation itself may therefore not have a valueObservation
 * by itself (even though it could have one, e.g., the sum total of white blood cells). Thus, as long as an Act is essentially
 * an Act of recognizing and noting information about a subject, it is an Observation, regardless of whether it has a simple
 * valueObservation by itself or whether it has sub-observations.
 *
 * Even though observations are professional acts (see Act) and as such are intentional actions, this does not require
 * that every possible outcome of an observation be pondered in advance of it being actually made. For instance, differential
 * white blood cell counts (WBC) rarely show blasts, but if they do, this is part of the WBC observation even though blasts
 * might not be predefined in the structure of a normal WBC.
 *
 * Diagnoses, findings, symptoms, etc. are also Observations. The Observation.code (or the reference to the Observation
 * definition) specifies the kind of diagnosis (e.g. "chief complaint" or "discharge diagnosis") and the valueObservation specifies
 * the diagnosis code or symptom code.
 *
 * @author Danial Bellon & Juan Diaz
 */
public class Observation extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    protected String idObservation;
    protected String valueObservation;
    protected Code interpretationCode;
    protected Code methodCode;
    protected Code targetSiteCode;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    public String getValueObservation() {
        return valueObservation;
    }

    public void setValueObservation(String valueObservation) {
        this.valueObservation = valueObservation;
    }

    public Code getInterpretationCode() {
        return interpretationCode;
    }

    public void setInterpretationCode(Code interpretationCode) {
        this.interpretationCode = interpretationCode;
    }

    public Code getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(Code methodCode) {
        this.methodCode = methodCode;
    }

    public Code getTargetSiteCode() {
        return targetSiteCode;
    }

    public void setTargetSiteCode(Code targetSiteCode) {
        this.targetSiteCode = targetSiteCode;
    }

    public String getIdObservation() {
        return idObservation;
    }

    public void setIdObservation(String idObservation) {
        this.idObservation = idObservation;
    }
}
