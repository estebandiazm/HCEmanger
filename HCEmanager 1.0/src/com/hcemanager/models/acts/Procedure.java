package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

/**
 * An Act whose immediate and primary outcome (post-condition) is the alteration of the physical condition of the subject.
 *
 * Examples: Procedures may involve the disruption of some body surface (e.g. an incision in a surgical procedure)
 * conservative procedures such as reduction of a luxated join, including physiotherapy such as chiropractic treatment,
 * massage, balneotherapy, acupuncture, shiatsu, etc. Outside of clinical medicine, procedures may be such things as
 * alteration of environments (e.g. straightening rivers, draining swamps, building dams) or the repair or change of
 * machinery etc.
 *
 * Discussion: Applied to clinical medicine, procedure is but one among several types of clinical activities such as
 * observation, substance-administrations, and communicative interactions (e.g. teaching, advice, psychotherapy,
 * represented simply as Acts without special attributes). Procedure does not subsume those other activities nor is
 * procedure subsumed by them. Notably Procedure does not comprise all acts of whose intent is intervention or treatment.
 * Whether the bodily alteration is appreciated or intended as beneficial to the subject is likewise irrelevant,
 * what counts is that the act is essentially an alteration of the physical condition of the subject.
 *
 * The choice between representations for real activities is based on whether the specific properties of procedure are
 * applicable and whether the activity or activity step's necessary post-condition is the physical alteration. For example,
 * taking an x-ray image may sometimes be called "procedure", but it is not a Procedure in the RIM sense for an x-ray
 * image is not done to alter the physical condition of the body.
 *
 * Many clinical activities combine Acts of Observation and Procedure nature into one composite. For instance,
 * interventional radiology (e.g., catheter directed thrombolysis) does both observing and treating, and most surgical
 * procedures include conscious and documented Observation steps. These clinical activities therefore are best represented
 * by multiple component acts each of the appropriate type.
 *
 * @author daniel.
 */
public class Procedure extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private Code methodCode;
    private Code approachSiteCode;
    private Code targetSiteCode;
    private String idProcedure;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public Procedure() {
    }

    public Code getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(Code methodCode) {
        this.methodCode = methodCode;
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

    public String getIdProcedure() {
        return idProcedure;
    }

    public void setIdProcedure(String idProcedure) {
        this.idProcedure = idProcedure;
    }
}
