package controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.EmployeeState;
import model.PLevel;
import model.PLevelPK;
import service.PasswordHash;
import service.PasswordHash.CannotPerformOperationException;

/**
 * PLevelController.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Named("pLevelController")
@SessionScoped
public class PLevelController implements Serializable {

    @Inject
    private DatabaseController database;

    private List<PLevel> pLevels;

    private PLevel editPLevel;

    @PostConstruct
    public void init() {
        editPLevel = null;
        pLevels = database.getPLevels();
        System.out.println(pLevels);

    }

    /**
     * Returns the {bare_field_name} for this PLevelController.
     * @return the pLevels
     */
    public List<PLevel> getpLevels() {
        return pLevels;
    }

    /**
     * Sets the pLevels for this PLevelController
     * @param pLevels the pLevels to set
     */
    public void setpLevels(List<PLevel> pLevels) {
        this.pLevels = pLevels;
    }

    /**
     * Returns the {bare_field_name} for this PLevelController.
     * @return the editPLevel
     */
    public PLevel getEditPLevel() {
        return editPLevel;
    }

    /**
     * Sets the editPLevel for this PLevelController
     * @param editPLevel the editPLevel to set
     */
    public void setEditPLevel(PLevel editPLevel) {
        this.editPLevel = editPLevel;
        PrimeFaces.current().executeScript("PF('editPLevelDialog').show();");
    }
    
    public void editPLevel(Float wage) {
        
        float wagef = wage;
        System.out.println("Float wage:" + wage);
        pLevels = database.getPLevels();        
        if (Float.valueOf(wage) > 0) {
            editPLevel.setWage(wagef);
            
            database.updatePLevel(editPLevel);
            
            pLevels = database.getPLevels();

            PrimeFaces.current()
                    .executeScript("PF('editPLevelDialog').hide();");
        }
    }
    
    public void remove(PLevel pLevel) {
        database.removePLevel(pLevel);
        pLevels = database.getPLevels();
    }
    
    public void add(String pLevel, Date startDate, String wage) {
        Float wagef = Float.valueOf(wage);
        pLevels = database.getPLevels();  
        boolean validPK = true;
        for(PLevel p : pLevels) {
            if(p.getpLevelPK().getpLevel().equals(pLevel) && p.getpLevelPK().getStartDate().equals(startDate)) {
                validPK = false;
                break;
            }
        }
        if(validPK) {
            database.addPLevel(new PLevel(pLevel, startDate, wagef));
        }
        pLevels = database.getPLevels();  
        PrimeFaces.current().executeScript("PF('addPLevelDialog').hide();");
    }
    
}
