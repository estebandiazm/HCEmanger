package com.hcemanager.models.entities;

import com.hcemanager.models.codes.Code;

/**
 * A subtype of Entity that is inanimate and locationally independent.
 *
 * Examples: Pharmaceutical substances (including active vaccines containing retarded virus), disposable supplies,
 * durable equipment, implantable devices, food items (including meat or plant products), waste, traded goods, etc.
 *
 * Discussion: Manufactured or processed products are considered material, even if they originate as living matter.
 * Materials come in a wide variety of physical forms and can pass through different states (ie. Gas, liquid, solid)
 * while still retaining their physical composition and material characteristics.
 *
 * Rationale: There are entities that have attributes in addition to the Entity class, yet cannot be classified as
 * either LivingSubjectDAO or Place.
 *
 * @author daniel.
 */
public class Material extends Entity {

    protected String idMaterial;
    protected Code formCode;

    public Material() {
    }

    public Code getFormCode() {
        return formCode;
    }

    public void setFormCode(Code formCode) {
        this.formCode = formCode;
    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String id) {
        this.idMaterial = id;
    }
}
