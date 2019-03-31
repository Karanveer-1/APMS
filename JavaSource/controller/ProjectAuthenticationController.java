package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
//import model.ProAssi;
import model.Project;
import model.WorkPackage;

@Named("paController")
@SessionScoped
public class ProjectAuthenticationController implements Serializable {

	@Inject
	private DatabaseController database;

	private Employee currentEmployee;

	public ProjectAuthenticationController() {

	}

	@PostConstruct
	public void init() {
		currentEmployee = getLoggedInEmployee();

	}

	public boolean isSupervisor() {
		List<Employee> empList = this.database.getEmployees();
		for (Employee emp : empList) {
			if (emp.getSuperEmpNo() == currentEmployee.getEmpNumber()) {
				return true;
			}
		}
		return false;
	}

	public boolean isProjectManager() {
		List<Project> allProject = this.database.getAllProjects();
		for (Project p : allProject) {
			if (currentEmployee.getEmpNumber() == p.getProMgrEmpNo()) {
				return true;
			}
		}
		return false;
	}

//	public boolean isProjectAssistant() {
//		List<ProAssi> allProAssi = this.database.getAllProAssi();
//		for (ProAssi pa : allProAssi) {
//			if (currentEmployee.getEmpNumber() == pa.getEmpNo()) {
//				return true;
//			}
//		}
//		return false;
//	}

	public boolean isPMorPA() {
		return isProjectManager() ;
	}

	public boolean isREEmp() {
		for (WorkPackage wp : this.database.getAllWp()) {
			if (currentEmployee.getEmpNumber() == wp.getReEmpNo()) {
				return true;
			}
		}
		return false;
	}

	private static Employee getLoggedInEmployee() {
		return (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(LoginController.USER_KEY);
	}

}
