package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import model.PLevel;
import model.Project;
import model.WorkPackage;

@Named("monthlyReport")
@ConversationScoped
public class MonthlyReportController implements Serializable {
    @PersistenceContext(unitName = "apms", type = PersistenceContextType.TRANSACTION)
    private EntityManager manager;
    @Inject
    private DatabaseController database;
    @Inject 
    private Conversation convo;
    private Project project;
    private List<WorkPackage> allWP;
    private List<PLevel> pLevel = new ArrayList<PLevel>();
            
    public String generateReport(Project project) {
        convo.begin();
        setProject(project);
        setAllWP(database.getAllWp());
        findPLevel();
        return "MonthlyReport.xhtml?faces-redirect=true";
    }

    public List<WorkPackage> getAllWps() {
        return allWP;
    }
    
    public int generatePmEstimate(WorkPackage wp) {
        return wp.getPmEstDS() + wp.getPmEstJS() + wp.getPmEstP1() + wp.getPmEstP2() +
                 wp.getPmEstP3() + wp.getPmEstP4() + wp.getPmEstP5() + wp.getPmEstP6() +
                 wp.getPmEstSS();
    }
    
    public float convertPmEstimateIntoLabour(WorkPackage wp) {
        return pLevel.get(6).getWage()*wp.getPmEstDS() + pLevel.get(8).getWage()*wp.getPmEstJS() + 
                pLevel.get(0).getWage()*wp.getPmEstP1() +
                pLevel.get(1).getWage()*wp.getPmEstP2() + pLevel.get(2).getWage()*wp.getPmEstP3() +
                pLevel.get(3).getWage()*wp.getPmEstP4() + pLevel.get(4).getWage()*wp.getPmEstP5() + 
                pLevel.get(5).getWage()*wp.getPmEstP6() + pLevel.get(7).getWage()*wp.getPmEstSS();
    }

    public int generateReEstimate(WorkPackage wp) {
        return wp.getReEstDS() + wp.getReEstJS() + wp.getReEstP1() + wp.getReEstP2() +
                 wp.getReEstP3() + wp.getReEstP4() + wp.getReEstP5() + wp.getReEstP6() +
                 wp.getReEstSS();
    }
    
    public float convertReEstimateIntoLabour(WorkPackage wp) {
        return pLevel.get(6).getWage()*wp.getReEstDS() + pLevel.get(8).getWage()*wp.getReEstJS() + 
                pLevel.get(0).getWage()*wp.getReEstP1() +
                pLevel.get(1).getWage()*wp.getReEstP2() + pLevel.get(2).getWage()*wp.getReEstP3() +
                pLevel.get(3).getWage()*wp.getReEstP4() + pLevel.get(4).getWage()*wp.getReEstP5() + 
                pLevel.get(5).getWage()*wp.getReEstP6() + pLevel.get(7).getWage()*wp.getReEstSS();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<WorkPackage> getAllWP() {
        return allWP;
    }

    public void setAllWP(List<WorkPackage> allWP) {
        this.allWP = allWP;
    }
    
    private void findPLevel() {
        ArrayList<String> ps = new ArrayList<String>(Arrays.asList("P1","P2","P3","P4","P5","P6","DS","SS","JS"));
        for(String s : ps) {
            try {
                PLevel pl = database.getPLevelByStartDate(project.getStartDate(), s);
                System.out.println(s + " " + pl.getWage());
                pLevel.add(pl);
            } catch (Exception e) {
                System.out.println("Exception in Monthly controller");
                return;
            }
        }
    }
        
}
