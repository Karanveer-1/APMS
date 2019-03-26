package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import model.Employee;
import model.Project;
import model.WorkPackage;

@Named("vpController")

public class ViewProjectController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4162724450338685996L;

	@Inject
	private DatabaseController database;

	private Project project;

	private List<WorkPackage> wpList;

	private List<Employee> proAssi;

	public ViewProjectController() {

	}

	@PostConstruct
	public void init() {

		project = database.findByProjectNo(100);
		wpList = database.getAllWp();

//		proAssi = this.database.getEmployeeByProAssi(100);
		System.out.println(proAssi.get(0).getEmpNumber());

	}

	public List<WorkPackage> getWpList() {
		System.out.println(wpList);
		return wpList;
	}

	public void setWpList(List<WorkPackage> wpList) {
		this.wpList = wpList;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Employee> getProAssi() {
		return proAssi;
	}

	public void setProAssi(List<Employee> proAssi) {
		this.proAssi = proAssi;
	}
	


}
