package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.Timesheet;
import model.TimesheetState;

@Named("approveTimesheetController")
@ViewScoped
public class ApproveTimesheetController implements Serializable {
    @Inject
    private DatabaseController database;
    private List<Timesheet> submittedTimesheets;

    private Employee currentEmployee;

    @PostConstruct
    public void init() {
        currentEmployee = getLoggedInEmployee();
        submittedTimesheets = databaseGetSubmittedTimesheets();
    }

    public void approveTimesheet(Timesheet t) {
        t.setState(TimesheetState.APPROVED);
        database.updateTimesheet(t);
        submittedTimesheets = databaseGetSubmittedTimesheets();
    }

    public void returnTimesheet(Timesheet t) {
        t.setState(TimesheetState.RETURNED);
        database.updateTimesheet(t);
        submittedTimesheets = databaseGetSubmittedTimesheets();
    }

    private List<Timesheet> databaseGetSubmittedTimesheets() {
        return database.getSubmittedTimesheets(currentEmployee.getEmpNumber());
    }

    private Employee getLoggedInEmployee() {
        return (Employee) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(LoginController.USER_KEY);
    }
    
    public List<Timesheet> getSubmittedTimesheets() {
        return submittedTimesheets;
    }

    public void setSubmittedTimesheets(List<Timesheet> submittedTimesheets) {
        this.submittedTimesheets = submittedTimesheets;
    }
}
