package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.ProAssi;
import model.Project;

@Named("pdController")
@SessionScoped
public class ProjectDetailController implements Serializable {

	@Inject
	private DatabaseController database;

	private Project project;

	private List<Employee> proAssi;
	
	private List<Employee> empPool;

	public ProjectDetailController() {

	}

	@PostConstruct
	public void init() {

	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String detail(Project project) {
		
		this.project = project;
		this.proAssi = getAssistantManager();

		return "ProjectDetail.xhtml?faces-redirect=true";

	}

	public List<Employee> getAssistantManager() {
		List<Employee> result = new ArrayList<Employee>();
		for (ProAssi pe : this.database.getProAssiByProNo(this.project.getProNo())) {
			result.add(this.database.getEmployeeById(pe.getEmpNo()));
		}
		return result;
	}

	public List<Employee> getProAssi() {
		return proAssi;
	}

	public void setProAssi(List<Employee> proAssi) {
		this.proAssi = proAssi;
	}
	

}
