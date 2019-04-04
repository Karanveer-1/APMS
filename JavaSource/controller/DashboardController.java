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
	
	@PostConstruct
	public void init() {
		employeeCount = database.countEmployees();
	}
	
	public long getEmployeeCount () {
		return employeeCount;
	}
}