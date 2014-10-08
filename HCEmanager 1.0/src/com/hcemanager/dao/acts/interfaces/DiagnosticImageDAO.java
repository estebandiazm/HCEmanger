package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.DiagnosticImage;

import java.util.List;

/**
 * This interface define the Diagnostic image DAO methods
 * @author Daniel Bellon & Juan Diaz
 */
public interface DiagnosticImageDAO {

    public void insertDiagnosticImage(DiagnosticImage diagnosticImage) throws DiagnosticImageExistsException,
            ObservationExistsException, ActExistsException, CodeExistsException;
    public void updateDiagnosticImage(DiagnosticImage diagnosticImage) throws DeviceTaskExistsException,
            DiagnosticImageNotExistsException, ObservationExistsException, ObservationNotExistsException,
            ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteDiagnosticImage(String id) throws DiagnosticImageNotExistsException, DiagnosticImageCannotBeDeleted,
            ObservationNotExistsException, ObservationCannotBeDeleteException, ActNotExistsException, ActCannotBeDeleted,
            CodeIsNotValidException, CodeNotExistsException;
    public DiagnosticImage getDiagnosticImage(String id) throws DiagnosticImageNotExistsException,
            CodeNotExistsException, CodeIsNotValidException;
    public List<DiagnosticImage> getDiagnosticImages() throws DiagnosticImageNotExistsException, CodeNotExistsException,
            CodeIsNotValidException;
}
