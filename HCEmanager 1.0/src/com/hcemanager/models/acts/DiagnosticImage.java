package com.hcemanager.models.acts;

import com.hcemanager.models.codes.Code;

import java.sql.Blob;

/**
 *An observation whose immediate and primary outcome (post-condition) is new data about a subject, in the form of
 *visualized images.
 *
 * @author Daniel Bellon & Juan Diaz
 */
public class DiagnosticImage extends Observation {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private Code subjectOrientationCode;
    private String idDiagnosticImage;
    private Blob image;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    public Code getSubjectOrientationCode() {
        return subjectOrientationCode;
    }

    public void setSubjectOrientationCode(Code subjectOrientationCode) {
        this.subjectOrientationCode = subjectOrientationCode;
    }

    public String getIdDiagnosticImage() {
        return idDiagnosticImage;
    }

    public void setIdDiagnosticImage(String idDiagnosticImage) {
        this.idDiagnosticImage = idDiagnosticImage;
    }

    public Blob getImage() {return image;}

    public void setImage(Blob image) {this.image = image;}

}
