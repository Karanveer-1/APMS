package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import model.Employee;
import model.Project;

@Named("projectController")
@ViewScoped
public class ProjectController implements Serializable {

	@Inject
	private DatabaseController database;

	private List<Project> projects;

	private Project editProject;

	private Project addProject;

	private List<Employee> employeeList;

	private Employee currentEmployee;

	public Project getAddProject() {
		return addProject;
	}

	public void setAddProject(Project addProject) {
		this.addProject = addProject;
	}

	public ProjectController() {

	}

	@PostConstruct
	public void init() {

		projects = database.getAllProjects();
		addProject = new Project();
		employeeList = database.getActiveEmployees();
		editProject = new Project();
		currentEmployee = getLoggedInEmployee();
//		projects = 	database.getProjectsBySupervisor(currentEmployee.getEmpNumber());

	}

	/**
	 * Returns of list of employee that is supervised under current user
	 * 
	 * @return
	 */
	public List<Employee> getAssignedEmployeeList() {
		List<Employee> el = new ArrayList<Employee>();
		for (Employee e : database.getActiveEmployees()) {
			if (e.getSuperEmpNo() == currentEmployee.getEmpNumber()) {
				el.add(e);
			}
		}
		return el;
	}

	/**
	 * Get All the Current projects
	 * 
	 * @return
	 */
	public List<Project> getProjects() {

		return projects;
	}

	public void setProjects(List<Project> projects) {

		this.projects = projects;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public void persistProject() throws IOException {

		List<Project> allP = database.getAllProjects();
		int proLength = allP.size();
		if (proLength != 0) {
			int lastId = allP.get(proLength - 1).getProNo();
			addProject = new Project();
			this.addProject.setProNo(lastId + 1);
			System.out.println(this.addProject);

		} else {
			addProject = new Project();
			this.addProject.setProNo(100);
		}
		boolean addSuccess = database.persistProject(addProject);
		projects = database.getAllProjects();
		System.out.println("Add success is " + addSuccess);
		if (addSuccess) {
			FacesMessage msg = new FacesMessage("New Project Added");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} else {
			FacesMessage msg = new FacesMessage("Failed To Add New Project");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void onRowEdit(RowEditEvent event) {
		editProject = (Project) event.getObject();
		boolean updateSuccess = this.database.updateProject(editProject);

		projects = database.getAllProjects();
		if (updateSuccess) {
			FacesMessage msg = new FacesMessage("Project #" + editProject.getProNo() + " Edited");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("Failed To Edit Project");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void deleteProject(Project project) throws IOException {
		this.database.deleteProjectByProNo(project.getProNo());
		projects = this.database.getAllProjects();
	}

	private static Employee getLoggedInEmployee() {
		return (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(LoginController.USER_KEY);
	}

	public Employee getCurrentEmployee() {
		return currentEmployee;
	}


}