package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

/**
 * A dynamic list of individual instances of Act which reflect the needs of an individual worker, team of workers,
 * or an organization to view groups of Acts for clinical or administrative reasons.
 *
 * Discussion: The grouped Acts are related to the WorkingList via an ActRelationship of type 'COMP' (component).
 * 
 * Examples: Problem lists, goal lists, allergy lists, to-do lists, etc.
 *
 * @author daniel.
 */
public class WorkingList extends Act {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private Code ownershipLevelCode;
    private String idWorkingList;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public WorkingList() {
    }

    public Code getOwnershipLevelCode() {
        return ownershipLevelCode;
    }

    public void setOwnershipLevelCode(Code ownershipLevelCode) {
        this.ownershipLevelCode = ownershipLevelCode;
    }

    public String getIdWorkingList() {
        return idWorkingList;
    }

    public void setIdWorkingList(String idWorkingList) {
        this.idWorkingList = idWorkingList;
    }
}
