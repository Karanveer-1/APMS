package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.PLevel;
import model.Project;
import model.TimesheetRowState;
import model.WorkPackage;

@Named("monthlyReport")
@ConversationScoped
public class MonthlyReportController implements Serializable {
    @Inject
    private DatabaseController database;
    @Inject
    private LoginController login;
    @Inject AuthenticationController auth;
    
    private Project project;
    private Integer proNo;
    
    private List<WorkPackage> allWP;
    private List<PLevel> pLevel = new ArrayList<PLevel>();
    
    public List<Integer> init() {
        if (auth.isUserSystemAdmin()) {
            return database.getAllProjectNo();
        }
        
        return database.getAllProjectNoForProjectManager(login.getCurrentEmployee().getEmpNumber());
    }
            
    public void generateReport() {
        project = database.findByProjectNo(proNo.intValue());
        allWP = getLeafWpids(proNo.intValue());
        findPLevel();
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
    
    public double leftWork(WorkPackage wp) {
        double pm = generatePmEstimate(wp);
        double re = generateReEstimate(wp);
        
        double test =  ((pm - re)/re)*100;

        return test;
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
                pLevel.add(pl);
            } catch (Exception e) {
                System.out.println("Exception in Monthly controller");
                return;
            }
        }
    }

    public Integer getProNo() {
        return proNo;
    }

    public void setProNo(Integer proNo) {
        this.proNo = proNo;
    }
    
    private List<WorkPackage> getLeafWpids(int proNo) {
        return database.getAllWp()
                .stream()
                .filter(wp -> wp.getProNo() == proNo)
                .filter(wp -> wp.isLeaf())
                .collect(Collectors.toList());
    }
    
    public float getTotalForEmployee(int empNo, String wpid) {
        return (float) database.getTimesheetRows()
            .stream()
            .filter(r -> r.getTimesheetRowPk().getEmpNo() == empNo)
            .filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
            .filter(r -> r.getTimesheetRowPk().getWpid().equals(wpid))
            .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED))
            .mapToDouble(r -> {
                float sat = r.getSat();
                float sun = r.getSun();
                float mon = r.getMon();
                float tue = r.getTue();
                float wed = r.getWed();
                float thu = r.getThu();
                float fri = r.getFri();

                return sat + sun + mon + tue + wed + thu + fri;
            })
            .sum();
    }

    public float getTotalForAllEmployees(String wpid) {
        return (float) getParticipatingEmployees(wpid)
            .stream()
            .mapToDouble(e -> {
                return getTotalForEmployee(e.getEmpNumber(), wpid);
            })
            .sum();
    }
    
    private List<Employee> getParticipatingEmployees(String wpid) {
        return database.getEmployees()
            .stream()
            .filter(e -> database.getTimesheetRows()
                .stream()
                .filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
                .filter(r -> r.getTimesheetRowPk().getWpid().equals(wpid))
                .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED))
                .map(r -> {
                    return r.getTimesheetRowPk().getEmpNo();
                })
                .distinct()
                .anyMatch(re -> e.getEmpNumber() == re))
            .collect(Collectors.toList());
    }

        
}
