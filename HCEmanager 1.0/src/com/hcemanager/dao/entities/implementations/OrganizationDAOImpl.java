package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.entities.rowMappers.OrganizationRowMapper;
import com.hcemanager.dao.entities.interfaces.OrganizationDAO;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.Organization;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, juan.
 */
public class OrganizationDAOImpl extends JdbcDaoSupport implements OrganizationDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constans
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Organization (idOrganization, addr, Entity_idEntity) values (?,?,?)";
    public static final String UPDATE = "update Organization set addr=? where idOrganization=?";
    public static final String DELETE = "delete from Organization where idOrganization=?";
    public static final String SELECT_ORGANIZATION = "select * from Organization inner join Entity where Entity_idEntity=idEntity and idOrganization=?";
    public static final String SELECT_ORGANIZATIONS = "select * from Organization inner join Entity where Entity_idEntity=idEntity";
    public static final String INSERT_CODE = "insert into Codes_has_Organization (Codes_idCodes,Organization_idOrganization,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Codes_has_Organization set Codes_idCodes=? where Organization_idOrganization=? and type=?;";
    public static final String DELETE_CODE = "delete from Codes_has_Organization where Organization_idOrganization=?";
    public static final String SELECT_CODES = "select * from Codes_has_Organization inner join Codes where Codes_idCodes=idCodes and Organization_idOrganization=?";

    //TODO: completar codes y foreignkey.

    //------------------------------------------------------------------------------------------------------------------
    //CRUD mtehods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an Organization.
     * @param organization
     * @throws OrganizationExistsException
     */
    @Override
    public void insertOrganization(Organization organization) throws OrganizationExistsException, EntityExistsException {
        try {

            SpringServices.getEntityDAO().insertEntity(organization);

            getJdbcTemplate().update(INSERT,
                    organization.getIdOrganization(),
                    organization.getAddr(),
                    organization.getId());

            //Codes
            
            getJdbcTemplate().update(INSERT_CODE,
                    organization.getStandardIndustryCode().getId(),
                    organization.getIdOrganization(),
                    organization.getStandardIndustryCode().getType());


        }catch (DataIntegrityViolationException e){
            throw new OrganizationExistsException(e);
        }
    }

    /**
     * Update an Organization.
     * @param organization
     * @throws OrganizationNotExistsException
     * @throws OrganizationExistsException
     */
    @Override
    public void updateOrganization(Organization organization) throws OrganizationNotExistsException, OrganizationExistsException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException {

        if (isDifferent(organization)){
            try {

                SpringServices.getEntityDAO().updateEntity(organization);

                int rows = getJdbcTemplate().update(UPDATE, organization.getAddr(), organization.getIdOrganization());
                if(rows==0){
                    throw new OrganizationNotExistsException();
                }
                updateCodes(organization);
            }catch (DataIntegrityViolationException e){
                throw new OrganizationExistsException(e);
            }
        }else {
            SpringServices.getEntityDAO().updateEntity(organization);
            updateCodes(organization);
        }
    }

    /**
     * Delete an Organization.
     * @param id
     * @throws OrganizationNotExistsException
     * @throws OrganizationExistsException
     */
    @Override
    public void deleteOrganization(String id) throws OrganizationNotExistsException, OrganizationCannotBeDeletedException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException {
        try {
            Organization organization = getOrganization(id);

            getJdbcTemplate().update(DELETE_CODE,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if(rows==0){
                throw new OrganizationNotExistsException();
            }

            SpringServices.getEntityDAO().deleteEntity(organization.getId());

        }catch (DataIntegrityViolationException e){
            throw new OrganizationCannotBeDeletedException(e);
        }
    }

    /**
     * Get an Organization.
     * @param id
     * @return Organization.
     * @throws OrganizationNotExistsException
     */
    @Override
    public Organization getOrganization(String id) throws OrganizationNotExistsException, CodeNotExistsException {
        try {
            Organization organization = getJdbcTemplate().queryForObject(SELECT_ORGANIZATION, new OrganizationRowMapper(), id);

            setOrganizationCodes(organization, getOrganizationCodes(id));

            return organization;
        }catch (EmptyResultDataAccessException e){
            throw new OrganizationNotExistsException(e);
        }
    }

    /**
     * Get all Organizations
     * @return
     * @throws OrganizationNotExistsException
     */
    @Override
    public List<Organization> getOrganizations() throws OrganizationNotExistsException, CodeNotExistsException {
        try {
            List<Organization> organizations = getJdbcTemplate().query(SELECT_ORGANIZATIONS, new OrganizationRowMapper());

            for (Organization organization:organizations){
                setOrganizationCodes(organization, getOrganizationCodes(organization.getIdOrganization()));
                organizations.set(organizations.indexOf(organization),organization);
            }

            return organizations;
        }catch (EmptyResultDataAccessException e){
            throw new OrganizationNotExistsException(e);
        }
    }

    /**
     * Get Codes of an Organization by id
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    public List<Code> getOrganizationCodes(String id) throws CodeNotExistsException {
        try {

            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set Codes to Organization
     * @param organization
     * @param codes
     * @return
     */
    public void setOrganizationCodes(Organization organization, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getEntityDAO().setEntityCodes(organization,
                SpringServices.getEntityDAO().getEntityCodes(organization.getId()));

        for (Code code:codes){

            String typeCode = code.getType();

            if (typeCode.equals("standardIndustryCode")){
                organization.setStandardIndustryCode(code);
            }

        }
    }

    private boolean isDifferent(Organization organization) throws OrganizationNotExistsException, CodeNotExistsException {
        Organization oldOrganization = getOrganization(organization.getIdOrganization());

        int attrDifferent=0;

        if (!organization.getAddr().equals(oldOrganization.getAddr())){
            attrDifferent++;
        }

        return attrDifferent!=0;
    }

    private void updateCodes(Organization organization) throws CodeNotExistsException {
        List<Code> oldCodes = getOrganizationCodes(organization.getIdOrganization());

        for (Code code:oldCodes){
            if (code.getType().equalsIgnoreCase(organization.getStandardIndustryCode().getType())){
                if (!code.getId().equals(organization.getStandardIndustryCode().getId())){
                    getJdbcTemplate().update(UPDATE_CODE,
                            organization.getStandardIndustryCode().getId(),
                            organization.getIdOrganization(),
                            organization.getStandardIndustryCode().getType());
                }
            }
        }
    }
}
