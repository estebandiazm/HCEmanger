package com.hcemanager.dao.medicalAppointments.rowMappers;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.medicalAppointments.DicomImage;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class DicomImageRowMapper implements ParameterizedRowMapper<DicomImage> {
    @Override
    public DicomImage mapRow(ResultSet resultSet, int i) throws SQLException {
        DicomImage dicomImage = new DicomImage();

        dicomImage.setIdDicomImage(resultSet.getString("idDicomImage"));
        dicomImage.setImage(resultSet.getBlob("image"));
        dicomImage.setMetadata(resultSet.getString("metadata"));
        try {
            dicomImage.setPatient(SpringServices.getUserDAO().getUser(resultSet.getString("User_idUser")));
        } catch (UserNotExistsException e) {
            e.printStackTrace();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }

        return dicomImage;

    }
}
