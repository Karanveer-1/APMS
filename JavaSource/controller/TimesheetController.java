package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

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

    private Employee currentEmployee = getLoggedInEmployee();
    
    @PostConstruct
    public void init() {
        timesheets = database.getTimesheets(currentEmployee.getEmpNumber());
    }

    public String addTimesheet(Date date) {
        date = date == null ? DateUtils.today() : date;
        TimesheetPK pk = new TimesheetPK(currentEmployee.getEmpNumber(),
                DateUtils.getTimesheetStartDate(date));
        editTimesheet = new Timesheet(pk, null, null,
                TimesheetState.Draft.toString(), null);
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
            row.setState(TimesheetRowState.Pending.toString());
        }

        editTimesheet.setState(TimesheetState.Draft.toString());

        database.removeTimesheetRows(editTimesheet);

        database.addIfNotExistTimesheetRows(editTimesheetRows);
        database.addTimesheetIfNotExist(editTimesheet, true);

        timesheets = database.getTimesheets(currentEmployee.getEmpNumber());

        return "Timesheets.xhtml?faces-redirect=true";
    }

    public String discardTimesheetChanges() {
        editTimesheet = null;
        editTimesheetRows = null;

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
        row.setState(TimesheetRowState.Draft.toString());

        editTimesheetRows.add(row);
    }

    public boolean canEditTimesheet(Timesheet t) {
        Date start = DateUtils.getTimesheetStartDate(DateUtils.today());

        if (t.getTimesheetPk().getStartDate().compareTo(start) >= 0) {
            return true;
        }

        return false;
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

    public boolean canCreateTimesheet(Date selectedDate) {
        return hasTimesheetForWeek(selectedDate == null ? DateUtils.today() : selectedDate);
    }
    
    public void submitTimesheet(Timesheet t) {
        t.setState(TimesheetState.Submitted.toString());
        database.updateTimesheet(t);
    }
    
    public void cancelSubmitTimesheet(Timesheet t) {
        t.setState(TimesheetState.Draft.toString());
        database.updateTimesheet(t);
    }
}
