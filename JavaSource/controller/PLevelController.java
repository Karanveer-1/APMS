package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.PLevel;

/**
 * PLevelController.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
@Named("pLevelController")
public class PLevelController {

    @Inject
    private DatabaseController database;

    private List<PLevel> activeEmployees;

    private PLevel editPLevel;

    @PostConstruct
    public void init() {
        editPLevel = null;
        activeEmployees = database.getPLevels();
    }
}
