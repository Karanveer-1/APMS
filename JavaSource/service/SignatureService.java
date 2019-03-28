package service;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import controller.DatabaseController;
import controller.LoginController;
import model.Employee;
import service.PasswordHash.CannotPerformOperationException;


@Named("sig")
@RequestScoped
public class SignatureService {
    @Inject LoginController login;
    @Inject DatabaseController database;
    
    public void encryptWithPhrase(String phrase) {
        if (phrase == null || phrase.trim().length() == 0) {
            addErrorMessage("Please enter the value");
        } else {
            try {
                String hashedPhrase = PasswordHash.createHash(phrase);
                Employee currentEmployee = login.getCurrentEmployee();
                currentEmployee.setPassphrase(hashedPhrase);
                database.updateEmployee(currentEmployee);
            } catch (CannotPerformOperationException e) {
                e.printStackTrace();
            }
            
            PrimeFaces.current().executeScript("PF('keyPhraseDialog').hide();");
        }
        
    }
    
    private void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}