package com.hcemanager.utils;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentNotExistsException;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.Entity;
import com.hcemanager.models.entities.LivingSubject;
import com.hcemanager.models.entities.Person;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.hcemanager.models.users.User;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.jdom2.Document;
import org.jdom2.output.*;
import org.jdom2.Element;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class XMLGenerator {

    private Class classUser;
    private Field fieldsUser[];
    private Field atriibutesMedicalAppointment[];
    private List<MedicalAppointment> medicalAppointments;


    public String writeXML(User user){

        medicalAppointments=getMedicalAppointments(user);

        System.out.println(user.getClass());
        Field[] fieldsUser = user.getClass().getDeclaredFields();
        Field[] fieldsPerson=user.getClass().getSuperclass().getDeclaredFields();
        Field[] fieldsLivingSubject=user.getClass().getSuperclass().getSuperclass().getDeclaredFields();
        Field[] fieldsEntity=user.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredFields();


        //Se asigna la raiz del documento
        Element root = new Element("ClinicalDocument");



        setTagsEntity(fieldsEntity, user, root);
        setTagsLivingSubject(fieldsLivingSubject, user, root);
        setTagsPerson(fieldsPerson, user,root);
        setTagsUser(fieldsUser, user, root);

        Element componentBody = new Element("component");
        componentBody.setAttribute("type","BODY");

        for (MedicalAppointment medicalAppointment:medicalAppointments){
            Element medicalAppointmentComponent = new Element("section");
            medicalAppointmentComponent.setAttribute("type", "CONSULT");
            setTagsMedicalAppointment(medicalAppointment.getClass().getDeclaredFields(), medicalAppointmentComponent, medicalAppointment);
            componentBody.addContent(medicalAppointmentComponent);
        }

        root.addContent(componentBody);


        //Se escribe el archivo
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

        final String  BASE_PATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/xml/"+user.getIdUser()+".xml";
        File file = new File(BASE_PATH);

        try {
            outputter.output(new Document(root), new FileOutputStream(BASE_PATH));
        }catch (Exception e ){
            e.getMessage();
        }

        return BASE_PATH;
    }

    private List<MedicalAppointment> getMedicalAppointments(User user){
        List<MedicalAppointment> medicalAppointmentsByUser=null;
        try {
           medicalAppointmentsByUser = SpringServices.getMedicalAppointmentDAO().getMedicalAppointmentsByUser(user.getIdUser());
        } catch (MedicalAppointmentNotExistsException e) {
            e.printStackTrace();
        }
        return medicalAppointmentsByUser;
    }

    private void setTagsUser(Field[] fields,User user,Element component){
        for (Field field:fields){
            field.setAccessible(true);

            Element tag = new Element(field.getName());
            String data = null;
            try {
                if (field.getType().getSimpleName().equals("String")){
                    data = (String)field.get(user);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            tag.setText(data);

            component.addContent(tag);
        }
    }

    private void setTagsMedicalAppointment(Field[] fields, Element component, MedicalAppointment medicalAppointment){
        for (Field field:fields){
            field.setAccessible(true);

            Element tag = new Element(field.getName());
            String data = null;
            try {
                if(field.getType().getSimpleName().equals("User")){
                    data = (String) ((User)field.get(medicalAppointment)).getName();
                }else {
                    data = (String)String.valueOf(field.get(medicalAppointment));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            tag.setText(data);

            component.addContent(tag);
        }
    }

    private void setTagsPerson(Field[] fields,User user,Element component){
        for (Field field:fields){
            field.setAccessible(true);

            Element tag = new Element(field.getName());
            String data = null;
            try {

                if (field.getType().getSimpleName().equals("Code")){
                    tag.setAttribute("type","Code");
                    tag.setAttribute("mnemonic",((Code)field.get(user)).getMnemonic());
                    data = ((Code)field.get(user)).getPrintName();
                }else {
                    data = String.valueOf(field.get((Person)user));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            tag.setText(data);

            component.addContent(tag);
        }
    }

    private void setTagsLivingSubject(Field[] fields,User user,Element component){
        for (Field field:fields){
            field.setAccessible(true);

            Element tag = new Element(field.getName());
            String data = null;

            if (field.getType().getSimpleName().equals("String")){
                try {
                    if (field.getType().getSimpleName().equals("Code")){
                        tag.setAttribute("type","Code");
                        tag.setAttribute("mnemonic",((Code)field.get(user)).getMnemonic());
                        data = ((Code)field.get(user)).getPrintName();
                    }else {
                        data = String.valueOf(field.get((LivingSubject)user));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            tag.setText(data);

            component.addContent(tag);
        }
    }

    private void setTagsEntity(Field[] fields,User user,Element component){
        for (Field field:fields){
            field.setAccessible(true);

            Element tag = new Element(field.getName());
            String data = null;
            try {
                if (field.getType().getSimpleName().equals("Code")){
                    tag.setAttribute("type","Code");
                    tag.setAttribute("mnemonic",((Code)field.get(user)).getMnemonic());
                    data = ((Code)field.get(user)).getPrintName();
                }else{
                    data = String.valueOf(field.get((Entity)user).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            tag.setText(data);

            component.addContent(tag);
        }
    }
}
