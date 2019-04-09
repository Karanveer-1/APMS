package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import model.Employee;
import model.Project;
import model.WorkPackage;

@Named("wpmController")
@ViewScoped
public class WorkPackageManagementController implements Serializable {

	@Inject
	private DatabaseController database;

	private Employee currentEmployeee;

	private List<WorkPackage> wpList;

	private WorkPackage editWP;
	
	private WorkPackage viewWp;


	public WorkPackageManagementController() {

	}

	@PostConstruct
	public void init() {
		currentEmployeee = getLoggedInEmployee();
		wpList = this.database.getWPByEmpNo(currentEmployeee.getEmpNumber());
		editWP = new WorkPackage();
		viewWp = new WorkPackage();
	}

	

	private static Employee getLoggedInEmployee() {
		return (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(LoginController.USER_KEY);
	}

	public List<WorkPackage> getWpList() {
		return wpList;
	}

	public void setWpList(List<WorkPackage> wpList) {
		this.wpList = wpList;
	}

	public WorkPackage getEditWP() {
		return editWP;
	}

	public void setEditWP(WorkPackage editWP) {
		this.editWP = editWP;
	}

	public WorkPackage getViewWp() {
		return viewWp;
	}

	public void setViewWp(WorkPackage viewWp) {
		this.viewWp = viewWp;
	}
	
	public void onClick(WorkPackage wp) {
		
		this.viewWp = wp;
		
	}


}
