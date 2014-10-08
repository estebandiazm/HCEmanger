package com.hcemanager.sandbox;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.connectors.ParticipationNotExistsException;
import com.hcemanager.exceptions.connectors.RoleHasEntityNotExistsException;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentNotExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.connectors.Participation;
import com.hcemanager.models.connectors.RoleHasEntity;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import com.hcemanager.models.users.User;
import com.vaadin.server.VaadinServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by juan on 23/04/14.
 */
public class TestCaseEntity extends VaadinServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        User user = new User();
        MedicalAppointment medicalAppointment = new MedicalAppointment();

        //Entity Codes
        Code classCode = new Code("Code_22","code-22","Codigo 22 ClassCode","Codigo 22 test","classCode");
        Code determinerCode = new Code("Code_02","code-02","Codigo 02 DeterminerCode","Codigo 02 test","determinerCode");
        Code code = new Code("Code_03","code-03","Codigo 03 code","Codigo 03 test","code");
        Code statusCode = new Code("Code_04","code-04","Codigo 04 statusCode","Codigo 04 test","statusCode");
        Code riskCode = new Code("Code_17","code-17","Codigo 17 riskCode","Codigo 17 test","riskCode");
        Code handlingCode = new Code("Code_06","code-06","Codigo 06 handlingCode","Codigo 06 test","handlingCode");
        //LivingSubject code
        Code administrativeGenderCode = new Code("Code_37","code-37","codigo 37 administrativeGenderCode","codigo 37 test","administrativeGenderCode");
        //person codes
        Code maritalStatusCode = new Code("Code_38","code-38","codigo 38 maritalStatusCode","codigo 38 test","maritalStatusCode");
        Code educationLevelCode = new Code("Code_39","code-39","codigo 39 educationLevelCode","codigo 39 test","educationLevelCode");
        Code raceCode = new Code("Code_40","code-40","codigo 40 raceCode","codigo 40 test","raceCode");
        Code disabilityCode = new Code("Code_41","code-41","codigo 41 disabilityCode","codigo 41 test","disabilityCode");
        Code livingArrangementCode = new Code("Code_42","code-42","codigo 42 livingArrangementCode","codigo 42 test","livingArrangementCode");
        Code religiousAffiliationCode = new Code("Code_43","code-43","codigo 43 religiousAffiliationCode","codigo 43 test","religiousAffiliationCode");
        Code ethnicGroupCode = new Code("Code_44","code-44","codigo 44 ethnicGroupCode","codigo 44 test","ethnicGroupCode");





//        try {
//
//            SpringServices.getCodeDAO().insertCode(administrativeGenderCode);
//            SpringServices.getCodeDAO().insertCode(maritalStatusCode);
//            SpringServices.getCodeDAO().insertCode(educationLevelCode);
//            SpringServices.getCodeDAO().insertCode(raceCode);
//            SpringServices.getCodeDAO().insertCode(disabilityCode);
//            SpringServices.getCodeDAO().insertCode(livingArrangementCode);
//            SpringServices.getCodeDAO().insertCode(religiousAffiliationCode);
//            SpringServices.getCodeDAO().insertCode(ethnicGroupCode);
//        } catch (CodeExistsException e) {
//            e.printStackTrace();
//        }


        //Attributes entity
        user.setClassCode(classCode);
        user.setDeterminerCode(determinerCode);
        user.setCode(code);
        user.setStatusCode(statusCode);
        user.setRiskCode(riskCode);
        user.setHandlingCode(handlingCode);
        user.setId("Entity_22");
        user.setName("Entity name 22");
        user.setQuantity("019");
        user.setDescription("Description entity 22");
        user.setExistenceTime("9/9/9");
        user.setTelecom("99999999");

        //Attributes LivingSubject
        user.setIdLivingSubject("LivingSubject_08");
        user.setDeceasedInd(false);
        user.setBirthTime("01/01/01");
        user.setDeceasedTime("");
        user.setMultipleBirthInd(true);
        user.setMultipleBirthOrderNumber(2);
        user.setOrganDonorInd(false);
        user.setAdministrativeGenderCode(administrativeGenderCode);

        //Attributes Person
        user.setIdPerson("Person_07");
        user.setAddr("addr person 07");
        user.setMaritalStatusCode(maritalStatusCode);
        user.setEducationLevelCode(educationLevelCode);
        user.setRaceCode(raceCode);
        user.setDisabilityCode(disabilityCode);
        user.setLivingArrangementCode(livingArrangementCode);
        user.setReligiousAffiliationCode(religiousAffiliationCode);
        user.setEthnicGroupCode(ethnicGroupCode);

        //Attributes User
        user.setIdUser("User_02");
        user.setPassword("passwordtest_02");

        medicalAppointment.setDate("01/01/01");
        medicalAppointment.setWeight("90kg");
        medicalAppointment.setSize("180m");
        medicalAppointment.setBloodPressure("10/89");
        medicalAppointment.setTemperature("29");
        medicalAppointment.setPulse("100");
        medicalAppointment.setFamilyHistory("antecedentes familiares");
        medicalAppointment.setPersonalHistory("Antecedentes personales");
        medicalAppointment.setHabits("habitos");
        medicalAppointment.setQueryReason("dolor de kernel");

//        try {
//            medicalAppointment.setPatient(SpringServices.getUserDAO().getUser("User_03"));
//            medicalAppointment.setDoctor(SpringServices.getUserDAO().getUser("User_02"));
//        } catch (UserNotExistsException e) {
//            e.printStackTrace();
//        } catch (CodeNotExistsException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            SpringServices.getMedicalAppointmentDAO().insertMedicalAppointment(medicalAppointment);
//        } catch (MedicalAppointmentExistsException e) {
//            e.printStackTrace();
//        }
//



//        try {
//            SpringServices.getUserDAO().insertUser(user);
//        } catch (EntityExistsException e) {
//            e.printStackTrace();
//        } catch (LivingSubjectExistsException e) {
//            e.printStackTrace();
//        } catch (UserExistsException e) {
//            e.printStackTrace();
//        } catch (PersonExistException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        }

        Participation participation = new Participation();
        participation.setIdParticipation("Participation_02");
        participation.setNegationInd(true);
        participation.setNoteText("Nota participation01");
        participation.setTime("00/00/00");
        participation.setIdRole("Role_02");
        participation.setIdAct("Act_01");
        participation.setPerformInd(true);
        participation.setSequenceNumber("1");
        participation.setSignatureText("sigantures text ");

        RoleHasEntity roleHasEntity = new RoleHasEntity();
        roleHasEntity.setIdEntity("Entity_02");
        roleHasEntity.setIdRole("Role_02");

//        try {
//            SpringServices.getParticipationDAO().insertParticipation(participation);
//            SpringServices.getRoleHasEntityDAO().insertRoleHasEntity(roleHasEntity);
//        } catch (RoleHasEntityExistsException e) {
//            e.printStackTrace();
//        } catch (ParticipationExistsException e) {
//            e.printStackTrace();
//        }

        try {
            List<User> usersRes = SpringServices.getUserDAO().getUsers();
            List<MedicalAppointment> medicalAppointmentsRes = SpringServices.getMedicalAppointmentDAO().getMedicalAppointments();
            List<Participation> participationsRes = SpringServices.getParticipationDAO().getParticipations();
            List<RoleHasEntity> rolesHasEntitiesRes = SpringServices.getRoleHasEntityDAO().getRolesHasEntities();


            for (User userRes : usersRes){

                res.getWriter().write("Entity id: " + userRes.getId());
                res.getWriter().write("\n");
                res.getWriter().write("Entity name: " + userRes.getName());
                res.getWriter().write("\n");
                res.getWriter().write("Entity Quantity: " + userRes.getQuantity());
                res.getWriter().write("\n");
                res.getWriter().write("Entity Description: " + userRes.getDescription());
                res.getWriter().write("\n");
                res.getWriter().write("Entity Existence Time: " + userRes.getExistenceTime());
                res.getWriter().write("\n");
                res.getWriter().write("Entity Telecom: " + userRes.getTelecom());
                res.getWriter().write("\n");
                res.getWriter().write("Entity ClassCode: " + userRes.getClassCode().getType());
                res.getWriter().write("\n");
                res.getWriter().write("Entity DeterminerCode: " + userRes.getDeterminerCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Entity Code: " + userRes.getCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Entity StatusCode: " + userRes.getStatusCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Entity RiskCode: " + userRes.getRiskCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Entity HandlingCode: " + userRes.getHandlingCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("\n");

                //LivingSubject

                res.getWriter().write("LivingSubject id: "+userRes.getIdLivingSubject());
                res.getWriter().write("\n");
                res.getWriter().write("LivingSubject deceased time: "+userRes.getDeceasedTime());
                res.getWriter().write("\n");
                res.getWriter().write("LivingSubject deceased ind: "+userRes.isDeceasedInd());
                res.getWriter().write("\n");
                res.getWriter().write("LivingSubject birth time: "+userRes.getBirthTime());
                res.getWriter().write("\n");
                res.getWriter().write("LivingSubject Multibirth ind: "+userRes.isMultipleBirthInd());
                res.getWriter().write("\n");
                res.getWriter().write("LivingSubject Multibirth order: "+userRes.getMultipleBirthOrderNumber());
                res.getWriter().write("\n");
                res.getWriter().write("LivingSubject organ ind: "+userRes.isOrganDonorInd());
                res.getWriter().write("\n");
                res.getWriter().write("LivingSubject administrate gender code: "+userRes.getAdministrativeGenderCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("\n");

                res.getWriter().write("Person id: "+userRes.getIdPerson());
                res.getWriter().write("\n");
                res.getWriter().write("Person addr: "+userRes.getAddr());
                res.getWriter().write("\n");
                res.getWriter().write("Person maritalStatusCode: "+userRes.getMaritalStatusCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Person education level code: "+userRes.getEducationLevelCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Person raceCode "+userRes.getEducationLevelCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Person disability code "+userRes.getDisabilityCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Person disability code "+userRes.getDisabilityCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Person living arr code"+userRes.getDisabilityCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Person religious code"+userRes.getReligiousAffiliationCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("Person ethnic code"+userRes.getEthnicGroupCode().getPrintName());
                res.getWriter().write("\n");
                res.getWriter().write("\n");

                res.getWriter().write("User  id: "+userRes.getIdUser());
                res.getWriter().write("\n");
                res.getWriter().write("User  password: "+userRes.getPassword());
                res.getWriter().write("\n");
                res.getWriter().write("\n");

                res.getWriter().write("--------------------------------------------------------------------------------");
                res.getWriter().write("\n");
                res.getWriter().write("\n");
            }

            res.getWriter().write("-----------------------------Consultas---------------------------------------------");

            for (MedicalAppointment medicalAppointmentRes:medicalAppointmentsRes){
                res.getWriter().write("\n");
                res.getWriter().write("\n");
                res.getWriter().write("id consult: "+medicalAppointmentRes.getIdConsult());
                res.getWriter().write("\n");
                res.getWriter().write("date: "+medicalAppointmentRes.getDate());
                res.getWriter().write("\n");
                res.getWriter().write("weight: "+medicalAppointmentRes.getWeight());
                res.getWriter().write("\n");
                res.getWriter().write("size: "+medicalAppointmentRes.getSize());
                res.getWriter().write("\n");
                res.getWriter().write("personal history: "+medicalAppointmentRes.getPersonalHistory());
                res.getWriter().write("\n");
                res.getWriter().write("family history: "+medicalAppointmentRes.getFamilyHistory());
                res.getWriter().write("\n");
                res.getWriter().write("patient: "+medicalAppointmentRes.getPatient().getIdUser());
                res.getWriter().write("\n");
                res.getWriter().write("doctor: "+medicalAppointmentRes.getDoctor().getIdUser());
                res.getWriter().write("\n");
                res.getWriter().write("\n");
            }

            res.getWriter().write("-----------------------------Participations and RolesHasEntities--------------------");
            res.getWriter().write("\n");
            res.getWriter().write("\n");
            for (Participation participationRes:participationsRes){
                res.getWriter().write("\n");
                res.getWriter().write("id participation: "+participationRes.getIdParticipation());
                res.getWriter().write("\n");
                res.getWriter().write("time: "+participationRes.getTime());
                res.getWriter().write("\n");
                res.getWriter().write("note: "+participationRes.getNoteText());
                res.getWriter().write("\n");
                res.getWriter().write("\n");
            }
            res.getWriter().write("\n");
            res.getWriter().write("\n");

            for (RoleHasEntity roleHasEntityRes:rolesHasEntitiesRes){
                res.getWriter().write("idEntity: "+roleHasEntityRes.getIdEntity());
                res.getWriter().write("\n");
                res.getWriter().write("idRole: "+roleHasEntityRes.getIdRole());
                res.getWriter().write("\n");
                res.getWriter().write("\n");
            }

            res.getWriter().write("-------------------------------codes----------------------------------------------");
            res.getWriter().write("\n");
            res.getWriter().write("\n");



        } catch (UserNotExistsException e) {
            e.printStackTrace();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        } catch (MedicalAppointmentNotExistsException e) {
            e.printStackTrace();
        } catch (ParticipationNotExistsException e) {
            e.printStackTrace();
        } catch (RoleHasEntityNotExistsException e) {
            e.printStackTrace();
        }

    }
}
