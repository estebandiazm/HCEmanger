package com.hcemanager.views.adminModule;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.exceptions.users.UserExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.users.User;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class UserDetailPanel extends Panel implements Button.ClickListener{

    // User bean fields
    private TextField idUserTextField;
    private TextField userPasswordTextField;

    // Person bean fields
    private TextField idPersonTextField;
    private TextField addrTextField;
    private ComboBox maritalStatusCodeComboBoxComboBox;
    private ComboBox educationLevelCodeComboBox;
    private ComboBox raceCodeComboBox;
    private ComboBox disabilityCodeComboBox;
    private ComboBox livingArrangementCodeComboBox;
    private ComboBox religiousAffiliationCodeComboBox;
    private ComboBox ethnicGroupCodeComboBox;

    // LivingSubject bean fields
    private PopupDateField birthTimePopupDateField;
    private PopupDateField deceasedTimePopupDateField;
    private TextField multipleBirthOrderNumberTextField;
    private CheckBox deceasedIndCheckBox;
    private CheckBox multipleBirthIndCheckBox;
    private CheckBox organDonorIndCheckBox;
    private ComboBox administrativeGenderCodeComboBox;

    // Entity bean fields
    private TextField nameTextField;
    private TextField quantityTextField;
    private TextField descriptionTextField;
    private TextField telecomTextField;
    private PopupDateField existenceTimePopupDateField;
    private ComboBox classCodeComboBox;
    private ComboBox determinerCodeComboBox;
    private ComboBox codeComboBox;
    private ComboBox statusCodeComboBox;
    private ComboBox riskCodeComboBox;
    private ComboBox handlingCodeComboBox;

    private Button createButton;
    private Button updateButton;
    private Button editButton;
    private Button backButton;
    private Button nextButton;


    private HorizontalLayout sectionOneLayout;
    private HorizontalLayout sectionTwoLayout;
    private HorizontalLayout sectionThreeLayout;
    private VerticalLayout layout;

    private List<Code> codes;
    private Window window;
    private String userType;

    public UserDetailPanel(Window window, String userType){

        this.userType = userType;
        this.window=window;
        try {
            codes = SpringServices.getCodeDAO().getCodes();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }

        layout = new VerticalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setStyleName("details-buttons-layout");
        buttonLayout.setSizeFull();

        //initialization fields Entity bean
        nameTextField = new TextField("Nombre y Apellidos");
        quantityTextField = new TextField("Cantidad(Entity)");
        descriptionTextField = new TextField("Descripción(Entity)");
        telecomTextField = new TextField("Telefono");
        existenceTimePopupDateField = new PopupDateField("Tiempo de existencia");
        existenceTimePopupDateField.setDateFormat("dd/MM/yyyy");
        classCodeComboBox = new ComboBox("Código de clase");
        addItemsComboBox(classCodeComboBox,"classCode");
        determinerCodeComboBox = new ComboBox("Código determinador");
        addItemsComboBox(determinerCodeComboBox,"determinerCode");
        codeComboBox = new ComboBox("Código");
        addItemsComboBox(codeComboBox,"code");
        statusCodeComboBox = new ComboBox("Código de Status");
        addItemsComboBox(statusCodeComboBox,"statusCode");
        riskCodeComboBox = new ComboBox("Código de riesgo");
        addItemsComboBox(riskCodeComboBox,"riskCode");
        handlingCodeComboBox = new ComboBox("Código de manejo");
        addItemsComboBox(handlingCodeComboBox,"handlingCode");

        //Initialization fields LivingSubject bean
        birthTimePopupDateField = new PopupDateField("Fecha de nacimiento");
        birthTimePopupDateField.setDateFormat("dd/MM/yyyy");
        deceasedTimePopupDateField = new PopupDateField("Fecha de fallecimiento");
        deceasedTimePopupDateField.setDateFormat("dd/MM/yyyy");
        deceasedTimePopupDateField.setEnabled(false);
        multipleBirthOrderNumberTextField = new TextField("Orden de nacimiento");
        multipleBirthOrderNumberTextField.setEnabled(false);
        deceasedIndCheckBox = new CheckBox("Fallecido");
        deceasedIndCheckBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if ((Boolean)valueChangeEvent.getProperty().getValue()){
                    deceasedTimePopupDateField.setEnabled(true);
                }else{
                    deceasedTimePopupDateField.setEnabled(false);
                }
            }
        });
        multipleBirthIndCheckBox = new CheckBox("Hermanos");
        multipleBirthIndCheckBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if(Boolean.valueOf((Boolean) valueChangeEvent.getProperty().getValue())){
                    multipleBirthOrderNumberTextField.setEnabled(true);
                }else{
                    multipleBirthOrderNumberTextField.setEnabled(false);
                }
            }
        });
        organDonorIndCheckBox = new CheckBox("Donador");
        administrativeGenderCodeComboBox = new ComboBox("Género");
        addItemsComboBox(administrativeGenderCodeComboBox,"administrativeGenderCode");

        //Initialization fields Person bean
        idPersonTextField = new TextField("Documento de identidad");
        addrTextField = new TextField("Dirección");
        maritalStatusCodeComboBoxComboBox = new ComboBox("Estado marital");
        addItemsComboBox(maritalStatusCodeComboBoxComboBox,"maritalStatusCode");
        educationLevelCodeComboBox = new ComboBox("Nivel educativo");
        addItemsComboBox(educationLevelCodeComboBox,"educationLevelCode");
        raceCodeComboBox = new ComboBox("Código carrera");
        addItemsComboBox(raceCodeComboBox,"raceCode");
        disabilityCodeComboBox = new ComboBox("Código discapacidad");
        addItemsComboBox(disabilityCodeComboBox,"disabilityCode");
        livingArrangementCodeComboBox = new ComboBox("Código tipo de vivienda");
        addItemsComboBox(livingArrangementCodeComboBox,"livingArrangementCode");
        religiousAffiliationCodeComboBox = new ComboBox("Código religión");
        addItemsComboBox(religiousAffiliationCodeComboBox,"religiousAffiliationCode");
        ethnicGroupCodeComboBox = new ComboBox("Código grupo étnico");
        addItemsComboBox(ethnicGroupCodeComboBox,"ethnicGroupCode");

        //Initialization fields User bean
        idUserTextField = new TextField("Usuario");
        idUserTextField.addValidator( new StringLengthValidator("El username debe tener mínimo 8 caractéres",8,20,false));
        userPasswordTextField = new TextField("Contraseña");
        userPasswordTextField.addValidator( new StringLengthValidator("La contraseña debe tener mínimo 8 caractéres",8,20,false));


        createButton = new Button("Finalizar", FontAwesome.CHECK);
        createButton.setWidth(70.0f, Unit.PERCENTAGE);
        createButton.setEnabled(false);

        updateButton = new Button("Actualizar");
        updateButton.setWidth(70.0f,Unit.PERCENTAGE);
        updateButton.setEnabled(false);
        updateButton.setVisible(false);

        editButton = new Button("Editar");
        editButton.setWidth(70.0f,Unit.PERCENTAGE);
        editButton.setEnabled(false);
        editButton.setVisible(false);

        backButton = new Button("Atrás",FontAwesome.CHEVRON_LEFT);
        backButton.setWidth(70.0f, Unit.PERCENTAGE);
        backButton.setEnabled(false);

        nextButton = new Button("Siguiente", FontAwesome.CHEVRON_RIGHT);
        nextButton.setWidth(70.0f,Unit.PERCENTAGE);

        createButton.addClickListener(this);
        updateButton.addClickListener(this);
        editButton.addClickListener(this);
        backButton.addClickListener(this);
        nextButton.addClickListener(this);

        sectionOneLayout = buildSectionOne();
        sectionOneLayout.setId("sectionOne");
        sectionOneLayout.setMargin(true);

        sectionTwoLayout = buildSectionTwo();
        sectionTwoLayout.setId("sectionTwo");
        sectionTwoLayout.setMargin(true);

        sectionThreeLayout = buildSectionThree();
        sectionThreeLayout.setId("sectionThree");
        sectionThreeLayout.setMargin(true);

        buttonLayout.addComponent(backButton);
        buttonLayout.addComponent(createButton);
        buttonLayout.addComponent(updateButton);
        buttonLayout.addComponent(editButton);
        buttonLayout.addComponent(nextButton);

        layout.addComponent(sectionOneLayout);
        layout.addComponent(buttonLayout);
        layout.setMargin(true);

        setContent(layout);
        setCSS();
    }

    public UserDetailPanel(User user, Window window){
        this(window,user.getTypeUser());

        //Entity bean
        nameTextField.setValue(user.getName());
        nameTextField.setEnabled(false);

        quantityTextField.setValue(user.getQuantity());
        quantityTextField.setEnabled(false);

        descriptionTextField.setValue(user.getDescription());
        descriptionTextField.setEnabled(false);

        telecomTextField.setValue(user.getTelecom());
        telecomTextField.setEnabled(false);


        existenceTimePopupDateField.setValue(stringToDate(user.getExistenceTime()));
        existenceTimePopupDateField.setEnabled(false);

        setItemComboBox(classCodeComboBox,user.getClassCode());
        classCodeComboBox.setEnabled(false);

        setItemComboBox(determinerCodeComboBox,user.getDeterminerCode());
        determinerCodeComboBox.setEnabled(false);

        setItemComboBox(codeComboBox,user.getCode());
        codeComboBox.setEnabled(false);

        setItemComboBox(statusCodeComboBox,user.getStatusCode());
        statusCodeComboBox.setEnabled(false);

        setItemComboBox(riskCodeComboBox,user.getRiskCode());
        riskCodeComboBox.setEnabled(false);

        setItemComboBox(handlingCodeComboBox,user.getHandlingCode());
        handlingCodeComboBox.setEnabled(false);

        //Initialization fields LivingSubject bean
        birthTimePopupDateField.setValue(stringToDate(user.getBirthTime()));
        birthTimePopupDateField.setEnabled(false);
        deceasedTimePopupDateField.setValue(stringToDate(user.getDeceasedTime()));
        deceasedTimePopupDateField.setEnabled(false);
        multipleBirthOrderNumberTextField.setValue(String.valueOf(user.getMultipleBirthOrderNumber()));
        multipleBirthOrderNumberTextField.setEnabled(false);
        deceasedIndCheckBox.setValue(user.isDeceasedInd());
        deceasedIndCheckBox.setEnabled(false);
        multipleBirthIndCheckBox.setValue(user.isMultipleBirthInd());
        multipleBirthIndCheckBox.setEnabled(false);
        organDonorIndCheckBox.setValue(user.isOrganDonorInd());
        organDonorIndCheckBox.setEnabled(false);
        setItemComboBox(administrativeGenderCodeComboBox,user.getAdministrativeGenderCode());
        administrativeGenderCodeComboBox.setEnabled(false);

        //Initialization fields Person bean
        idPersonTextField.setValue(user.getIdPerson());
        idPersonTextField.setEnabled(false);
        addrTextField.setValue(user.getAddr());
        addrTextField.setEnabled(false);
        setItemComboBox(maritalStatusCodeComboBoxComboBox,user.getMaritalStatusCode());
        maritalStatusCodeComboBoxComboBox.setEnabled(false);
        setItemComboBox(educationLevelCodeComboBox,user.getEducationLevelCode());
        educationLevelCodeComboBox.setEnabled(false);
        setItemComboBox(raceCodeComboBox,user.getRaceCode());
        raceCodeComboBox.setEnabled(false);
        setItemComboBox(disabilityCodeComboBox,user.getDisabilityCode());
        disabilityCodeComboBox.setEnabled(false);
        setItemComboBox(livingArrangementCodeComboBox,user.getLivingArrangementCode());
        livingArrangementCodeComboBox.setEnabled(false);
        setItemComboBox(religiousAffiliationCodeComboBox,user.getReligiousAffiliationCode());
        religiousAffiliationCodeComboBox.setEnabled(false);
        setItemComboBox(ethnicGroupCodeComboBox,user.getEthnicGroupCode());
        ethnicGroupCodeComboBox.setEnabled(false);

        //Initialization fields User bean
        idUserTextField.setValue(user.getIdUser());
        idUserTextField.setEnabled(false);
        userPasswordTextField.setValue(user.getPassword());
        userPasswordTextField.setEnabled(false);

        createButton.setEnabled(false);
        createButton.setVisible(false);
        editButton.setEnabled(true);
        editButton.setVisible(true);


    }

    /**
     *method that add items Code into comboBox
     * @param comboBox
     * @param type
     */
    private void addItemsComboBox(ComboBox comboBox, String type){
        for (Code code:codes){
            if (code.getType().equals(type)){
                comboBox.addItem(code);
            }
        }
    }

    private void setItemComboBox(ComboBox comboBox,Code code){
        Collection<Code> ids= (Collection<Code>) comboBox.getItemIds();
        for (Code item:ids){
            if (item.getPrintName().equals(code.getPrintName())){
                comboBox.setValue(item);
                break;
            }
        }
    }

    private void setEnabledAllComponents(){
        // User bean fields
        idUserTextField.setEnabled(true);
        userPasswordTextField.setEnabled(true);

        // Person bean fields
        idPersonTextField.setEnabled(true);
        addrTextField.setEnabled(true);
        maritalStatusCodeComboBoxComboBox.setEnabled(true);
        educationLevelCodeComboBox.setEnabled(true);
        raceCodeComboBox.setEnabled(true);
        disabilityCodeComboBox.setEnabled(true);
        livingArrangementCodeComboBox.setEnabled(true);
        religiousAffiliationCodeComboBox.setEnabled(true);
        ethnicGroupCodeComboBox.setEnabled(true);

        // LivingSubject bean fields
        birthTimePopupDateField.setEnabled(true);
        deceasedTimePopupDateField.setEnabled(true);
        multipleBirthOrderNumberTextField.setEnabled(true);
        deceasedIndCheckBox.setEnabled(true);
        multipleBirthIndCheckBox.setEnabled(true);
        organDonorIndCheckBox.setEnabled(true);
        administrativeGenderCodeComboBox.setEnabled(true);

        // Entity bean fields
        nameTextField.setEnabled(true);
        quantityTextField.setEnabled(true);
        descriptionTextField.setEnabled(true);
        telecomTextField.setEnabled(true);
        existenceTimePopupDateField.setEnabled(true);
        classCodeComboBox.setEnabled(true);
        determinerCodeComboBox.setEnabled(true);
        codeComboBox.setEnabled(true);
        statusCodeComboBox.setEnabled(true);
        riskCodeComboBox.setEnabled(true);
        handlingCodeComboBox.setEnabled(true);

    }

    public HorizontalLayout buildSectionOne (){

        FormLayout formLayout1 = new FormLayout();
        FormLayout formLayout2 = new FormLayout();

        formLayout1.addComponent(nameTextField);
        formLayout1.addComponent(telecomTextField);
        formLayout1.addComponent(quantityTextField);
        formLayout1.addComponent(existenceTimePopupDateField);
        formLayout1.addComponent(classCodeComboBox);
        formLayout2.addComponent(determinerCodeComboBox);
        formLayout2.addComponent(codeComboBox);
        formLayout2.addComponent(statusCodeComboBox);
        formLayout2.addComponent(riskCodeComboBox);
        formLayout2.addComponent(descriptionTextField);

        return new HorizontalLayout(formLayout1,formLayout2);
    }

    public HorizontalLayout buildSectionTwo(){

        FormLayout formLayout1 = new FormLayout();
        FormLayout formLayout2 = new FormLayout();

        formLayout1.addComponent(idPersonTextField);
        formLayout1.addComponent(addrTextField);
        formLayout1.addComponent(administrativeGenderCodeComboBox);
        formLayout1.addComponent(birthTimePopupDateField);
        formLayout1.addComponent(deceasedIndCheckBox);
        formLayout2.addComponent(deceasedTimePopupDateField);
        formLayout2.addComponent(multipleBirthIndCheckBox);
        formLayout2.addComponent(multipleBirthOrderNumberTextField);
        formLayout2.addComponent(organDonorIndCheckBox);
        formLayout2.addComponent(handlingCodeComboBox);

        return new HorizontalLayout(formLayout1,formLayout2);
    }

    public HorizontalLayout buildSectionThree(){

        FormLayout formLayout1 = new FormLayout();
        FormLayout formLayout2 = new FormLayout();

        formLayout1.addComponent(maritalStatusCodeComboBoxComboBox);
        formLayout1.addComponent(educationLevelCodeComboBox);
        formLayout1.addComponent(raceCodeComboBox);
        formLayout1.addComponent(disabilityCodeComboBox);
        formLayout1.addComponent(livingArrangementCodeComboBox);
        formLayout2.addComponent(religiousAffiliationCodeComboBox);
        formLayout2.addComponent(ethnicGroupCodeComboBox);
        formLayout2.addComponent(idUserTextField);
        formLayout2.addComponent(userPasswordTextField);

        return new HorizontalLayout(formLayout1,formLayout2);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String event = clickEvent.getButton().getCaption();
        String currentSection = layout.getComponent(0).getId();

        if (event.equals(backButton.getCaption())){
            if (currentSection.equals(sectionTwoLayout.getId())) {
                layout.removeComponent(layout.getComponent(0));
                layout.addComponent(sectionOneLayout,0);
                backButton.setEnabled(false);
            }
            if (currentSection.equals(sectionThreeLayout.getId())){
                layout.removeComponent(layout.getComponent(0));
                layout.addComponent(sectionTwoLayout,0);
                nextButton.setEnabled(true);
                createButton.setEnabled(false);
            }
        }
        if (event.equals(nextButton.getCaption())){
            if (currentSection.equals(sectionOneLayout.getId())){
                layout.removeComponent(layout.getComponent(0));
                layout.addComponent(sectionTwoLayout,0);
                backButton.setEnabled(true);
            }
            if (currentSection.equals(sectionTwoLayout.getId())){
                layout.removeComponent(layout.getComponent(0));
                layout.addComponent(sectionThreeLayout, 0);
                createButton.setEnabled(true);
                updateButton.setEnabled(true);
                nextButton.setEnabled(false);
            }
        }
        if (event.equals(createButton.getCaption())){
            try {
                addUser("insert");
                window.close();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (UserExistsException e) {
                Notification.show("El usuario ya existe", Notification.Type.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (LivingSubjectExistsException e) {
                Notification.show("El usuario ya existe", Notification.Type.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (PersonExistException e) {
                Notification.show("El usuario ya existe", Notification.Type.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (EntityExistsException e) {
                Notification.show("El usuario ya existe", Notification.Type.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (LivingSubjectNotExistsException e) {
                e.printStackTrace();
            } catch (CodeExistsException e) {
                e.printStackTrace();
            } catch (PersonNotExistException e) {
                e.printStackTrace();
            } catch (CodeNotExistsException e) {
                e.printStackTrace();
            } catch (UserNotExistsException e) {
                e.printStackTrace();
            } catch (EntityNotExistsException e) {
                e.printStackTrace();
            }
        }
        if (event.equals(updateButton.getCaption())){
            try {
                addUser("update");
                window.close();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (UserExistsException e) {
                e.printStackTrace();
            } catch (LivingSubjectExistsException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (PersonExistException e) {
                e.printStackTrace();
            } catch (EntityExistsException e) {
                e.printStackTrace();
            } catch (LivingSubjectNotExistsException e) {
                e.printStackTrace();
            } catch (CodeExistsException e) {
                e.printStackTrace();
            } catch (UserNotExistsException e) {
                e.printStackTrace();
            } catch (CodeNotExistsException e) {
                e.printStackTrace();
            } catch (PersonNotExistException e) {
                e.printStackTrace();
            } catch (EntityNotExistsException e) {
                e.printStackTrace();
            }
        }
        if (event.equals(editButton.getCaption())){
            createButton.setVisible(false);
            editButton.setVisible(false);
            updateButton.setEnabled(true);
            updateButton.setVisible(true);
            setEnabledAllComponents();
        }
    }

    public void addUser(String mode) throws NoSuchPaddingException, UserExistsException, LivingSubjectExistsException, NoSuchAlgorithmException, InvalidKeyException, PersonExistException, EntityExistsException, LivingSubjectNotExistsException, CodeExistsException, UserNotExistsException, CodeNotExistsException, PersonNotExistException, EntityNotExistsException {

        User user = new User();


        // User bean fields
        user.setIdUser(idUserTextField.getValue());
        user.setPassword(userPasswordTextField.getValue());

        // Person bean fields
        user.setIdPerson(idPersonTextField.getValue());
        user.setAddr(addrTextField.getValue());
        user.setMaritalStatusCode((Code)maritalStatusCodeComboBoxComboBox.getValue());
        user.setEducationLevelCode((Code)educationLevelCodeComboBox.getValue());
        user.setRaceCode((Code)raceCodeComboBox.getValue());
        user.setDisabilityCode((Code) disabilityCodeComboBox.getValue());
        user.setLivingArrangementCode((Code) livingArrangementCodeComboBox.getValue());
        user.setReligiousAffiliationCode((Code) religiousAffiliationCodeComboBox.getValue());
        user.setEthnicGroupCode((Code) ethnicGroupCodeComboBox.getValue());

        // LivingSubject bean fields
        user.setBirthTime(dateToString(birthTimePopupDateField.getValue()));
        String result = (deceasedTimePopupDateField.getValue()==null)?"":dateToString(deceasedTimePopupDateField.getValue());
        user.setDeceasedTime(result);
        result = (multipleBirthOrderNumberTextField.getValue().equals(""))?"0":multipleBirthOrderNumberTextField.getValue();
        user.setMultipleBirthOrderNumber(Integer.parseInt(result));
        user.setDeceasedInd(deceasedIndCheckBox.getValue());
        user.setMultipleBirthInd(multipleBirthIndCheckBox.getValue());
        user.setOrganDonorInd(organDonorIndCheckBox.getValue());
        user.setAdministrativeGenderCode((Code) administrativeGenderCodeComboBox.getValue());

        // Entity bean fields
        user.setName(nameTextField.getValue());
        user.setQuantity(quantityTextField.getValue());
        user.setDescription(descriptionTextField.getValue());
        user.setTelecom(telecomTextField.getValue());
        user.setExistenceTime(dateToString(existenceTimePopupDateField.getValue()));
        user.setClassCode((Code) classCodeComboBox.getValue());
        user.setDeterminerCode((Code) determinerCodeComboBox.getValue());
        user.setCode((Code) codeComboBox.getValue());
        user.setStatusCode((Code) statusCodeComboBox.getValue());
        user.setRiskCode((Code) riskCodeComboBox.getValue());
        user.setHandlingCode((Code) handlingCodeComboBox.getValue());

        //id
        user.setId(idPersonTextField.getValue());
        user.setIdLivingSubject(idPersonTextField.getValue());
        user.setTypeUser(userType);


        if (mode.equals("insert")){
            System.out.println("Inserta");
            SpringServices.getUserDAO().insertUser(user);
        }else if (mode.equals("update")){
            System.out.println("actualiza");
            SpringServices.getUserDAO().updateUser(user);
        }

    }

    private Date stringToDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date dateTemp = null;
        try {
            dateTemp= format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTemp;
    }

    private String dateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }

    public void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".details-buttons-layout{margin-left:5% !important; margin-bottom :2% !important;}");
    }
}