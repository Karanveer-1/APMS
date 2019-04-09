package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.Timesheet;
import model.TimesheetRow;
import model.TimesheetRowState;
import model.TimesheetState;

@Named("approveTimesheetController")
@SessionScoped
public class ApproveTimesheetController implements Serializable {
    
    @Inject
    private DatabaseController database;
    
    @Inject
    private AuthenticationController auth;
    
    private List<Timesheet> submittedTimesheets;

    private Employee currentEmployee;
    private Timesheet viewTimesheet;
    private List<TimesheetRow> viewTimesheetRows;

    public void init() {
        try {
            currentEmployee = getLoggedInEmployee();

            if (currentEmployee == null) {
                return;
            }

            submittedTimesheets = databaseGetSubmittedTimesheets();

        } catch (NullPointerException e) {
            // e.printStackTrace();
        }
    }

    public String viewTimesheet(Timesheet t) {
        viewTimesheet = t;
        viewTimesheetRows = database.getTimesheetRows(t.getTimesheetPk().getEmpNo(),
                t.getTimesheetPk().getStartDate());
        
        return "ApproveTimesheetView.xhtml?faces-redirect=true";
    }

    public String approveTimesheet() {
        Timesheet t = viewTimesheet;
        t.setState(TimesheetState.APPROVED);
        t.setApprovedEmpNo(currentEmployee.getEmpNumber());
        database.updateTimesheet(t);
        submittedTimesheets = databaseGetSubmittedTimesheets();
        updateTimesheetRowsState(viewTimesheetRows, TimesheetRowState.APPROVED);
        return "ApproveTimesheet.xhtml?faces-redirect=true";
    }

    public String returnTimesheet() {
        Timesheet t = viewTimesheet;
        t.setState(TimesheetState.RETURNED);
        database.updateTimesheet(t);
        submittedTimesheets = databaseGetSubmittedTimesheets();
        updateTimesheetRowsState(viewTimesheetRows, TimesheetRowState.RETURNED);
        return "ApproveTimesheet.xhtml?faces-redirect=true";
    }
    
    private void updateTimesheetRowsState(List<TimesheetRow> rows,
        String state) {
        for (TimesheetRow row : rows) {
            row.setState(state);
        }

        database.updateTimesheetRows(rows);
    }

    private List<Timesheet> databaseGetSubmittedTimesheets() {
        if (auth.isUserSystemAdmin()) {
            return database.getSubmittedTimesheets();
        }
        
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

    public List<TimesheetRow> getViewTimesheetRows() {
        return viewTimesheetRows;
    }

    public void setViewTimesheetRows(List<TimesheetRow> viewTimesheetRows) {
        this.viewTimesheetRows = viewTimesheetRows;
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    public Timesheet getViewTimesheet() {
        return viewTimesheet;
    }

    public void setViewTimesheet(Timesheet viewTimesheet) {
        this.viewTimesheet = viewTimesheet;
    }    
}
