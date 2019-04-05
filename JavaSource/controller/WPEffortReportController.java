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
    
    private float P1;
    private float P2;
    private float P3;
    private float P4;
    private float P5;
    private float P6;
    private float DS;
    private float SS;
    private float JS;


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

    public float getTotalP1() {
        if(wpid == null) {
            return 0;
        }
        List<Employee> emps = database.getEmployees("P1");
        return getTotal(emps);
    }

    public float getTotalP2() {
        if(wpid == null) {
            return 0;
        }
        List<Employee> emps = database.getEmployees("P2");
        return getTotal(emps);
    }

    public float getTotalP3() {
        if(wpid == null) {
            return 0;
        }
        List<Employee> emps = database.getEmployees("P3");
        return getTotal(emps);
    }

    public float getTotalP4() {
        if(wpid == null) {
            return 0;
        }
        List<Employee> emps = database.getEmployees("P4");
        return getTotal(emps);
    }

    public float getTotalP5() {
        if(wpid == null) {
            return 0;
        }
        List<Employee> emps = database.getEmployees("P5");
        return getTotal(emps);
    }

    public float getTotalP6() {
        if(wpid == null) {
            return 0;
        }
        List<Employee> emps = database.getEmployees("P6");
        return getTotal(emps);
    }

    public float getTotalDS() {
        if(wpid == null) {
            return 0;
        }
        List<Employee> emps = database.getEmployees("DS");
        return getTotal(emps);
    }

    public float getTotalJS() {
        if(wpid == null) {
            return 0;
        }
        List<Employee> emps = database.getEmployees("JS");
        return getTotal(emps);
    }

    public float getTotalSS() {
        if(wpid == null) {
            return 0;
        }
        List<Employee> emps = database.getEmployees("SS");
        return getTotal(emps);
    }

    public float getTotal(List<Employee> emps) {
        float total = 0;
        for(Employee e : emps) {
            float sum =  (float) database.getTimesheetRows()
                    .stream()
                    .filter(r -> r.getTimesheetRowPk().getEmpNo() == e.getEmpNumber())
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
            total += sum;
        }
        return total;
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

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the p1
     */
    public float getP1() {
        P1 = getTotalP1();
        return P1;
    }

    /**
     * Sets the p1 for this WPEffortReportController
     * @param p1 the p1 to set
     */
    public void setP1(float p1) {
        P1 = p1;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the p2
     */
    public float getP2() {
        P2 = getTotalP2();
        return P2;
    }

    /**
     * Sets the p2 for this WPEffortReportController
     * @param p2 the p2 to set
     */
    public void setP2(float p2) {
        P2 = p2;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the p3
     */
    public float getP3() {
        P3 = getTotalP3();
        return P3;
    }

    /**
     * Sets the p3 for this WPEffortReportController
     * @param p3 the p3 to set
     */
    public void setP3(float p3) {
        P3 = p3;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the p4
     */
    public float getP4() {
        P4 = getTotalP4();
        return P4;
    }

    /**
     * Sets the p4 for this WPEffortReportController
     * @param p4 the p4 to set
     */
    public void setP4(float p4) {
        P4 = p4;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the p5
     */
    public float getP5() {
        P5 = getTotalP5();
        return P5;
    }

    /**
     * Sets the p5 for this WPEffortReportController
     * @param p5 the p5 to set
     */
    public void setP5(float p5) {
        P5 = p5;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the p6
     */
    public float getP6() {
        P6 = getTotalP6();
        return P6;
    }

    /**
     * Sets the p6 for this WPEffortReportController
     * @param p6 the p6 to set
     */
    public void setP6(float p6) {
        P6 = p6;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the dS
     */
    public float getDS() {
        DS = getTotalDS();
       return DS;
    }

    /**
     * Sets the dS for this WPEffortReportController
     * @param dS the dS to set
     */
    public void setDS(float dS) {
        DS = dS;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the sS
     */
    public float getSS() {
        SS = getTotalSS();
        return SS;
    }

    /**
     * Sets the sS for this WPEffortReportController
     * @param sS the sS to set
     */
    public void setSS(float sS) {
        SS = sS;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the jS
     */
    public float getJS() {
        JS = getTotalJS();
        return JS;
    }

    /**
     * Sets the jS for this WPEffortReportController
     * @param jS the jS to set
     */
    public void setJS(float jS) {
        JS = jS;
    }

}