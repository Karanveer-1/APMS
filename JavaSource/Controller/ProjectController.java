package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
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
		employeeList = database.getEmployees();
		editProject = new Project();
	}

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

	public void addProject() {
		List<Project> allP = database.getAllProjects();
		int proLength = allP.size();
		int lastId = allP.get(proLength - 1).getProNo();
		addProject = new Project();
		this.addProject.setProNo(lastId + 1);

		boolean addSuccess = database.persistProject(addProject);
		if (addSuccess) {
			System.out.println(projects);
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

	public void deleteProject(Project project) {
		boolean deleteSucess = this.database.deleteProjectByProNo(project.getProNo());
		if (deleteSucess) {
			FacesMessage msg = new FacesMessage("Project #" + project.getProNo() + " Deleted");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

}