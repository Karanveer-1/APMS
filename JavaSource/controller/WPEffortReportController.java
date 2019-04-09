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
import model.WPEffortRow;
import model.WPNeed;
import model.WPNeedPK;
import model.WorkPackage;
import model.WorkPackagePK;
import utils.DateUtils;

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
    private List<WPEffortRow> rows;

    private Integer proNo;
    private String wpid;
    private WorkPackage wp;
    private boolean show;
    private String[] pLevels = {"P1", "P2", "P3", "P4", "P5", "P6", "DS", "JS", "SS"};

    public void init() {
        projectNos = database.getAllProjectNo();
        show = false;
    }

    public void onProjectChange() {
        if (proNo != null) {
            wpids = getLeafWpids(proNo);
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

    private List<Employee> getParticipatingEmployees() {
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

    private float getSumHoursForRows(int empNo, Date date) {
        return (float) database.getTimesheetRows()
                .stream()
                .filter(r -> r.getTimesheetRowPk().getEmpNo() == empNo)
                .filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
                .filter(r -> r.getTimesheetRowPk().getWpid().equals(wpid))
                .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED))
                .filter(r -> r.getTimesheetRowPk().getStartDate().equals(date))
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

    private List<TimesheetRow> getRelaventTimesheetRows() {
        List<TimesheetRow> rows = database.getTimesheetRows()
                .stream()
                .filter(r -> r.getTimesheetRowPk().getProNo() == proNo)
                .filter(r -> r.getTimesheetRowPk().getWpid().equals(wpid))
                .filter(r -> r.getState().equalsIgnoreCase(TimesheetRowState.APPROVED))
                .collect(Collectors.toList());

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
            return new Date();
        }

        return database.getAllWp()
                .stream()
                .filter(wp -> wp.getProNo() == proNo)
                .filter(wp -> wp.getWpid().equals(wpid))
                .findFirst()
                .map(WorkPackage::getStartDate)
                .get();
    }

    public float getTotalForEmployee(int empNo) {
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

    public float getTotalForAllEmployees() {
        return (float) getParticipatingEmployees()
                .stream()
                .mapToDouble(e -> {
                    return getTotalForEmployee(e.getEmpNumber());
                })
                .sum();
    }

    public void generateReport() {
        wp = database.getWpByPk(proNo, wpid);
        rows = new ArrayList<>();
        show = true;

        List<Employee> participatingEmployees = getParticipatingEmployees();
        List<TimesheetRow> relaventRows = getRelaventTimesheetRows();

        List<Date> dates = relaventRows
                .stream()
                .map(r -> {
                    return r.getTimesheetRowPk().getStartDate();
                })
                .distinct()
                .collect(Collectors.toList());

        List<EmpPLevel> empPLevels = database.getEmpPLevels();
        List<WPNeed> wpNeeds = database.getWPNeeds();
        for(WPNeed need : wpNeeds) {
            System.out.println(proNo + " proNo: " + need.getWpNeedPK().getProNo());
            System.out.println(wpid + " wpid: " + need.getWpNeedPK().getWpid());
            if(need.getWpNeedPK().getProNo() == proNo && need.getWpNeedPK().getWpid().equals(wpid)) {
                float[] hours = new float[9];
                Date d = need.getWpNeedPK().getStartDate();
                hours[0] = getHoursByPLevel("P1", d);
                hours[1] = getHoursByPLevel("P2", d);
                hours[2] = getHoursByPLevel("P3", d);
                hours[3] = getHoursByPLevel("P4", d);
                hours[4] = getHoursByPLevel("P5", d);
                hours[5] = getHoursByPLevel("P6", d);
                hours[6] = getHoursByPLevel("DS", d);
                hours[7] = getHoursByPLevel("JS", d);
                hours[8] = getHoursByPLevel("SS", d);
                WPEffortRow row = new WPEffortRow(d, pLevels, hours, database.getWPNeedsByPK(d, wpid, proNo));
                System.out.println(row.toString());
                rows.add(row);
            }
        }
/*
        for(Date d : dates) {
            float[] hours = new float[9];
            hours[0] = getHoursByPLevel("P1", d);
            hours[1] = getHoursByPLevel("P2", d);
            hours[2] = getHoursByPLevel("P3", d);
            hours[3] = getHoursByPLevel("P4", d);
            hours[4] = getHoursByPLevel("P5", d);
            hours[5] = getHoursByPLevel("P6", d);
            hours[6] = getHoursByPLevel("DS", d);
            hours[7] = getHoursByPLevel("JS", d);
            hours[8] = getHoursByPLevel("SS", d);
            WPEffortRow row = new WPEffortRow(d, pLevels, hours, database.getWPNeedsByPK(d, wpid, proNo));
            System.out.println(row.toString());
            rows.add(row);
        }
        */
        System.out.println("size:" + rows.size());
    }
    public void closeDialog() {
        PrimeFaces.current().executeScript("PF('errorDialog').hide();");
    }
    
    public void addEntry(Date startDate) {
        Date customStartDate = DateUtils.getTimesheetStartDate(startDate);
        List<TimesheetRow> relaventRows = getRelaventTimesheetRows();
        List<Date> dates = relaventRows
                .stream()
                .map(r -> {
                    return r.getTimesheetRowPk().getStartDate();
                })
                .distinct()
                .collect(Collectors.toList());
        if(dates.contains(customStartDate)) {
            PrimeFaces.current().executeScript("PF('errorDialog').show();");

        } else {
            WPNeed tempWPNeed = new WPNeed(new WPNeedPK(proNo, customStartDate, wpid));
            database.addWPNeed(tempWPNeed);
        }
        generateReport();
    }


    public float getHoursByPLevel(String pLevel, Date d) {

        List<EmpPLevel> empPLevels = database.getEmpPLevels();
        float total = 0;
        List<String> relevantEmpNo = new ArrayList<String>();
        for(EmpPLevel ep : empPLevels) {
            if(ep.getpLevel().equals(pLevel)) {
                relevantEmpNo.add("" + ep.getEmpPLevelPK().getEmpNo());
            }
            for(String e : relevantEmpNo) {
                for(TimesheetRow tsr : database.getTimesheetRows()
                        .stream()
                        .collect(Collectors.toList())) {
                }

                float sum =  (float) database.getTimesheetRows()
                        .stream()
                        .filter(r -> r.getTimesheetRowPk().getStartDate().equals(DateUtils.getTimesheetStartDate(d)))
                        .filter(r -> (r.getTimesheetRowPk().getEmpNo() + "").equals(e))
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
        }

        return total;

    }
    

    public void saveEstimateP1(WPEffortRow row, int val) {
        row.getWpNeed().setReNeedP1(val);
        database.updateWPNeed(row.getWpNeed());
    }
    public void saveEstimateP2(WPEffortRow row, int val) {
        row.getWpNeed().setReNeedP2(val);
        database.updateWPNeed(row.getWpNeed());
    }
    public void saveEstimateP3(WPEffortRow row, int val) {
        row.getWpNeed().setReNeedP3(val);
        database.updateWPNeed(row.getWpNeed());
    }
    public void saveEstimateP4(WPEffortRow row, int val) {
        row.getWpNeed().setReNeedP4(val);
        database.updateWPNeed(row.getWpNeed());
    }
    public void saveEstimateP5(WPEffortRow row, int val) {
        row.getWpNeed().setReNeedP5(val);
        database.updateWPNeed(row.getWpNeed());
    }
    public void saveEstimateP6(WPEffortRow row, int val) {
        row.getWpNeed().setReNeedP6(val);
        database.updateWPNeed(row.getWpNeed());
    }
    public void saveEstimateJS(WPEffortRow row, int val) {
        row.getWpNeed().setReNeedJS(val);
        database.updateWPNeed(row.getWpNeed());
    }
    public void saveEstimateDS(WPEffortRow row, int val) {
        row.getWpNeed().setReNeedDS(val);
        database.updateWPNeed(row.getWpNeed());
    }
    public void saveEstimateSS(WPEffortRow row, int val) {
        row.getWpNeed().setReNeedSS(val);
        database.updateWPNeed(row.getWpNeed());
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

    public List<WPEffortRow> getRows() {
        return rows;
    }

    public void setRows(List<WPEffortRow> rows) {
        this.rows = rows;
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
     * @return the pLevels
     */
    public String[] getpLevels() {
        return pLevels;
    }

    /**
     * Sets the pLevels for this WPEffortReportController
     * @param pLevels the pLevels to set
     */
    public void setpLevels(String[] pLevels) {
        this.pLevels = pLevels;
    }

    /**
     * Returns the {bare_field_name} for this WPEffortReportController.
     * @return the show
     */
    public boolean isShow() {
        return show;
    }

    /**
     * Sets the show for this WPEffortReportController
     * @param show the show to set
     */
    public void setShow(boolean show) {
        this.show = show;
    }
}
