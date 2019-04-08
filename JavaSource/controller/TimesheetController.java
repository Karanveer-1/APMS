package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.Project;
import model.Timesheet;
import model.TimesheetPK;
import model.TimesheetRow;
import model.TimesheetRowPK;
import model.TimesheetRowState;
import model.TimesheetState;
import utils.DateUtils;
import utils.Pair;

// WPEmp Table
// Make sure that the WP that can be chosen are only the ones assigned to the current employee!
@Named("timesheetController")
@SessionScoped
public class TimesheetController implements Serializable {
    @Inject
    private AuthenticationController auth;

    @Inject
    private DatabaseController database;
    private List<Timesheet> timesheets;

    private Timesheet editTimesheet;
    private List<TimesheetRow> editTimesheetRows;

    private Employee currentEmployee;

    private List<Integer> projectNumbers;

    public void init() {
        try {
            currentEmployee = getLoggedInEmployee();

            if (currentEmployee == null) {
                return;
            }

            timesheets = updateUserTimesheets();

        } catch (NullPointerException e) {
            // e.printStackTrace();
        }
    }

    public List<Timesheet> updateUserTimesheets() {
        if (auth.isUserSystemAdmin()) {
            return database.getTimesheets();
        } else {
            return database.getTimesheets(currentEmployee.getEmpNumber());
        }
    }

    public String addTimesheet(Date date) {
        date = date == null ? DateUtils.today() : date;
        TimesheetPK pk = new TimesheetPK(currentEmployee.getEmpNumber(), DateUtils.getTimesheetStartDate(date));
        editTimesheet = new Timesheet(pk, null, null, TimesheetState.DRAFT, null);
        editTimesheetRows = new ArrayList<TimesheetRow>();

        projectNumbers = getRelaventProNos();

        return "EditTimesheet.xhtml?faces-redirect=true";
    }

    public List<Integer> getRelaventProNos() {
        return database.getAllProjectsbyEmpNo(currentEmployee.getEmpNumber()).stream().map(Project::getProNo)
                .collect(Collectors.toList());
    }

    public String editTimesheet(Timesheet t) {
        editTimesheet = t;
        editTimesheetRows = database.getTimesheetRows(t.getTimesheetPk().getEmpNo(), t.getTimesheetPk().getStartDate());

        projectNumbers = database.getAllProjectNo();

        return "EditTimesheet.xhtml?faces-redirect=true";
    }

    public String viewTimesheet(Timesheet t) {
        editTimesheet = t;
        editTimesheetRows = database.getTimesheetRows(t.getTimesheetPk().getEmpNo(), t.getTimesheetPk().getStartDate());

        return "ViewTimesheet.xhtml?faces-redirect=true";
    }

    public List<String> getRelaventWpIds(TimesheetRow row) {
        if (row.getTimesheetRowPk().getProNo() == null) {
            row.getTimesheetRowPk().setWpid(null);
            return new ArrayList<String>();
        }

        List<String> wpids = database
                .getAllEmpAssignedWpid(row.getTimesheetRowPk().getProNo(), currentEmployee.getEmpNumber()).stream()
                .filter(wp -> database.getWPById(wp) != null && database.getWPById(wp).getState().equals("OPEN"))
                .collect(Collectors.toList());

        if (wpids.isEmpty()) {
            row.getTimesheetRowPk().setWpid(null);
        }

        return wpids;
    }

    public String saveTimesheet() {
        if (!isValidTimesheet()) {
            return null;
        }

        editTimesheet.setState(TimesheetState.DRAFT);

        database.updateTimesheet(editTimesheet);

        database.removeTimesheetRows(editTimesheet);

        database.addIfNotExistTimesheetRows(editTimesheetRows);
        database.addTimesheetIfNotExist(editTimesheet, true);

        updateTimesheetRowsState(editTimesheetRows, TimesheetRowState.DRAFT);
        timesheets = updateUserTimesheets();

        return "Timesheets.xhtml?faces-redirect=true";
    }

    public boolean isValidTimesheet() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean hasError = false;

        for (TimesheetRow row : editTimesheetRows) {
            if (!isTimesheetRowValuesInRange(row)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Timesheet values must be between 0.0 and 24.0.", null);
                context.addMessage(null, msg);
                hasError = true;
                break;
            }
        }

        for (TimesheetRow row : editTimesheetRows) {
            if (!isTimesheetRowValuesValidMultiple(row)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Timesheet values must be multiples of 0.25.", null);
                context.addMessage(null, msg);
                hasError = true;
                break;
            }
        }

        for (TimesheetRow row : editTimesheetRows) {
            if (isTimesheetRowKeysNull(row)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Timesheet can not have any empty project number or workpackage id.", null);
                context.addMessage(null, msg);
                hasError = true;
                break;
            }
        }

        if (hasDuplicateTimesheetRow(editTimesheetRows)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Duplicate project number and workpackage id combination.", null);
            context.addMessage(null, msg);
            hasError = true;
        }

        if (hasError) {
            return false;
        }

        return true;
    }

    private List<Float> getTimesheetRowValues(TimesheetRow r) {
        return new ArrayList<Float>() {
            {
                add(r.getSat());
                add(r.getSun());
                add(r.getMon());
                add(r.getTue());
                add(r.getWed());
                add(r.getThu());
                add(r.getFri());
            }
        };
    }

    public boolean isTimesheetRowValuesValidMultiple(TimesheetRow r) {
        List<Float> values = getTimesheetRowValues(r);

        for (float i : values) {
            if (i % 0.25 != 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isTimesheetRowValuesInRange(TimesheetRow r) {
        List<Float> values = getTimesheetRowValues(r);

        for (float i : values) {
            BigDecimal a = new BigDecimal(i);

            BigDecimal min = new BigDecimal(0);
            BigDecimal max = new BigDecimal(24);

            if (a.compareTo(min) < 0 || a.compareTo(max) > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean hasDuplicateTimesheetRow(List<TimesheetRow> rows) {
        Set<Pair<Integer, String>> sets = new HashSet<>();

        for (TimesheetRow row : rows) {
            TimesheetRowPK pk = row.getTimesheetRowPk();
            if (!sets.add(new Pair<Integer, String>(pk.getProNo(), pk.getWpid()))) {
                return true;
            }
        }

        return false;
    }

    public boolean isTimesheetRowKeysNull(TimesheetRow row) {
        TimesheetRowPK pk = row.getTimesheetRowPk();

        if (pk.getProNo() == null || pk.getWpid() == null) {
            return true;
        }

        return false;
    }

    public String discardTimesheetChanges() {
        editTimesheet = null;
        editTimesheetRows = null;

        timesheets = updateUserTimesheets();

        return "Timesheets.xhtml?faces-redirect=true";
    }

    public void deleteTimesheet(Timesheet t) {
        database.removeTimesheet(t);
        database.removeTimesheetRows(
                database.getTimesheetRows(t.getTimesheetPk().getEmpNo(), t.getTimesheetPk().getStartDate()));

        timesheets = updateUserTimesheets();
    }

    public void addTimesheetRow() {
        TimesheetRowPK pk = new TimesheetRowPK(currentEmployee.getEmpNumber(),
                DateUtils.getTimesheetStartDate(editTimesheet.getTimesheetPk().getStartDate()), null, null);

        TimesheetRow row = new TimesheetRow();

        row.setTimesheetRowPk(pk);
        row.getTimesheetRowPk().setStartDate(getStartDateCustom(editTimesheet.getTimesheetPk().getStartDate()));
        row.setState(TimesheetRowState.DRAFT);

        List<Integer> proNos = database.getAllProjectNo();

        if (!proNos.isEmpty()) {
            row.getTimesheetRowPk().setProNo(database.getAllProjectNo().get(0));

            List<String> wpids = database.getWpIdByProjectNo(proNos.get(0));

            if (!wpids.isEmpty()) {
                row.getTimesheetRowPk().setWpid(wpids.get(0));
            }
        }

        editTimesheetRows.add(row);
    }

    public void deleteTimesheetRow(TimesheetRow row) {
        editTimesheetRows.remove(row);
    }

    public boolean hasTimesheetForWeek(Date date) {
        for (Timesheet timesheet : timesheets) {
            Date start = DateUtils.getTimesheetStartDate(date);
            Date end = DateUtils.getTimesheetEndDate(date);

            if (DateUtils.isWithinRange(timesheet.getTimesheetPk().getStartDate(), start, end)
                    && timesheet.getTimesheetPk().getEmpNo() == currentEmployee.getEmpNumber()) {
                return true;
            }
        }

        return false;
    }

    public Date calendarEditMinDate() {
        return DateUtils.getTimesheetStartDate(editTimesheet.getTimesheetPk().getStartDate());
    }

    public Date calendarEditMaxDate() {
        return DateUtils.getTimesheetEndDate(editTimesheet.getTimesheetPk().getStartDate());
    }

    public Date calendarCurrentTimesheetStartDate() {
        return DateUtils.getTimesheetStartDate(DateUtils.today());
    }

    public Date getStartDateCustom(Date date) {
        return DateUtils.getTimesheetStartDate(date);
    }

    public boolean canEditTimesheet(Timesheet t) {
        if (!t.getState().equalsIgnoreCase(TimesheetState.PENDING)
                && !t.getState().equalsIgnoreCase(TimesheetState.APPROVED)) {
            return true;
        }

        return false;
    }

    public boolean canCreateTimesheet(Date selectedDate) {
        return hasTimesheetForWeek(selectedDate == null ? DateUtils.today() : selectedDate);
    }

    public boolean canSubmitTimesheet(Timesheet t) {
        if (!t.getState().equalsIgnoreCase(TimesheetState.PENDING)
                && !t.getState().equalsIgnoreCase(TimesheetState.APPROVED)) {
            return true;
        }

        return false;
    }

    public boolean canCancelSubmitTimesheet(Timesheet t) {
        if (t.getState().equalsIgnoreCase(TimesheetState.PENDING)) {
            return true;
        }

        return false;
    }

    private static Employee getLoggedInEmployee() {
        return (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .get(LoginController.USER_KEY);
    }

    public List<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public Timesheet getEditTimesheet() {
        return editTimesheet;
    }

    public void setEditTimesheet(Timesheet editTimesheet) {
        this.editTimesheet = editTimesheet;
    }

    public List<TimesheetRow> getEditTimesheetRows() {
        return editTimesheetRows;
    }

    public void setEditTimesheetRows(List<TimesheetRow> editTimesheetRows) {
        this.editTimesheetRows = editTimesheetRows;
    }

    public List<Integer> getProjectNumbers() {
        return projectNumbers;
    }

    public void setProjectNumbers(List<Integer> projectNumbers) {
        this.projectNumbers = projectNumbers;
    }
    
    private boolean hasEnoughHours(Timesheet t) {
        List<TimesheetRow> rows = database.getTimesheetRows(t);
        float totalHours = (float) rows
                .stream()
                .mapToDouble(r -> {
                    return getTimesheetRowValues(r)
                            .stream()
                            .mapToDouble(Float::doubleValue)
                            .sum();
                })
                .sum();
                
        return totalHours >= 40 * rows.size() ? true : false;
    }

    public void submitTimesheet(Timesheet t) {
        if (!hasEnoughHours(t)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "All rows must have at least 40 hours.", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);

            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();

            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(priv);

            String data = t.toString();
            System.out.println("Signing: " + data);

            byte[] dataBytes = data.getBytes();

            dsa.update(dataBytes);
            byte[] signature = dsa.sign();
            byte[] publicKey = pub.getEncoded();

            model.Signature sig = new model.Signature(signature, publicKey);
            sig.setTimesheetPk(t.getTimesheetPk());
            database.addSignature(sig);

        } catch (Exception e) {
            e.printStackTrace();
        }

        t.setState(TimesheetState.PENDING);
        database.updateTimesheet(t);
        updateTimesheetRowsState(database.getTimesheetRows(t), TimesheetState.PENDING);
    }

    public void cancelSubmitTimesheet(Timesheet t) {
        model.Signature sig = database.findSignature(t.getTimesheetPk());
        if (sig != null) {
            database.removeSignature(sig);
        }
        t.setState(TimesheetState.DRAFT);
        database.updateTimesheet(t);
        updateTimesheetRowsState(database.getTimesheetRows(t), TimesheetState.DRAFT);
    }

    private void updateTimesheetRowsState(List<TimesheetRow> rows, String state) {
        for (TimesheetRow row : rows) {
            row.setState(state);
        }

        database.updateTimesheetRows(rows);
    }
}