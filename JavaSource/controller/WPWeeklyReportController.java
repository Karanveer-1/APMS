package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.TimesheetRow;
import model.WPWeeklyReportRow;
import model.WorkPackage;

/*
 * Only PM can see
 */
@Named("wpWeeklyReportController")
@SessionScoped
public class WPWeeklyReportController implements Serializable {

    @Inject
    private DatabaseController database;

    private List<Integer> projectNos;
    private List<String> wpids;
    private List<WPWeeklyReportRow> rows;

    private Integer proNo;
    private String wpid;

    public void init() {
        projectNos = database.getAllProjectNo();
    }

    public void onProjectChange() {
        if (proNo != null) {
            wpids = getLeafWpids(proNo);
        }
    }
    
    // Wrong!
    private List<String> getLeafWpids(int proNo) {
        List<WorkPackage> roots = database.getRootWPByProNo(proNo);
        List<String> leafsids = new ArrayList<>();
        
        for (WorkPackage wp : roots) {
            List<WorkPackage> leafs = database.getLowerWP(wp.getWpid());

            for (WorkPackage wpl : leafs) {
                if (!leafsids.contains(wpl.getWpid())) {
                    leafsids.add(wpl.getWpid());
                }
            }
        }
        
        return leafsids;
    }

    // SHould be get all leafs
    public boolean canSelectWP() {
        return proNo != null && !database.getWpIdByProjectNo(proNo).isEmpty();
    }

    public boolean canGenerateReport() {
        return proNo != null && wpid != null;
    }

    public void generateReport() {
        List<TimesheetRow> rows = database.getTimesheetRows(proNo, wpid);
        Collections.sort(rows, new Comparator<TimesheetRow>() {
            public int compare(TimesheetRow a, TimesheetRow b) {
                return a.getTimesheetRowPk().getStartDate().compareTo(b.getTimesheetRowPk().getStartDate());
            }
        });
    }

    public Date getWeekStartDate() {
        return null;
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
