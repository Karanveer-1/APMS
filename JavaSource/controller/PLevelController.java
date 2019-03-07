package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class PLevelController implements Serializable{

    @Inject
    private DatabaseController database;

    private List<PLevel> pLevels;

    private PLevel editPLevel;

    @PostConstruct
    public void init() {
        editPLevel = null;
        pLevels = database.getPLevels();
    }
}
