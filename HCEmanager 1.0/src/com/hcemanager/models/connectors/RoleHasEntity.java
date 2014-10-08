package com.hcemanager.models.connectors;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class RoleHasEntity {

    //------------------------------------------------------------------------------------------------------------------
    //Attributes
    //------------------------------------------------------------------------------------------------------------------
    private String idRole;
    private String idEntity;

    //------------------------------------------------------------------------------------------------------------------
    //Getters and Setters.
    //------------------------------------------------------------------------------------------------------------------
    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(String idEntity) {
        this.idEntity = idEntity;
    }
}
