package controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("dashboardController")
@ViewScoped
public class DashboardController implements Serializable {
	@Inject
	private DatabaseController database;
	
	private long employeeCount;
	private long timesheetCount;
	private long projectCount;
	
	@PostConstruct
	public void init() {
		employeeCount = database.countEmployees();
		timesheetCount = database.countTimesheets();
		projectCount = database.countProjects();
	}
	
	public long getEmployeeCount() {
		return employeeCount;
	}
	
	public long getTimesheetCount() {
		return timesheetCount;
	}
	
	public long getProjectCount() {
		return projectCount;
	}
}