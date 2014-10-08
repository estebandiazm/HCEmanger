package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.InvoiceElement;

import java.util.List;

/**
 * THis interface define the invoice element DAO methods
 * @author daniel, esteban.
 */
public interface InvoiceElementDAO {

    public void insertInvoiceElement(InvoiceElement invoiceElement) throws InvoiceElementExistsException, ActExistsException, CodeExistsException;
    public void updateInvoiceElement(InvoiceElement invoiceElement) throws InvoiceElementExistsException, InvoiceElementNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteInvoiceElement(String id) throws InvoiceElementCannotBeDeleted, InvoiceElementNotExistsException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public InvoiceElement getInvoiceElement(String id) throws InvoiceElementNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<InvoiceElement> getInvoiceElements() throws InvoiceElementNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
