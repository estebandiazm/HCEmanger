package com.hcemanager.sandbox;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.roles.LicensedEntity;
import com.hcemanager.models.roles.Patient;
import com.vaadin.server.VaadinServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by juan on 15/04/14.
 */
public class TestCaseRoles extends VaadinServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

;
        Patient patient = new Patient();

        //Role Codes
        Code classCode = new Code("Code_36","code-36","Codigo 36 classCode","Codigo test ","classCode");
        Code code = new Code("Code_31","code-31","codigo 31 code","Codigo test","code");
        Code confidentialityCode = new Code("Code_32","code-32 confidentialityCode","codigo 32","Codigo test","confidentialityCode");
        Code statusCode = new Code("Code_33","code-33","codigo 33 statusCode","Codigo test 33","statusCode");

//        Code code16 = new Code("Code_16","code-16","Codigo 16","Codigo tets acces 16","approachSiteCode");
//        Code code15 = new Code("Code_15","code-15","Codigo 15","Codigo tets acces 15","targetSiteCode");

//        //Employee Codes
//        Code code17 = new Code("Code_17","code-17","Codigo 17","Codigo test 17","jobCode");
//        Code code18 = new Code("Code_18","code-18","codigo 18","Codigo test 18","jobClassCode");
//        Code code19 = new Code("Code_19","code-19","codigo 19","Codigo test 19","occupationCode");
//        Code code20 = new Code("Code_20","code-20","codigo 20","Codigo test 20","salaryTypeCode");

        //Patient Codes

        Code veryImportantPersonCode = new Code("Code_35","code-34","Codigo 34 VIP","Codigo test 34","veryImportantPersonCode");

//        try {
//
//            SpringServices.getCodeDAO().insertCode(veryImportantPersonCode);
//            SpringServices.getCodeDAO().insertCode(classCode);
//
//        } catch (CodeExistsException e) {
//            e.printStackTrace();
//        }

        //Role

        patient.setId("Role_04");
        patient.setName("Role 4");
        patient.setQuantity("0008");
        patient.setPositionNumber("888");
        patient.setAddr("calle 111 num 01");
        patient.setCertificateText("Certificado del Role 8");
        patient.setTelecom("777888666777");
        patient.setEffectiveTime("08/08/08");
        patient.setNegationInd(false);

        patient.setClassCode(classCode);
        patient.setCode(code);
        patient.setConfidentialityCode(confidentialityCode);
        patient.setStatusCode(statusCode);


        //Access
//
//        access.setIdAccess("Access_2");
//        access.setGaugeQuantity("03");
//
//        access.setApproachSiteCode(code16);
//        access.setTargetSiteCode(code15);

        //Employee
//
//        employee.setIdEmployee("employee_01");
//        employee.setSalaryQuantity("10000");
//        employee.setJobTitleName("Titulo test employee");
//        employee.setHazardExposureText("hazzard test");
//        employee.setProtectiveEquipmentText("equipo de protexion estandar test");
//
//        employee.setJobCode(code17);
//        employee.setJobClassCode(code18);
//        employee.setOccupationCode(code19);
//        employee.setSalaryTypeCode(code20);

//        //LicensedEntity
//        patient.setIdLicensedEntity("LicensedEntity_1");
//        patient.setRecertificationTime("02/02/02");

        //Patient
        patient.setIdPatient("Patient_04");
        patient.setVeryImportantPersonCode(veryImportantPersonCode);



        try {
            SpringServices.getPatientDAO().insertPatient(patient);
        } catch (PatientExistsException e) {
            e.printStackTrace();
        } catch (RoleExistsException e) {
            e.printStackTrace();
        }

        try {
            List<Patient> patientsRes = SpringServices.getPatientDAO().getPatients();


            for (Patient patientRes:patientsRes){


                res.getWriter().write("Id: " + patientRes.getId());
                res.getWriter().write("\n");
                res.getWriter().write("Name: " + patientRes.getName());
                res.getWriter().write("\n");
                res.getWriter().write("Addr: " + patientRes.getAddr());
                res.getWriter().write("\n");
                res.getWriter().write("Quantity: " + patientRes.getQuantity());
                res.getWriter().write("\n");
                res.getWriter().write("Position Number: " + patientRes.getPositionNumber());
                res.getWriter().write("\n");
                res.getWriter().write("Certificate Text: " + patientRes.getCertificateText());
                res.getWriter().write("\n");
                res.getWriter().write("Telecom: " + patientRes.getTelecom() + "/n");
                res.getWriter().write("\n");
                res.getWriter().write("Effective Time: " + patientRes.getEffectiveTime());
                res.getWriter().write("\n");
                res.getWriter().write("NegationInd: " + patientRes.isNegationInd());
                res.getWriter().write("\n");
                //Codes
                res.getWriter().write("Class Code: " + patientRes.getClassCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Code: " + patientRes.getCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Confidentiality Code: " + patientRes.getConfidentialityCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Status Code: " + patientRes.getStatusCode().getPrintName());
                res.getWriter().write("\n");

//                res.getWriter().write("\n");
//                res.getWriter().write("Access id: "+licensedEntityRes.getIdAccess());
//                res.getWriter().write("\n");
//                res.getWriter().write("Gauge Quantity: "+licensedEntityRes.getGaugeQuantity());
//                res.getWriter().write("\n");
//                res.getWriter().write("Approach Site Code: "+licensedEntityRes.getApproachSiteCode().getPrintName());
//                res.getWriter().write("\n");
//                res.getWriter().write("Target Site Code: "+licensedEntityRes.getTargetSiteCode().getPrintName());

//                res.getWriter().write("Employee id: "+ licensedEntityRes.getIdEmployee());
//                res.getWriter().write("\n");
//                res.getWriter().write("Job Title: "+ licensedEntityRes.getJobTitleName());
//                res.getWriter().write("\n");
//                res.getWriter().write("Salary Quantity: "+ licensedEntityRes.getSalaryQuantity());
//                res.getWriter().write("\n");
//                res.getWriter().write("Hazard Text: "+ licensedEntityRes.getHazardExposureText());
//                res.getWriter().write("\n");
//                res.getWriter().write("Protective Equipament: "+ licensedEntityRes.getProtectiveEquipmentText());
//                res.getWriter().write("\n");
//                res.getWriter().write("Job Code: "+ licensedEntityRes.getJobCode().getPrintName());
//                res.getWriter().write("\n");
//                res.getWriter().write("Job Class Code: "+ licensedEntityRes.getJobClassCode().getPrintName());
//                res.getWriter().write("\n");
//                res.getWriter().write("Occupation Code: "+ licensedEntityRes.getOccupationCode().getPrintName());
//                res.getWriter().write("\n");
//                res.getWriter().write("Salary Type Code: " + licensedEntityRes.getSalaryTypeCode().getPrintName());

//                res.getWriter().write("\n");
//                res.getWriter().write("Licensed Entity: " + patientRes.getIdLicensedEntity());
//                res.getWriter().write("\n");
//                res.getWriter().write("Recertification Time: " + patientRes.getRecertificationTime());

                res.getWriter().write("\n");
                res.getWriter().write("Patient id: " + patientRes.getIdPatient());
                res.getWriter().write("\n");
                res.getWriter().write("VIP Code: " + patientRes.getVeryImportantPersonCode().getPrintName());



                res.getWriter().write("\n");
                res.getWriter().write("\n");
                res.getWriter().write("------------------------");
                res.getWriter().write("\n");
                res.getWriter().write("\n");
            }

        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        } catch (PatientNotExistsException e) {
            e.printStackTrace();
        }


    }
    }
