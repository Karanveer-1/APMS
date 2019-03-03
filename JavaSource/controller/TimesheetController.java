package controller;

import java.io.Serializable;
import java.time.Instant;
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
import utils.DateUtils;

@Named("timesheetController")
@SessionScoped
public class TimesheetController implements Serializable {

    @Inject
    private DatabaseController database;
    private List<Timesheet> timesheets;

    private Timesheet editTimesheet;
    private List<TimesheetRow> editTimesheetRows;

    @PostConstruct
    public void init() {
        timesheets = database.getTimesheets();
    }

    public String addTimesheet() {
        TimesheetPK pk = new TimesheetPK(getLoggedInEmployee().getEmpNumber(),
                Date.from(Instant.now()));

        editTimesheet = new Timesheet(pk, null, null, null, null);
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
        // TODO: Validate
        // TODO: Merge to database
        // TODO: Update timesheets list using database method

        timesheets.add(editTimesheet);

        return "Timesheets.xhtml?faces-redirect=true";
    }
    
    public String discardTimesheetChanges() {
        editTimesheet = null;
        editTimesheetRows = null;
        
        return "Timesheets.xhtml?faces-redirect=true";
    }

    public boolean hasTimesheetForCurrentWeek() {
        for (Timesheet timesheet : timesheets) {
            if (DateUtils.isWithinWeekOfYear(
                    timesheet.getTimesheetPk().getStartDate(),
                    Date.from(Instant.now()))) {
                return true;
            }
        }

        return false;
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

    private Employee getLoggedInEmployee() {
        return (Employee) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(LoginController.USER_KEY);
    }
}
