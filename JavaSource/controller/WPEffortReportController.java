package controller;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.EmpPLevel;
import model.Employee;
import model.TimesheetRow;
import model.TimesheetRowState;
import model.WPWeeklyReportRow;
import model.WorkPackage;

/*
 * Only PM can see
 */
@Named("wpEffortReportController")
@SessionScoped
public class WPEffortReportController implements Serializable {

    
    @Inject
    private DatabaseController database;
    private List<Integer> projectNos;
    private List<String> wpids;
    private Integer proNo;
    private String wpid;
    private WorkPackage wp;
    private Boolean showReport;
    
    public void init() {
        projectNos = database.getAllProjectNo();
        showReport = false;
    }

    public void onProjectChange() {
        if (proNo != null) {
            wpids = getLeafWpids(proNo);
            System.out.println(wpids);
        }
        wpid = null;
    }
    
 // Get any WP that is in any approved timesheet that is in the current project
    // number
    private List<String> getLeafWpids(int proNo) {
        return database.getTimesheetRows()
            .stream()
            .filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
            .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED))
            .map(m -> {
                return m.getTimesheetRowPk().getWpid();
            })
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the projectNos
     */
    public List<Integer> getProjectNos() {
        return projectNos;
    }

    /**
     * Sets the projectNos for this WPEffortReportController
     * @param projectNos the projectNos to set
     */
    public void setProjectNos(List<Integer> projectNos) {
        this.projectNos = projectNos;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the wpids
     */
    public List<String> getWpids() {
        return wpids;
    }

    /**
     * Sets the wpids for this WPEffortReportController
     * @param wpids the wpids to set
     */
    public void setWpids(List<String> wpids) {
        this.wpids = wpids;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the proNo
     */
    public Integer getProNo() {
        return proNo;
    }

    /**
     * Sets the proNo for this WPEffortReportController
     * @param proNo the proNo to set
     */
    public void setProNo(Integer proNo) {
        this.proNo = proNo;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the wpid
     */
    public String getWpid() {
        return wpid;
    }

    /**
     * Sets the wpid for this WPEffortReportController
     * @param wpid the wpid to set
     */
    public void setWpid(String wpid) {
        this.wpid = wpid;
    }

    
    public void generateReport() {
        wp = database.getWpByPk(proNo, wpid);
        System.out.println(wp);
        showReport = true;
    }
    
    // Should be get all leafs
    public boolean canSelectWP() {
        return proNo != null && !getLeafWpids(proNo).isEmpty();
    }

    public boolean canGenerateReport() {
        return proNo != null && wpid != null;
    }

    public void saveEstimateP1(int i) {
        wp.setReEstP1(i);
        database.updateWP(wp);
        database.getWpByPk(proNo, wpid);
    }
    public void saveEstimateP2(int i) {
        wp.setReEstP2(i);
        database.updateWP(wp);
        database.getWpByPk(proNo, wpid);
    }
    public void saveEstimateP3(int i) {
        wp.setReEstP3(i);
        database.updateWP(wp);
        database.getWpByPk(proNo, wpid);
    }
    public void saveEstimateP4(int i) {
        wp.setReEstP4(i);
        database.updateWP(wp);
        database.getWpByPk(proNo, wpid);
    }
    public void saveEstimateP5(int i) {
        wp.setReEstP5(i);
        database.updateWP(wp);
        database.getWpByPk(proNo, wpid);
    }
    public void saveEstimateP6(int i) {
        wp.setReEstP6(i);
        database.updateWP(wp);
        database.getWpByPk(proNo, wpid);
    }
    public void saveEstimateDS(int i) {
        wp.setReEstDS(i);
        database.updateWP(wp);
        database.getWpByPk(proNo, wpid);
    }
    public void saveEstimateSS(int i) {
        wp.setReEstSS(i);
        database.updateWP(wp);
        database.getWpByPk(proNo, wpid);
    }
    public void saveEstimateJS(int i) {
        wp.setReEstJS(i);
        database.updateWP(wp);
        database.getWpByPk(proNo, wpid);
    }
    

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the wp
     */
    public WorkPackage getWp() {
        return wp;
    }

    /**
     * Sets the wp for this WPEffortReportController
     * @param wp the wp to set
     */
    public void setWp(WorkPackage wp) {
        this.wp = wp;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the showReport
     */
    public Boolean getShowReport() {
        return showReport;
    }

    /**
     * Sets the showReport for this WPEffortReportController
     * @param showReport the showReport to set
     */
    public void setShowReport(Boolean showReport) {
        this.showReport = showReport;
    }
    
}