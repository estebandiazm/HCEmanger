package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.ActCannotBeDeleted;
import com.hcemanager.exceptions.acts.ActExistsException;
import com.hcemanager.exceptions.acts.ActNotExistsException;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Act;
import com.hcemanager.models.codes.Code;

import java.util.List;

/**
 *
 * This interface define the Act DAO methods
 * @author daniel.
 */
public interface ActDAO {

    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    public void insertAct(Act act) throws ActExistsException, CodeExistsException;
    public void updateAct(Act act) throws ActNotExistsException, ActExistsException, CodeNotExistsException, CodeIsNotValidException, CodeExistsException;
    public void deleteAct(String id) throws ActNotExistsException, ActCannotBeDeleted;
    public Act getAct(String id) throws ActNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<Act> getActs() throws ActNotExistsException;
    public List<Code> getActCodes(String id) throws CodeNotExistsException;
    public void setActCodes(Act act,List<Code>codes) throws CodeIsNotValidException;


}
