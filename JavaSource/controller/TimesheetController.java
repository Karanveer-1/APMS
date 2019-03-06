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

    private Employee currentEmployee = getLoggedInEmployee();

    @PostConstruct
    public void init() {
        timesheets = database.getTimesheets(currentEmployee.getEmpNumber());
    }

    public String addTimesheet() {
        TimesheetPK pk = new TimesheetPK(currentEmployee.getEmpNumber(),
                DateUtils.getTimesheetStartDate(DateUtils.today()));

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

        editTimesheet.setState(TimesheetState.Pending.toString());

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
                DateUtils.getTimesheetStartDate(DateUtils.today()), null, null);
        TimesheetRow row = new TimesheetRow();
        row.setTimesheetRowPk(pk);
        row.setState(TimesheetRowState.Draft.toString());

        editTimesheetRows.add(row);
    }

    public boolean hasTimesheetForCurrentWeek() {
        for (Timesheet timesheet : timesheets) {
            Date start = DateUtils.getTimesheetStartDate(DateUtils.today());
            Date end = DateUtils.getTimesheetEndDate(DateUtils.today());

            if (DateUtils.isWithinRange(
                    timesheet.getTimesheetPk().getStartDate(), start, end)) {
                return true;
            }
        }

        return false;
    }

    public boolean timesheetIsInCurrentWeek(Timesheet t) {
        Date start = DateUtils.getTimesheetStartDate(DateUtils.today());
        Date end = DateUtils.getTimesheetEndDate(DateUtils.today());

        return DateUtils.isWithinRange(t.getTimesheetPk().getStartDate(), start,
                end);
    }

    public Date calendarMinDate() {
        return DateUtils.getTimesheetStartDate(
                editTimesheet.getTimesheetPk().getStartDate());
    }

    public Date calendarMaxDate() {
        return DateUtils.getTimesheetEndDate(
                editTimesheet.getTimesheetPk().getStartDate());
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

    private static Employee getLoggedInEmployee() {
        return (Employee) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(LoginController.USER_KEY);
    }
}
