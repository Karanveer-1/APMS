package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import controller.ProjectController.Status;
import model.Employee;
import model.Project;
import model.WorkPackage;

@Named("pmController")
@SessionScoped
public class ProjectMangementController implements Serializable {
	
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4162724450338685996L;
	
	@Inject
	private DatabaseController database;

	private List<Project> projects;

	private Project editProject;

	private Employee currentEmployee;

	public ProjectMangementController() {

	}

	@PostConstruct
	public void init() {
		currentEmployee = getLoggedInEmployee();
		projects = getProjectByManagerOrAssi();

	}

	public List<Project> getProjectByManagerOrAssi() {
		int id = currentEmployee.getEmpNumber();
		List<Project> result = this.database.getProjectsByManagerNo(id);
//		List<Project> assistantP = this.database.getProjectsByAssistantNo(id);
//		for (Project p : assistantP) {
//			if (result.indexOf(p) == -1) {
//				result.add(p);
//			}
//		}
		return result;
	}

	private static Employee getLoggedInEmployee() {
		return (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(LoginController.USER_KEY);
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

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project getEditProject() {
		return editProject;
	}

	public void setEditProject(Project editProject) {
		this.editProject = editProject;
	}
	
	public Status[] getStatuses() {
		return Status.values();
	}


}
