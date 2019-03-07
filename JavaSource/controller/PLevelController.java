package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.PLevel;

/**
 * PLevelController.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Named("pLevelController")
@ViewScoped
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
    }
}
