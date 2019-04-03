package controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.EmpPLevel;
import model.EmpPLevelPK;
import model.PLevel;

/**
 * EmpPLevelController.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Named("empPLevelController")
@RequestScoped
public class EmpPLevelController implements Serializable {

    @Inject
    private DatabaseController database;
    private List<PLevel> pLevels;
    private List<EmpPLevel> empPLevels;
    private List<String> empPLevelDropDownList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private EmpPLevel editEmpPLevel;

    @PostConstruct
    public void init() {
        editEmpPLevel = null;
        pLevels = database.getPLevels();
        empPLevels = database.getEmpPLevels();
        empPLevelDropDownList = new ArrayList<String>();
        populateDropDown();
        System.out.println(pLevels);
        System.out.println(empPLevels);

    }

    void populateDropDown() {
        empPLevelDropDownList.clear();
        for(PLevel p : pLevels) {
            empPLevelDropDownList.add(p.getpLevelPK().getpLevel() + " : " + p.getpLevelPK().getStartDate());
        }
    }

    /**
     * Returns the {bare_field_name} for this EmpPLevelController.
     * @return the pLevels
     */
    public List<PLevel> getpLevels() {
        pLevels = database.getPLevels();
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
        empPLevels = database.getEmpPLevels();
        populateDropDown();
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

    public void add(int empNo, String pLevelText) {
        pLevels = database.getPLevels();  
        boolean validPK = true;
        EmpPLevel tempEmpPLevel = null;
        for(PLevel p : pLevels) {
            if((p.getpLevelPK().getpLevel() + " : " + p.getpLevelPK().getStartDate()).equals(pLevelText)) {
                tempEmpPLevel = new EmpPLevel(new EmpPLevelPK(empNo, p.getpLevelPK().getStartDate()), p.getpLevelPK().getpLevel());
            }
        }
        for(EmpPLevel ep : empPLevels) {
            if(ep.getEmpPLevelPK().getEmpNo() == tempEmpPLevel.getEmpPLevelPK().getEmpNo() && ep.getEmpPLevelPK().getStartDate().equals(tempEmpPLevel.getEmpPLevelPK().getStartDate())) {
                validPK = false;
                PrimeFaces.current().executeScript("PF('errorDialog').show();");
                break;

            }
        }
        if(validPK) {
            System.out.println("Added: " + empNo + " : " + pLevelText);
            database.addEmpPLevel(tempEmpPLevel);
        }
        empPLevels = database.getEmpPLevels();
        PrimeFaces.current().executeScript("PF('addEmpPLevelDialog').hide();");
    }

    /**
     * Returns the {bare_field_name} for this EmpPLevelController.
     * @return the empPLevelDropDownList
     */
    public List<String> getEmpPLevelDropDownList() {
        return empPLevelDropDownList;
    }

    /**
     * Sets the empPLevelDropDownList for this EmpPLevelController
     * @param empPLevelDropDownList the empPLevelDropDownList to set
     */
    public void setEmpPLevelDropDownList(List<String> empPLevelDropDownList) {
        this.empPLevelDropDownList = empPLevelDropDownList;
    }

    public void removeEmpPLevel(EmpPLevel p) {
        database.removeEmpPLevel(p);
        empPLevels = database.getEmpPLevels();
    }
    public void closeDialog() {
        PrimeFaces.current().executeScript("PF('errorDialog').hide();");
    }
}
