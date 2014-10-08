package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;


/**
 * A record of something that is being done, has been done, can be done, or is intended or requested to be done.
 *
 * Examples: The kinds of acts that are common in health care are (1) a clinical observation, (2) an assessment of
 * health condition (such as problems and diagnoses), (3) healthcare goals, (4) treatment services (such as medication,
 * surgery, physical and psychological therapy), (5) assisting, monitoring or attending, (6) training and education services
 * to patients and their next of kin, (7) and notary services (such as advanced directives or living will),
 * (8) editing and maintaining documents, and many others.
 *
 * Discussion and Rationale: Acts are the pivot of the RIM; all domain information and processes are represented primarily
 * in Acts. Any profession or business, including healthcare, is primarily constituted of intentional actions, performed
 * and recorded by responsible actors. An Act-instance is a record of such an intentional action. Intentional actions are
 * distinguished from something that happens by forces of nature (natural events). Such natural events are not Acts by
 * themselves, but may be recorded as observed (Observation).
 *
 * Acts connect to Entities in their Roles through Participations and connect to other Acts through ActRelationships.
 * Participations are the authors, performers and other responsible parties as well as subjects and beneficiaries
 * (which includes tools and material used in the performance of the act, which are also subjects).
 * The moodCode distinguishes between Acts that are meant as factual records, vs. records of intended or ordered services,
 * and the other modalities in which act can appear.
 *
 * One of the Participations that all acts have (at least implicitly) is a primary author, who is responsible of the Act
 * and who "owns" the act. Responsibility for the act means responsibility for what is being stated in the Act and as what
 * it is stated. Ownership of the act is assumed in the sense of who may operationally modify the same act. Ownership and
 * responsibility of the Act is not the same as ownership or responsibility of what the Act-object refers to in the real world.
 * he same real world activity can be described by two people, each being the author of their Act, describing the same real
 * world activity. Yet one can be a witness while the other can be a principal performer. The performer has responsibilities
 * for the physical actions; the witness only has responsibility for making a true statement to the best of his or her ability.
 * The two Act-instances may even disagree, but because each is properly attributed to its author, such disagreements can
 * exist side by side and left to arbitration by a recipient of these Act-instances.
 *
 * In this sense, an Act-instance represents a "statement" according to Rector and Nowlan (1991) [Foundations for an electronic
 * medical record. Methods Inf Med. 30.] Rector and Nowlan have emphasized the importance of understanding the medical
 * record not as a collection of facts, but "a faithful record of what clinicians have heard, seen, thought, and done."
 * Rector and Nowlan go on saying that "the other requirements for a medical record, e.g., that it be attributable and
 * permanent, follow naturally from this view." Indeed the Act class is this attributable statement, and the rules of
 * updating acts (discussed in the state-transition model, see Act.statusCode) versus generating new Act-instances are
 * designed according to this principle of permanent attributable statements.
 *
 * Rector and Nolan focus on the electronic medical record as a collection of statements, while attributed statements,
 * these are still mostly factual statements. However, the Act class goes beyond this limitation to attributed factual
 * statements, representing what is known as "speech-acts" in linguistics and philosophy. The notion of speech-act includes
 * that there is pragmatic meaning in language utterances, aside from just factual statements; and that these utterances
 * interact with the real world to change the state of affairs, even directly cause physical activities to happen.
 * For example, an order is a speech act that (provided it is issued adequately) will cause the ordered action to be
 * physically performed. The speech act theory has culminated in the seminal work by Austin (1962) [How to do things
 * with words. Oxford University Press].
 *
 * An activity in the real world may progress from defined, through planned and ordered to executed, which is represented
 * as the mood of the Act. Even though one might think of a single activity as progressing from planned to executed, this
 * progression is reflected by multiple Act-instances, each having one and only one mood that will not change along the
 * Act-instance's life cycle. This is because the attribution and content of speech acts along this progression of an
 * activity may be different, and it is often critical that a permanent and faithful record be maintained of this progression.
 * The specification of orders or promises or plans must not be overwritten by the specification of what was actually done,
 * so as to allow comparing actions with their earlier specifications. Act-instances that describe this progression of the
 * same real world activity are linked through the ActRelationships (of the relationship category "sequel").
 *
 * Act as statements or speech-acts are the only representation of real world facts or processes in the HL7 RIM.
 * The truth about the real world is constructed through a combination (and arbitration) of such attributed statements only,
 * and there is no class in the RIM whose objects represent "objective state of affairs" or "real processes" independent
 * from attributed statements. As such, there is no distinction between an activity and its documentation. Every Act
 * includes both to varying degrees. For example, a factual statement made about recent (but past) activities, authored
 * (and signed) by the performer of such activities, is commonly known as a procedure report or original documentations
 * (e.g., surgical procedure report, clinic note etc.). Conversely, a status update on an activity that is presently in
 * progress, authored by the performer (or a close observer) is considered to capture that activity (and is later superceded
 * by a full procedure report). However, both status update and procedure report are acts of the same kind, only distinguishe
 * d by mood and state (see statusCode) and completeness of the information.
 *
 * @author daniel.
 */
public class Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    protected boolean independentInd;
    protected boolean interruptibleInd;
    protected boolean negationInd;
    protected Code classCode;
    protected Code code;
    protected Code confidentialityCode;
    protected Code languageCode;
    protected Code levelCode;
    protected Code modeCode;
    protected Code priorityCode;
    protected Code reasonCode;
    protected Code statusCode;
    protected Code uncertaintyCode;
    protected String effectiveTime;
    protected String activityTime;
    protected String availabilityTime;
    protected String repeatNumber;
    protected String derivationExpr;
    protected String id;
    protected String text;
    protected String title;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
 */
    public Act() {
    }

    public boolean isIndependentInd() {
        return independentInd;
    }

    public void setIndependentInd(boolean independentInd) {
        this.independentInd = independentInd;
    }

    public boolean isInterruptibleInd() {
        return interruptibleInd;
    }

    public void setInterruptibleInd(boolean interruptibleInd) {
        this.interruptibleInd = interruptibleInd;
    }

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

    public Code getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(Code languageCode) {
        this.languageCode = languageCode;
    }

    public Code getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(Code levelCode) {
        this.levelCode = levelCode;
    }

    public Code getModeCode() {
        return modeCode;
    }

    public void setModeCode(Code modeCode) {
        this.modeCode = modeCode;
    }

    public Code getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(Code priorityCode) {
        this.priorityCode = priorityCode;
    }

    public Code getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Code reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Code getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Code statusCode) {
        this.statusCode = statusCode;
    }

    public Code getUncertaintyCode() {
        return uncertaintyCode;
    }

    public void setUncertaintyCode(Code uncertaintyCode) {
        this.uncertaintyCode = uncertaintyCode;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getAvailabilityTime() {
        return availabilityTime;
    }

    public void setAvailabilityTime(String availabilityTime) {
        this.availabilityTime = availabilityTime;
    }

    public String getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(String repeatNumber) {
        this.repeatNumber = repeatNumber;
    }

    public String getDerivationExpr() {
        return derivationExpr;
    }

    public void setDerivationExpr(String derivationExpr) {
        this.derivationExpr = derivationExpr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
