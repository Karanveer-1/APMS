package controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.EmpPLevel;
import model.PLevel;

/**
 * EmpPLevelController.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Named("empPLevelController")
@SessionScoped
public class EmpPLevelController implements Serializable {

    @Inject
    private DatabaseController database;
    
    private List<PLevel> pLevels;
    private List<EmpPLevel> empPLevels;
    
    private EmpPLevel editEmpPLevel;
    
    @PostConstruct
    public void init() {
        editEmpPLevel = null;
        pLevels = database.getPLevels();
        empPLevels = database.getEmpPLevels();
        System.out.println(pLevels);
        System.out.println(empPLevels);

    }

    /**
     * Returns the {bare_field_name} for this EmpPLevelController.
     * @return the pLevels
     */
    public List<PLevel> getpLevels() {
        return pLevels;
    }

    /**
     * Sets the pLevels for this EmpPLevelController
     * @param pLevels the pLevels to set
     */
    public void setpLevels(List<PLevel> pLevels) {
        this.pLevels = pLevels;
    }

    /**
     * Returns the {bare_field_name} for this EmpPLevelController.
     * @return the empPLevels
     */
    public List<EmpPLevel> getEmpPLevels() {
        return empPLevels;
    }

    /**
     * Sets the empPLevels for this EmpPLevelController
     * @param empPLevels the empPLevels to set
     */
    public void setEmpPLevels(List<EmpPLevel> empPLevels) {
        this.empPLevels = empPLevels;
    }

    /**
     * Returns the {bare_field_name} for this EmpPLevelController.
     * @return the editEmpPLevel
     */
    public EmpPLevel getEditEmpPLevel() {
        return editEmpPLevel;
    }

    /**
     * Sets the editEmpPLevel for this EmpPLevelController
     * @param editEmpPLevel the editEmpPLevel to set
     */
    public void setEditEmpPLevel(EmpPLevel editEmpPLevel) {
        this.editEmpPLevel = editEmpPLevel;
    }
    
    public void remove(PLevel pLevel) {
        database.removePLevel(pLevel);
        pLevels = database.getPLevels();
    }
    

}
