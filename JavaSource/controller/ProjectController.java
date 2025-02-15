package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import model.Employee;
import model.ProEmp;
import model.Project;
import model.WorkPackage;
import validator.ProjectValidator;

@Named("projectController")
@ViewScoped
public class ProjectController implements Serializable {

	public enum Status {

		OPEN("Open"), ARCHIVED("Archived");

		private String label;

		private Status(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

	}

	public enum StatusCreation {

		OPEN("Open");

		private String label;

		private StatusCreation(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

	}

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
		authenticate();
		employeeList = getAssignedEmployeeList();

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

		// manager will assistant for now

		if (ProjectValidator.isValid(addProject) && !addProject.getStartDate().before(new Date())) {
			database.persistProject(addProject);
			FacesMessage msg = new FacesMessage("Project #" + addProject.getProNo() + " Added");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			addProject = new Project();
			projects = database.getAllProjects();
			PrimeFaces.current().executeScript("PF('addProjectDialog').hide();");
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Message:", "Invalid Project");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void onRowEdit(RowEditEvent event) {
		editProject = (Project) event.getObject();

		if (ProjectValidator.isValid(editProject)) {
			this.database.updateProject(editProject);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message:",
					"Project #" + editProject.getProNo() + " Edited");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Message:", "Failed To Edit Project");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		projects = database.getAllProjects();

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message:", "Cancel Edit");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		projects = database.getAllProjects();
	}

	public void deleteProject(Project project) throws IOException {
		if(this.database.deleteProjectByProNo(project.getProNo())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message:",
					"Project " + project.getProNo() + " Deleted");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		projects = this.database.getAllProjects();
	}

	private static Employee getLoggedInEmployee() {

		return (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(LoginController.USER_KEY);

	}

	public Employee getCurrentEmployee() {
		return currentEmployee;
	}

	public Status[] getStatuses() {
		return Status.values();
	}

	public StatusCreation[] getStatusCreation() {
		return StatusCreation.values();
	}

	public String getEmpName(int id) {
		return this.database.getEmployeeById(id).getUserName();

	}

	public void close() {
		addProject = new Project();
	}

	public boolean canDelete(Project pro) {
		return ProjectValidator.canDelete(database, pro);
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

	public boolean isProjectAssistant() {
		List<Project> allProject = this.database.getAllProjects();
		for (Project p : allProject) {
			if (currentEmployee.getEmpNumber() == p.getProAssiEmpNo()) {
				return true;
			}
		}
		return false;
	}

	public boolean isPMorPA() {
		return isProjectManager() || isProjectAssistant();
	}

	public boolean isREEmp() {
		for (WorkPackage wp : this.database.getAllWp()) {
			if (currentEmployee.getEmpNumber() == wp.getReEmpNo()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAssignedProjectEmployee() {
		List<ProEmp> pme = this.database.getAllProEmp();
		for(ProEmp pe : pme){
			if(pe.getProEmp().getEmpNo() == currentEmployee.getEmpNumber()) {
				return true;
			}
		}
		return false;
	}

	public void authenticate() {
		if (!isSupervisor()) {
			if (isPMorPA() || isAssignedProjectEmployee()) {
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				try {
					response.sendRedirect("ProjectManagement.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (isREEmp()) {
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				try {
					response.sendRedirect("WorkPackage.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				try {
					response.sendRedirect("Dashboard.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}