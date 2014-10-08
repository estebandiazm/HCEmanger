package com.hcemanager.views.generalUI;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.exceptions.users.UserExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.users.User;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import javax.crypto.NoSuchPaddingException;
import javax.swing.text.Style;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class ChangeDataUserWindow extends Window implements Button.ClickListener{

    private TextField telecomTextField;
    private TextField nameTextField;
    private TextField addrTextField;
    private ComboBox maritalStatusCodeComoBox;
    private Button changeButton;
    private List<Code> codesList;
    private User user;

    public ChangeDataUserWindow(String username){
        getUser(username);
        codesList = getCodesList();

        setCaption("Cambiar información");
        setModal(true);
        setResizable(false);
        setContent(buildChangeDataUserPanel());
        setCSS();
        setWidth(28.0f,Unit.PERCENTAGE);
    }

    private Panel buildChangeDataUserPanel(){
        Panel changeDataUserPanel = new Panel();
        FormLayout formLayout = new FormLayout();
        formLayout.setStyleName("change-data-layout");
        HorizontalLayout layout = new HorizontalLayout();

        nameTextField = new TextField("Nombre");
        nameTextField.setValue(user.getName());
        telecomTextField = new TextField("Teléfono");
        telecomTextField.setValue(user.getTelecom());
        addrTextField = new TextField("Dirección");
        addrTextField.setValue(user.getAddr());
        maritalStatusCodeComoBox = new ComboBox("Estado civil");
        addItemsComboBox(maritalStatusCodeComoBox, "maritalStatusCode");
        setItemComboBox(maritalStatusCodeComoBox,user.getMaritalStatusCode());
        changeButton = new Button("Cambiar", FontAwesome.CHECK);
        changeButton.addClickListener(this);
        changeButton.setWidth(100.0f,Unit.PERCENTAGE);
        changeButton.setStyleName("change-data-button");

        formLayout.addComponent(nameTextField);
        formLayout.addComponent(addrTextField);
        formLayout.addComponent(telecomTextField);
        formLayout.addComponent(maritalStatusCodeComoBox);
        formLayout.addComponent(changeButton);

        layout.addComponent(formLayout);
        layout.setMargin(true);
        changeDataUserPanel.setContent(layout);

        return changeDataUserPanel;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String event = clickEvent.getButton().getCaption();
        if (event.equals(changeButton.getCaption())){
            user.setName(nameTextField.getValue());
            user.setTelecom(telecomTextField.getValue());
            user.setAddr(addrTextField.getValue());
            user.setMaritalStatusCode((Code) maritalStatusCodeComoBox.getValue());

            try {
                SpringServices.getUserDAO().updateUser(user);
                close();
            } catch (UserExistsException e) {
                e.printStackTrace();
            } catch (EntityExistsException e) {
                e.printStackTrace();
            } catch (LivingSubjectExistsException e) {
                e.printStackTrace();
            } catch (CodeExistsException e) {
                e.printStackTrace();
            } catch (LivingSubjectNotExistsException e) {
                e.printStackTrace();
            } catch (PersonExistException e) {
                e.printStackTrace();
            } catch (CodeNotExistsException e) {
                e.printStackTrace();
            } catch (PersonNotExistException e) {
                e.printStackTrace();
            } catch (EntityNotExistsException e) {
                e.printStackTrace();
            } catch (UserNotExistsException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Code> getCodesList(){
        try {
            return SpringServices.getCodeDAO().getCodes();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void addItemsComboBox(ComboBox comboBox, String type){
        for (Code code:codesList){
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

    private void getUser(String username){
        try {
            user = SpringServices.getUserDAO().getUser(username);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }
    }

    private void setCSS(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".change-data-layout{margin: 8% !important}" +
                ".change-data-button{width:100% !important;}");
    }
}
