package service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import controller.DatabaseController;
import controller.LoginController;
import model.Employee;

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
                // Setting up key generator
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
                keyGen.initialize(1024, random);
                
                // Generating a key pair
                KeyPair pair = keyGen.generateKeyPair();
                PrivateKey priv = pair.getPrivate();
                PublicKey pub = pair.getPublic();
                
//                System.out.println("Private key: " +  pub.toString());
//                System.out.println("Private Key: " + priv.toString());
                
                byte[] privKey = priv.getEncoded();
                byte[] pubKey = pub.getEncoded();
                
                Employee currentEmployee = login.getCurrentEmployee();
                currentEmployee.setPublicKey(pubKey);
                currentEmployee.setPrivateKey(privKey);
                
                database.updateEmployee(currentEmployee);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
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
