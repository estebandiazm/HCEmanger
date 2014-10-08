package com.hcemanager.dao.acts.rowMappers;

import com.hcemanager.models.acts.DiagnosticImage;
import com.hcemanager.models.acts.Observation;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class DiagnosticImageRowMapper implements ParameterizedRowMapper<DiagnosticImage> {

    ObservationRowMapper observationRowMapper = new ObservationRowMapper();

    /**
     * Make an diagnosticImage mapper
     * @param resultSet argument
     * @param i index
     * @return diagnosticImage
     * @throws java.sql.SQLException
     */
    @Override
    public DiagnosticImage mapRow(ResultSet resultSet, int i) throws SQLException {

        Observation observation = observationRowMapper.mapRow(resultSet,i);
        DiagnosticImage diagnosticImage = new DiagnosticImage();

        // -------------------------------------------------------------------------------------------------------------
        // DiagnosticImage arguments
        // -------------------------------------------------------------------------------------------------------------

        diagnosticImage.setIdDiagnosticImage(resultSet.getString("idDiagnosticImage"));

        // -------------------------------------------------------------------------------------------------------------
        // Observation arguments
        // -------------------------------------------------------------------------------------------------------------

        diagnosticImage.setIdObservation(observation.getIdObservation());
        diagnosticImage.setValueObservation(observation.getValueObservation());

        // -------------------------------------------------------------------------------------------------------------
        // Act arguments
        // -------------------------------------------------------------------------------------------------------------

        diagnosticImage.setIndependentInd(observation.isIndependentInd());
        diagnosticImage.setInterruptibleInd(observation.isInterruptibleInd());
        diagnosticImage.setNegationInd(observation.isNegationInd());
        diagnosticImage.setEffectiveTime(observation.getEffectiveTime());
        diagnosticImage.setActivityTime(observation.getActivityTime());
        diagnosticImage.setAvailabilityTime(observation.getAvailabilityTime());
        diagnosticImage.setRepeatNumber(observation.getRepeatNumber());
        diagnosticImage.setDerivationExpr(observation.getDerivationExpr());
        diagnosticImage.setId(observation.getId());
        diagnosticImage.setText(observation.getText());
        diagnosticImage.setTitle(observation.getTitle());


        return diagnosticImage;
    }
}
