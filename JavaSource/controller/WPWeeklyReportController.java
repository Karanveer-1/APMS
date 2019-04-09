package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.EmpPLevel;
import model.Employee;
import model.TimesheetRow;
import model.TimesheetRowState;
import model.WPWeeklyReportRow;
import model.WorkPackage;

@Named("wpWeeklyReportController")
@SessionScoped
public class WPWeeklyReportController implements Serializable {

    @Inject
    private DatabaseController database;

    @Inject
    private AuthenticationController auth;

    private List<Integer> projectNos;
    private List<String> wpids;
    private List<WPWeeklyReportRow> rows;

    private Integer proNo;
    private String wpid;

    public void init() {
        Employee currentEmployee = ((Employee) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get(LoginController.USER_KEY));
        
        if (currentEmployee == null) {
            return;
        }
        
        if (auth.isUserSystemAdmin()) {
            projectNos = database.getAllProjectNo();
            return;
        }

        projectNos = database.getAllProjectNoForProjectManager(currentEmployee.getEmpNumber());
    }
    
    public void pageInit() {        
        proNo = null;
        wpid = null;
        rows = null;
        wpids = null;
        projectNos = null;
        
        init();
    }
    
    public void onProjectChange() {
        if (proNo != null) {
            wpids = getLeafWpids(proNo);
        }
        
        wpid = null;
    }

    private List<String> getLeafWpids(int proNo) {
        return database.getAllWp().stream()
                .filter(wp -> database.getTimesheetRows().stream()
                        .filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
                        .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED)).map(m -> {
                            return m.getTimesheetRowPk().getWpid();
                        }).distinct().anyMatch(k -> k.equals(wp.getWpid()) && wp.isLeaf()))
                .map(WorkPackage::getWpid).collect(Collectors.toList());
    }

    private List<Employee> getParticipatingEmployees() {
        return database.getEmployees().stream()
                .filter(e -> database.getTimesheetRows().stream().filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
                        .filter(r -> r.getTimesheetRowPk().getWpid().equals(wpid))
                        .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED)).map(r -> {
                            return r.getTimesheetRowPk().getEmpNo();
                        }).distinct().anyMatch(re -> e.getEmpNumber() == re))
                .collect(Collectors.toList());
    }

    private float getSumHoursForRows(int empNo, Date date) {
        return (float) database.getTimesheetRows().stream().filter(r -> r.getTimesheetRowPk().getEmpNo() == empNo)
                .filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
                .filter(r -> r.getTimesheetRowPk().getWpid().equals(wpid))
                .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED))
                .filter(r -> r.getTimesheetRowPk().getStartDate().equals(date)).mapToDouble(r -> {
                    float sat = r.getSat();
                    float sun = r.getSun();
                    float mon = r.getMon();
                    float tue = r.getTue();
                    float wed = r.getWed();
                    float thu = r.getThu();
                    float fri = r.getFri();

                    return sat + sun + mon + tue + wed + thu + fri;
                }).sum();
    }

    private List<TimesheetRow> getRelaventTimesheetRows() {
        List<TimesheetRow> rows = database.getTimesheetRows().stream()
                .filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
                .filter(r -> r.getTimesheetRowPk().getWpid().equals(wpid))
                .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED)).collect(Collectors.toList());

        rows.sort(new Comparator<TimesheetRow>() {
            @Override
            public int compare(TimesheetRow a, TimesheetRow b) {
                return ((TimesheetRow) a).getTimesheetRowPk().getStartDate()
                        .compareTo(((TimesheetRow) b).getTimesheetRowPk().getStartDate());
            }
        });

        return rows;
    }

    public Date getWeekStartDate() {
        if (proNo == null || wpid == null) {
            return null;
        }

        return database.getAllWp().stream().filter(wp -> wp.getProNo() == proNo).filter(wp -> wp.getWpid().equals(wpid))
                .findFirst().map(WorkPackage::getStartDate).get();
    }

    public float getTotalForEmployee(int empNo) {
        return (float) database.getTimesheetRows().stream().filter(r -> r.getTimesheetRowPk().getEmpNo() == empNo)
                .filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
                .filter(r -> r.getTimesheetRowPk().getWpid().equals(wpid))
                .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED)).mapToDouble(r -> {
                    float sat = r.getSat();
                    float sun = r.getSun();
                    float mon = r.getMon();
                    float tue = r.getTue();
                    float wed = r.getWed();
                    float thu = r.getThu();
                    float fri = r.getFri();

                    return sat + sun + mon + tue + wed + thu + fri;
                }).sum();
    }

    public float getTotalForAllEmployees() {
        return (float) getParticipatingEmployees().stream().mapToDouble(e -> {
            return getTotalForEmployee(e.getEmpNumber());
        }).sum();
    }

    public void generateReport() {
        rows = new ArrayList<>();

        List<Employee> participatingEmployees = getParticipatingEmployees();
        List<TimesheetRow> relaventRows = getRelaventTimesheetRows();

        List<Date> dates = relaventRows.stream().map(r -> {
            return r.getTimesheetRowPk().getStartDate();
        }).distinct().collect(Collectors.toList());

        dates.forEach(d -> {
            for (Employee e : participatingEmployees) {
                int empNo = e.getEmpNumber();

                String pLevel = database.getEmpPLevels().stream().filter(p -> p.getEmpPLevelPK().getEmpNo() == empNo)
                        .map(EmpPLevel::getpLevel).findFirst().get();

                float totalHours = getSumHoursForRows(empNo, d);

                rows.add(new WPWeeklyReportRow(empNo, pLevel, d, totalHours));
            }
        });
    }

    // Should be get all leafs
    public boolean canSelectWP() {
        return proNo != null && !getLeafWpids(proNo).isEmpty();
    }

    public boolean canGenerateReport() {
        return proNo != null && wpid != null;
    }

    public List<Integer> getProjectNos() {
        return projectNos;
    }

    public void setProjectNos(List<Integer> projectNos) {
        this.projectNos = projectNos;
    }

    public List<String> getWpids() {
        return wpids;
    }

    public void setWpids(List<String> wpids) {
        this.wpids = wpids;
    }

    public Integer getProNo() {
        return proNo;
    }

    public void setProNo(Integer proNo) {
        this.proNo = proNo;
    }

    public String getWpid() {
        return wpid;
    }

    public void setWpid(String wpid) {
        this.wpid = wpid;
    }

    public List<WPWeeklyReportRow> getRows() {
        return rows;
    }

    public void setRows(List<WPWeeklyReportRow> rows) {
        this.rows = rows;
    }
}
