package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

/**
 * A public health case is an Observation representing a condition or event that has a specific significance for public
 * health. Typically it involves an instance or instances of a reportable infectious disease or other condition.
 * The public health case can include a health-related event concerning a single individual or it may refer to multiple
 * health-related events that are occurrences of the same disease or condition of interest to public health. An outbreak
 * involving multiple individuals may be considered as a type of public health case. A public health case definition
 * (Act.moodCode = "definition") includes the description of the clinical, laboratory, and epidemiologic indicators
 * associated with a disease or condition of interest to public health. There are case definitions for conditions that
 * are reportable, as well as for those that are not. There are also case definitions for outbreaks. A public health
 * case definition is a construct used by public health for the purpose of counting cases, and should not be used as
 * clinical indications for treatment. Examples include AIDS, toxic-shock syndrome, and salmonellosis and their associated
 * indicators that are used to define a case.
 *
 * @author Daniel Bellon & Esteban Diaz
 */
public class PublicHealthCase extends Observation {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private Code detectionMethodCode;
    private Code transmissionModeCode;
    private Code diseaseImportCode;
    private String idPublicHealthCase;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    public Code getDetectionMethodCode() {
        return detectionMethodCode;
    }

    public void setDetectionMethodCode(Code detectionMethodCode) {
        this.detectionMethodCode = detectionMethodCode;
    }

    public Code getTransmissionModeCode() {
        return transmissionModeCode;
    }

    public void setTransmissionModeCode(Code transmissionModeCode) {
        this.transmissionModeCode = transmissionModeCode;
    }

    public Code getDiseaseImportCode() {
        return diseaseImportCode;
    }

    public void setDiseaseImportCode(Code diseaseImportCode) {
        this.diseaseImportCode = diseaseImportCode;
    }

    public String getIdPublicHealthCase() {
        return idPublicHealthCase;
    }

    public void setIdPublicHealthCase(String idPublicHealthCase) {
        this.idPublicHealthCase = idPublicHealthCase;
    }
}
