package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.rowMappers.InvoiceElementRowMapper;
import com.hcemanager.dao.acts.interfaces.InvoiceElementDAO;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.InvoiceElement;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class InvoiceElementDAOImpl extends JdbcDaoSupport implements InvoiceElementDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentence constants
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into InvoiceElement(idInvoiceElement,netAmt,unitPriceAmt,unitQuantity,pointsNumber,factorNumber,Act_idAct) values (?,?,?,?,?,?,?)";
    public static final String UPDATE = "update into InvoiceElement set netAmt=?,unitPriceAmt=?,unitQuantity=?,pointsNumber=?,factorNumber=? where idInvoiceElement=?";
    public static final String DELETE = "delete from InvoiceElement where idInvoiceElement=?";
    public static final String SELECT_INVOICE_ELEMENT = "select * from InvoiceElement inner join Act where Act_idAct=idAct and  idInvoiceElement=?";
    public static final String SELECT_INVOICE_ELEMENTS = "select * from InvoiceElement inner join Act where Act_idAct=idAct and";
    public static final String INSERT_CODE = "insert into InvoiceElement_has_Codes (InvoiceElement_idInvoiceElement,Codes_idCodes,type) values (?,?,?)";
    public static final String UPDATE_CODE = "update InvoiceElement_has_Codes set Codes_idCodes = ? where InvoiceElement_idInvoiceElement=? and type =? ";
    public static final String DELETE_CODES = "delete from InvoiceElement_has_Codes where InvoiceElement_idInvoiceElement=?";
    public static final String SELECT_CODES = "select * from InvoiceElement_has_Codes inner join Codes where Codes_idCodes = idCodes and InvoiceElement_idInvoiceElement=?";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert an invoice element into the db
     * @param invoiceElement an InvoiceElement object
     * @throws InvoiceElementExistsException
     */
    @Override
    public void insertInvoiceElement(InvoiceElement invoiceElement) throws InvoiceElementExistsException, ActExistsException, CodeExistsException {
       try {
           SpringServices.getActDAO().insertAct(invoiceElement);

           getJdbcTemplate().update(INSERT,
                   invoiceElement.getIdInvoiceElement(),
                   invoiceElement.getNetAmt(),
                   invoiceElement.getUnitPriceAmt(),
                   invoiceElement.getUnitQuantity(),
                   invoiceElement.getPointsNumber(),
                   invoiceElement.getFactorNumber(),
                   invoiceElement.getId());
       }catch (DataIntegrityViolationException e){
           throw new InvoiceElementExistsException(e);
       }
        try {
            getJdbcTemplate().update(INSERT_CODE,
                    invoiceElement.getIdInvoiceElement(),
                    invoiceElement.getModifierCode().getId(),
                    "modifierCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update an Invoice element into the db
     * @param invoiceElement an InvoiceElement object
     * @throws InvoiceElementExistsException
     * @throws InvoiceElementNotExistsException
     */
    @Override
    public void updateInvoiceElement(InvoiceElement invoiceElement) throws InvoiceElementExistsException, InvoiceElementNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        if (isDifferent(invoiceElement)){
            try {
                SpringServices.getActDAO().updateAct(invoiceElement);

                int rows = getJdbcTemplate().update(UPDATE,
                        invoiceElement.getNetAmt(),
                        invoiceElement.getUnitPriceAmt(),
                        invoiceElement.getUnitQuantity(),
                        invoiceElement.getPointsNumber(),
                        invoiceElement.getFactorNumber(),
                        invoiceElement.getIdInvoiceElement());
                if (rows==0){
                    throw new InvoiceElementNotExistsException();
                }

                updateCodes(invoiceElement);
            }catch (DataIntegrityViolationException e){
                throw new InvoiceElementExistsException(e);
            }
        }else {
            SpringServices.getActDAO().updateAct(invoiceElement);
            updateCodes(invoiceElement);
        }
    }

    /**
     * Delete an invoice element from the db
     * @param id the invoice element id
     * @throws InvoiceElementCannotBeDeleted
     * @throws InvoiceElementNotExistsException
     */
    @Override
    public void deleteInvoiceElement(String id) throws InvoiceElementCannotBeDeleted, InvoiceElementNotExistsException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new InvoiceElementNotExistsException();
            }

            InvoiceElement invoiceElement = getInvoiceElement(id);
            SpringServices.getActDAO().deleteAct(invoiceElement.getId());

        }catch (DataIntegrityViolationException e){
            throw new InvoiceElementCannotBeDeleted(e);
        }
    }

    /**
     * Get an invoice element from the db
     * @param id the invoice element id
     * @return invoice element
     * @throws InvoiceElementNotExistsException
     */
    @Override
    public InvoiceElement getInvoiceElement(String id) throws InvoiceElementNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            InvoiceElement invoiceElement =
                    getJdbcTemplate().queryForObject(SELECT_INVOICE_ELEMENT, new InvoiceElementRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(invoiceElement,actDAO.getActCodes(invoiceElement.getId()));
            setInvoiceElementCodes(invoiceElement,getInvoiceElementCodes(id));
            return invoiceElement;

        }catch (EmptyResultDataAccessException e){
            throw new InvoiceElementNotExistsException(e);
        }
    }

    /**
     * Get all the invoice elements form the db
     * @return invoice elements
     * @throws InvoiceElementNotExistsException
     */
    @Override
    public List<InvoiceElement> getInvoiceElements() throws InvoiceElementNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<InvoiceElement> invoiceElements =
                    getJdbcTemplate().query(SELECT_INVOICE_ELEMENTS, new InvoiceElementRowMapper());

            for (InvoiceElement invoiceElement:invoiceElements){
                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(invoiceElement,actDAO.getActCodes(invoiceElement.getId()));
                setInvoiceElementCodes(invoiceElement,getInvoiceElementCodes(invoiceElement.getIdInvoiceElement()));
            }
            return invoiceElements;

        }catch (EmptyResultDataAccessException e){
            throw new InvoiceElementNotExistsException(e);
        }
    }

    /**
     * Get an invoiceElement codes
      * @param id the invoice element id
     * @return a list with invoiceElement codes
     * @throws CodeNotExistsException
     */
    public List<Code> getInvoiceElementCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set invoiceElement codes
     * @param invoiceElement an InvoiceElement object
     * @param codes the invoiceElement codes
     * @throws CodeIsNotValidException
     */
    public void setInvoiceElementCodes(InvoiceElement invoiceElement,List<Code>codes) throws CodeIsNotValidException {
        try {
            for (Code code:codes){
                String type=code.getType();
                if (type.equalsIgnoreCase("modifierCode")){
                    invoiceElement.setModifierCode(code);
                }
            }
        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Evaluate if the new invoice element is different of the old invoice element
     * @param invoiceElement an InvoiceElement object
     * @return true id the invoice element is different
     * @throws CodeIsNotValidException
     * @throws InvoiceElementNotExistsException
     * @throws CodeNotExistsException
     */
    private boolean isDifferent(InvoiceElement invoiceElement) throws CodeIsNotValidException, InvoiceElementNotExistsException, CodeNotExistsException {
        InvoiceElement oldInvoiceElement = getInvoiceElement(invoiceElement.getIdInvoiceElement());
        int differentAttrs=0;

        if (!invoiceElement.getNetAmt().equalsIgnoreCase(oldInvoiceElement.getNetAmt()))
            differentAttrs++;
        if (!invoiceElement.getUnitPriceAmt().equalsIgnoreCase(oldInvoiceElement.getUnitPriceAmt()))
            differentAttrs++;
        if (!invoiceElement.getUnitQuantity().equalsIgnoreCase(oldInvoiceElement.getUnitQuantity()))
            differentAttrs++;
        if (invoiceElement.getPointsNumber() != oldInvoiceElement.getPointsNumber())
            differentAttrs++;
        if (invoiceElement.getFactorNumber() != oldInvoiceElement.getFactorNumber())
            differentAttrs++;

        return differentAttrs != 0;
    }

    /**
     * Update the invoice element codes
     * @param invoiceElement an InvoiceElement object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(InvoiceElement invoiceElement) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getInvoiceElementCodes(invoiceElement.getIdInvoiceElement());

        for (Code code:oldCodes){
            if (code.getType().equalsIgnoreCase("modifierCode")){
                if (!code.getId().equals(invoiceElement.getModifierCode().getId())){
                    try {
                        getJdbcTemplate().update(UPDATE_CODE,invoiceElement.getModifierCode().getId(),
                                invoiceElement.getIdInvoiceElement(),"modifierCode");
                    }catch (DataIntegrityViolationException e){
                        throw new CodeExistsException(e);
                    }
                }
            }
        }
    }
}
