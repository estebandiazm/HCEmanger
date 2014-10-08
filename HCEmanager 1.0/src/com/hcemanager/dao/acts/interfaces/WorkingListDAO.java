package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.WorkingList;

import java.util.List;

/**
 * This interface define the working list DAO methods
 * @author daniel, esteban.
 */
public interface WorkingListDAO {

    public void insertWorkingLIst(WorkingList workingList) throws WorkingListExistsException, ActExistsException, CodeExistsException;
    public void updateWorkingList(WorkingList workingList) throws WorkingListExistsException, WorkingListNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteWorkingList(String id) throws WorkingListNotExistsException, WorkingListCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public WorkingList getWorkingList(String id) throws WorkingListNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<WorkingList> getWorkingLists() throws WorkingListNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
