package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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

    @PostConstruct
    public void init() {
        currentEmployee = getLoggedInEmployee();
        timesheets = database.getTimesheets(currentEmployee.getEmpNumber());
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
                && !t.getState().equalsIgnoreCase(TimesheetState.SUBMTTED)) {
            return true;
        }

        return false;
    }

    public boolean canCreateTimesheet(Date selectedDate) {
        return hasTimesheetForWeek(
                selectedDate == null ? DateUtils.today() : selectedDate);
    }

    public boolean canSubmitTimesheet(Timesheet t) {
        if (!t.getState().equalsIgnoreCase(TimesheetState.SUBMTTED)) {
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

    public void submitTimesheet(Timesheet t) {
        t.setState(TimesheetState.SUBMTTED);
        database.updateTimesheet(t);
//        timesheets = database.getTimesheets(currentEmployee.getEmpNumber());
    }

    public void cancelSubmitTimesheet(Timesheet t) {
        t.setState(TimesheetState.DRAFT);
        database.updateTimesheet(t);
//        timesheets = database.getTimesheets(currentEmployee.getEmpNumber());
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
}