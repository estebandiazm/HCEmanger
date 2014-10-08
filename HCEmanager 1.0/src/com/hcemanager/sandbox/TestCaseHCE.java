package com.hcemanager.sandbox;

import com.hcemanager.dao.entities.interfaces.EntityDAO;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.AccountExistsException;
import com.hcemanager.exceptions.acts.AccountNotExistsException;
import com.hcemanager.exceptions.acts.ActExistsException;
import com.hcemanager.exceptions.acts.ActNotExistsException;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Account;
import com.hcemanager.models.codes.Code;
import com.vaadin.server.VaadinServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author daniel, juan.
 */
public class TestCaseHCE extends VaadinServlet {

    private EntityDAO entityDAO;


    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {


        Account account = new Account();

        //inserción de los atributos de la clase padre
        account.setIndependentInd(true);
        account.setInterruptibleInd(false);
        account.setNegationInd(false);
        account.setEffectiveTime("una hora");
        account.setActivityTime("dos horas");
        account.setAvailabilityTime("update availability time");
        account.setRepeatNumber("5");
        account.setDerivationExpr(" update test de derivation expr");
        account.setId("ACT_1");
        account.setText("probando la actualizacion");
        account.setTitle("retomando datos");
        account.setClassCode(new Code("CODE_2", "codigo 1", "currency code", "currency of account","currencyCode"));
        account.setCode(new Code("CODE_3", "codigo 1", "currency code", "currency of account","currencyCode"));
        account.setConfidentialityCode(new Code("CODE_4", "codigo 1", "currency code", "currency of account","currencyCode"));
        account.setLanguageCode(new Code("CODE_5", "codigo 1", "currency code", "currency of account","currencyCode"));
        account.setLevelCode(new Code("CODE_6", "codigo 1", "currency code", "currency of account","currencyCode"));
        account.setModeCode(new Code("CODE_7", "codigo 1", "currency code", "currency of account","currencyCode"));
        account.setPriorityCode(new Code("CODE_8", "codigo 1", "currency code", "currency of account","currencyCode"));
        account.setReasonCode(new Code("CODE_9", "codigo 1", "currency code", "currency of account","currencyCode"));
        account.setStatusCode(new Code("CODE_10", "codigo 1", "currency code", "currency of account","currencyCode"));
        account.setUncertaintyCode(new Code("CODE_11", "codigo 1", "currency code", "currency of account","currencyCode"));

        //inserción de los atributos propios de la clase

        account.setIdAccount("ACCOUNT_1");
        account.setInterestRateQuantity("432");
        account.setAllowedBalanceQuantity("123");
        account.setBalanceAmt("new update");
        account.setCurrencyCode(new Code("CODE_12","codigo 1","currency code","currency of account","currencyCode"));




        try {

            SpringServices.getAccountDAO().updateAccount(account);
//            Account accountResult= SpringServices.getAccountDAO().getAccount("ACCOUNT_1");

//            res.getWriter().write("idAccount: "+accountResult.getIdAccount()+"\n");
//            res.getWriter().write("balanceAmt: "+accountResult.getBalanceAmt()+"\n");
//            res.getWriter().write("\n");
//            res.getWriter().write("independentInd: "+accountResult.isIndependentInd()+"\n");
//            res.getWriter().write("text: "+accountResult.getText()+"\n");
//            res.getWriter().write("idAct: "+accountResult.getId()+"\n");
//            for (Entity entity1 : accounts) {
//                System.out.println("entity1.getId() = " + entity1.getId());
//                res.getWriter().write("entity1.getId() = " + entity1.getId());
//                res.getWriter().write("\n");
//                res.getWriter().write("quantity = " + entity1.getQuantity());
//                res.getWriter().write("\n");
//                res.getWriter().write("name = " + entity1.getName());
//                res.getWriter().write("\n");
//                res.getWriter().write("description = " + entity1.getDescription());
//                res.getWriter().write("\n");
//                res.getWriter().write("existenceTime = " + entity1.getExistenceTime());
//                res.getWriter().write("\n");
//                res.getWriter().write("telecom = " + entity1.getTelecom());
//                res.getWriter().write("\n");
//                res.getWriter().write("\n");
//            } catch (AccountNotExistsException e1) {
//            e1.printStackTrace();
        } catch (ActExistsException e) {
            e.printStackTrace();
        } catch (CodeIsNotValidException e) {
            e.printStackTrace();
        } catch (ActNotExistsException e) {
            e.printStackTrace();
        } catch (CodeExistsException e) {
            e.printStackTrace();
        } catch (AccountNotExistsException e) {
            e.printStackTrace();
        } catch (AccountExistsException e) {
            e.printStackTrace();
        } catch (CodeNotExistsException e) {
            e.printStackTrace();
        }


    }
}
