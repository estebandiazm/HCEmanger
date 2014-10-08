package com.hcemanager.dao.springServices;

import com.hcemanager.dao.acts.interfaces.AccountDAO;
import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.interfaces.ObservationDAO;
import com.hcemanager.dao.acts.interfaces.SupplyDAO;
import com.hcemanager.dao.codes.CodeDAO;
import com.hcemanager.dao.connectors.interfaces.ParticipationDAO;
import com.hcemanager.dao.connectors.interfaces.RoleHasEntityDAO;
import com.hcemanager.dao.entities.interfaces.*;
import com.hcemanager.dao.medicalAppointments.interfaces.DicomImageDAO;
import com.hcemanager.dao.medicalAppointments.interfaces.MedicalAppointmentDAO;
import com.hcemanager.dao.roles.interfaces.*;
import com.hcemanager.dao.users.interfaces.UserDAO;
import com.hcemanager.models.connectors.Participation;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class SpringServices implements ServletContextListener {

    private static ApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
       try{
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Acts beans
    // -----------------------------------------------------------------------------------------------------------------
    public static AccountDAO getAccountDAO(){
        return (AccountDAO) context.getBean("accountDao");
    }
    public static ActDAO getActDAO(){
        return (ActDAO)context.getBean("actDao");
    }
    public static ObservationDAO getObservationDAO(){
        return (ObservationDAO) context.getBean("observationDao");
    }
    public static SupplyDAO getSupplyDAO(){
        return (SupplyDAO)context.getBean("supplyDao");
    }
    // -----------------------------------------------------------------------------------------------------------------
    // Entities beans
    // -----------------------------------------------------------------------------------------------------------------
    public static LivingSubjectDAO getLivingSubjectDAO(){ return (LivingSubjectDAO) context.getBean("livingSubjectDao");}
    public static PersonDAO getPersonDAO(){
        return (PersonDAO) context.getBean("personDao");
    }
    public static ManufacturedMaterialDAO getManufacturedMaterialDAO() {return (ManufacturedMaterialDAO) context.getBean("manufacturedMaterialDao");}
    public static MaterialDAO getMaterialDAO() {
        return (MaterialDAO)context.getBean("materialDao");
    }
    public static EntityDAO getEntityDAO(){
        return (EntityDAO) context.getBean("entityDao");
    }
    public static NonPersonLivingSubjectDAO getNonPersonLivingSubjectDAO(){ return (NonPersonLivingSubjectDAO) context.getBean("nonPersonLivingSubjectDao");}
    public static OrganizationDAO getOrganizationDAO(){ return (OrganizationDAO) context.getBean("organizationDao");}
    public static PlaceDAO getPlaceDAO(){ return (PlaceDAO) context.getBean("placeDao");}
    public static DeviceDAO getDeviceDAO(){return (DeviceDAO) context.getBean("deviceDao");}
    public static ContainerDAO getContainerDAO(){ return (ContainerDAO) context.getBean("containerDao");}
    public static UserDAO getUserDAO(){ return (UserDAO) context.getBean("userDao");}
    // -----------------------------------------------------------------------------------------------------------------
    // Roles beans
    // -----------------------------------------------------------------------------------------------------------------
    public static RoleDAO getRoleDAO(){
        return (RoleDAO) context.getBean("roleDao");
    }
    public static AccessDAO getAccessDAO(){
        return  (AccessDAO) context.getBean("accessDao");
    }
    public static EmployeeDAO getEmployeeDAO() { return (EmployeeDAO) context.getBean("employeeDao");}
    public static LicensedEntityDAO getLicensedEntityDAO() { return (LicensedEntityDAO) context.getBean("licensedEntityDao");}
    public static PatientDAO getPatientDAO(){return (PatientDAO) context.getBean("patientDao");}
    //------------------------------------------------------------------------------------------------------------------
    //Codes bean
    //------------------------------------------------------------------------------------------------------------------
    public static CodeDAO getCodeDAO(){return (CodeDAO) context.getBean("codeDao");}
    //------------------------------------------------------------------------------------------------------------------
    //MedicalAppointment bean
    //------------------------------------------------------------------------------------------------------------------
    public static MedicalAppointmentDAO getMedicalAppointmentDAO(){return (MedicalAppointmentDAO) context.getBean("medicalAppointmentDao");}
    public static DicomImageDAO getDicomImageDAO(){return (DicomImageDAO) context.getBean("dicomImageDao");}
    //------------------------------------------------------------------------------------------------------------------
    //Connector bean
    //------------------------------------------------------------------------------------------------------------------
    public static ParticipationDAO getParticipationDAO(){return (ParticipationDAO) context.getBean("participationDao");}
    public static RoleHasEntityDAO getRoleHasEntityDAO(){return (RoleHasEntityDAO) context.getBean("roleHasEntityDao");}
}
