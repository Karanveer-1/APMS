package controller;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Project;

@Named("pdController")
@SessionScoped
public class ProjectDetailController implements Serializable {

	@Inject
	private DatabaseController database;

	private Project project;

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

		return "ProjectDetail.xhtml?faces-redirect=true";

	}

}
