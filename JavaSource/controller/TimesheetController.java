package controller;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.Timesheet;
import model.TimesheetPK;

@Named("timesheetController")
@RequestScoped
public class TimesheetController implements Serializable {

    @Inject
    private DatabaseController database;
    private List<Timesheet> timesheets;
    
    private Timesheet editTimesheet;

    @PostConstruct
    public void init() {
        timesheets = database.getTimesheets();
    }

    public String addTimesheet() {
        TimesheetPK pk = new TimesheetPK(getLoggedInEmployee().getEmpNumber(),
                Date.from(Instant.now()));
        
        editTimesheet = new Timesheet(pk, null, null, null, null);
        
        return "EditTimesheet.xhtml?faces-redirect=true";
    }
    
    public String saveTimesheet() {
        //TODO: Validate
        //TODO: Merge to database
        //TODO: Update timesheets list using database method
        
        timesheets.add(editTimesheet);
        
        return "Timesheets.xhtml?faces-redirect=true";
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

    private Employee getLoggedInEmployee() {
        return (Employee) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(LoginController.USER_KEY);
    }
}
