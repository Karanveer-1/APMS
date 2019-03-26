package controller;

import java.io.IOException;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.Timesheet;
import model.TimesheetPK;
import model.TimesheetRow;
import model.TimesheetRowPK;
import model.TimesheetRowState;
import model.TimesheetState;
import utils.DateUtils;

@Named("timesheetController")
@SessionScoped
public class TimesheetController implements Serializable {

    @Inject
    private DatabaseController database;
    private List<Timesheet> timesheets;

    private Timesheet editTimesheet;
    private List<TimesheetRow> editTimesheetRows;

    private Employee currentEmployee;

    /*
     * PostConstruct cannot happen if phase listener is overwritten by another
     * event!
     */
    public void init() {
        try {
            currentEmployee = getLoggedInEmployee();

            if (currentEmployee == null) {
                return;
            }

            timesheets = database.getTimesheets(currentEmployee.getEmpNumber());

        } catch (NullPointerException e) {
            // e.printStackTrace();
        }
    }

    public String addTimesheet(Date date) {
        date = date == null ? DateUtils.today() : date;
        TimesheetPK pk = new TimesheetPK(currentEmployee.getEmpNumber(),
                DateUtils.getTimesheetStartDate(date));
        editTimesheet = new Timesheet(pk, null, null, TimesheetState.DRAFT,
                null);
        editTimesheetRows = new ArrayList<TimesheetRow>();

        return "EditTimesheet.xhtml?faces-redirect=true";
    }

    public String editTimesheet(Timesheet t) {
        editTimesheet = t;
        editTimesheetRows = database.getTimesheetRows(
                t.getTimesheetPk().getEmpNo(),
                t.getTimesheetPk().getStartDate());

        return "EditTimesheet.xhtml?faces-redirect=true";
    }

    public String saveTimesheet() {
        for (TimesheetRow row : editTimesheetRows) {
            row.setState(TimesheetRowState.PENDING);
        }

        editTimesheet.setState(TimesheetState.DRAFT);

        database.removeTimesheetRows(editTimesheet);

        database.addIfNotExistTimesheetRows(editTimesheetRows);
        database.addTimesheetIfNotExist(editTimesheet, true);

        timesheets = database.getTimesheets(currentEmployee.getEmpNumber());

        return "Timesheets.xhtml?faces-redirect=true";
    }

    public String discardTimesheetChanges() {
        editTimesheet = null;
        editTimesheetRows = null;

        timesheets = database.getTimesheets(currentEmployee.getEmpNumber());

        return "Timesheets.xhtml?faces-redirect=true";
    }

    public void deleteTimesheet(Timesheet t) {
        database.removeTimesheet(t);
        database.removeTimesheetRows(
                database.getTimesheetRows(t.getTimesheetPk().getEmpNo(),
                        t.getTimesheetPk().getStartDate()));
        timesheets = database.getTimesheets(currentEmployee.getEmpNumber());
    }

    public void addTimesheetRow() {

        TimesheetRowPK pk = new TimesheetRowPK(currentEmployee.getEmpNumber(),
                DateUtils.getTimesheetStartDate(
                        editTimesheet.getTimesheetPk().getStartDate()),
                null, null);
        TimesheetRow row = new TimesheetRow();

        row.setTimesheetRowPk(pk);
        row.setState(TimesheetRowState.DRAFT);

        editTimesheetRows.add(row);
    }

    public boolean hasTimesheetForWeek(Date date) {
        for (Timesheet timesheet : timesheets) {
            Date start = DateUtils.getTimesheetStartDate(date);
            Date end = DateUtils.getTimesheetEndDate(date);

            if (DateUtils.isWithinRange(
                    timesheet.getTimesheetPk().getStartDate(), start, end)) {
                return true;
            }
        }

        return false;
    }

    public Date calendarEditMinDate() {
        return DateUtils.getTimesheetStartDate(
                editTimesheet.getTimesheetPk().getStartDate());
    }

    public Date calendarEditMaxDate() {
        return DateUtils.getTimesheetEndDate(
                editTimesheet.getTimesheetPk().getStartDate());
    }

    public Date calendarCurrentTimesheetStartDate() {
        return DateUtils.getTimesheetStartDate(DateUtils.today());
    }

    public boolean canEditTimesheet(Timesheet t) {
        Date start = DateUtils.getTimesheetStartDate(DateUtils.today());

        if (t.getTimesheetPk().getStartDate().compareTo(start) >= 0
                && !t.getState().equalsIgnoreCase(TimesheetState.SUBMTTED)
                && !t.getState().equalsIgnoreCase(TimesheetState.APPROVED)) {
            return true;
        }

        return false;
    }

    public boolean canCreateTimesheet(Date selectedDate) {
        return hasTimesheetForWeek(
                selectedDate == null ? DateUtils.today() : selectedDate);
    }

    public boolean canSubmitTimesheet(Timesheet t) {
        if (!t.getState().equalsIgnoreCase(TimesheetState.SUBMTTED)
                && !t.getState().equalsIgnoreCase(TimesheetState.APPROVED)) {
            return true;
        }

        return false;
    }

    public boolean canCancelSubmitTimesheet(Timesheet t) {
        if (t.getState().equalsIgnoreCase(TimesheetState.SUBMTTED)) {
            return true;
        }

        return false;
    }

    private static Employee getLoggedInEmployee() {
        return (Employee) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(LoginController.USER_KEY);
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

    public void submitTimesheet(Timesheet t) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA",
                    "SUN");
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

        t.setState(TimesheetState.SUBMTTED);
        database.updateTimesheet(t);
    }

    public void cancelSubmitTimesheet(Timesheet t) {
        model.Signature sig = database.findSignature(t.getTimesheetPk());
        if (sig != null) {
            database.removeSignature(sig);
        }
        t.setState(TimesheetState.DRAFT);
        database.updateTimesheet(t);
    }

}