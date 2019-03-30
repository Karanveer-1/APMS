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

	public WorkPackageManagementController() {

	}

	@PostConstruct
	public void init() {
		currentEmployeee = getLoggedInEmployee();
		wpList = this.database.getWPByEmpNo(currentEmployeee.getEmpNumber());
		editWP = new WorkPackage();
		System.out.println(wpList);
	}

	public void onRowEdit(RowEditEvent event) {
		editWP = (WorkPackage) event.getObject();
		boolean updateSuccess = this.database.updateWP(editWP);
		if (updateSuccess) {
			FacesMessage msg = new FacesMessage(
					"WorkPackage #" + editWP.getWpid() + " - (P " + editWP.getProNo() + ")" + " Edited");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("Failed To Edit Project");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
	public String print() {
		return "Total Length" + wpList.size();
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
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

}
