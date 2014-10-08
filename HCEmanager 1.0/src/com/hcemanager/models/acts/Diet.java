package com.hcemanager.models.acts;

/**
 * A supply act dealing specifically with the feeding or nourishment of a subject.
 *
 * Discussion: The detail of the diet is given as a description of the MaterialDAO associated via Participation
 * .typeCode="product". Medically relevant diet types may be communicated in the Diet.code, however, the detail
 * of the food supplied and the various combinations of dishes should be communicated as MaterialDAO instances.
 *
 * Examples: Gluten free; Low sodium
 *
 * @author daniel.
 */
public class Diet extends Supply {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private String energyQuantity;
    private String carbohydrateQuantity;
    private String idDiet;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public Diet() {
    }

    public String getEnergyQuantity() {
        return energyQuantity;
    }

    public void setEnergyQuantity(String energyQuantity) {
        this.energyQuantity = energyQuantity;
    }

    public String getCarbohydrateQuantity() {
        return carbohydrateQuantity;
    }

    public void setCarbohydrateQuantity(String carbohydrateQuantity) {
        this.carbohydrateQuantity = carbohydrateQuantity;
    }

    public String getIdDiet() {
        return idDiet;
    }

    public void setIdDiet(String idDiet) {
        this.idDiet = idDiet;
    }
}
