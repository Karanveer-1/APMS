package controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.PLevel;

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
    
    public void closeDialog() {
        PrimeFaces.current().executeScript("PF('errorDialog').hide();");
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Float wagef = Float.valueOf(wage);
        pLevels = database.getPLevels();  
        boolean validPK = true;
        for(PLevel p : pLevels) {
            System.out.println(p.getpLevelPK().getpLevel() + " : " + pLevel);
            System.out.println(p.getpLevelPK().getStartDate() + " : " + startDate);
            System.out.println(p.getpLevelPK().getpLevel().equals(pLevel));
            System.out.println(sdf.format(p.getpLevelPK().getStartDate()).equals(sdf.format(startDate)));
            
            if(p.getpLevelPK().getpLevel().equals(pLevel) && sdf.format(p.getpLevelPK().getStartDate()).equals(sdf.format(startDate))) {
                validPK = false;
                PrimeFaces.current().executeScript("PF('errorDialog').show();");
                break;
            }
        }
        if(validPK) {
            System.out.println(startDate);
            database.addPLevel(new PLevel(pLevel, startDate, wagef));
        }
        pLevels = database.getPLevels();  
        PrimeFaces.current().executeScript("PF('addPLevelDialog').hide();");
    }
    
}
