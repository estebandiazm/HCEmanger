package com.hcemanager.models.codes;

/**
 * @author daniel.
 */
public class Code {

    //------------------------------------------------------------------------------------------------------------------
    //Arguments
    //------------------------------------------------------------------------------------------------------------------

    private String id;
    private String mnemonic;
    private String printName;
    private String description;
    private String type;

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    public Code() {

    }

    /**
     * Default constructor
     * @param id
     * @param mnemonic
     * @param printName
     * @param description
     * @param type
     */
    public Code(String id, String mnemonic, String printName, String description, String type) {
        this.id=id;
        this.mnemonic= mnemonic;
        this.printName=printName;
        this.description=description;
        this.type=type;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    @Override
    public String toString(){
        return getPrintName();
    }
}
