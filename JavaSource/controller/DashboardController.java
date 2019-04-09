package controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;

@Named("dashboardController")
@ViewScoped
public class DashboardController implements Serializable {
	@Inject
	private DatabaseController database;
//	@Inject
//	private EmployeeController currentEmployee;
	private long employeeCount;
	private long timesheetCount;
	private long approvedTimesheetCount;
	private long rejectedTimesheetCount;
	private long projectCount;
	
	private Employee currentEmployee;
	
	@PostConstruct
	public void init() {
		try {
            currentEmployee = getLoggedInEmployee();

            if (currentEmployee == null) {
                return;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
		
		employeeCount = database.countEmployees();
		int id = currentEmployee.getEmpNumber();
		System.out.println(id);
		timesheetCount = database.countTimesheets(id);
		approvedTimesheetCount = database.countApprovedTimesheets(id);
		rejectedTimesheetCount = database.countRejectedTimesheets(id);
		projectCount = database.countProjects();
	}
	
    private Employee getLoggedInEmployee() {
        return (Employee) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(LoginController.USER_KEY);
    }
	
	public long getEmployeeCount() {
		return employeeCount;
	}

	public long getTimesheetCount() {
		return timesheetCount;
	}

	public long getApprovedTimesheetCount() {
		return approvedTimesheetCount;
	}

	public long getRejectedTimesheetCount() {
		return rejectedTimesheetCount;
	}
	
	public long getProjectCount() {
		return projectCount;
	}
}