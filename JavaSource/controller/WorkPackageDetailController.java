package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Employee;
import model.WorkPackage;

@Named("wpdController")
@SessionScoped
public class WorkPackageDetailController implements Serializable {

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

	@Inject
	private DatabaseController database;

	private WorkPackage wp;

	private WorkPackage ogwp;

	private boolean editable;

	private boolean wpEditable;

	private List<Employee> empPool;

	private WorkPackage editwp;

	public WorkPackageDetailController() {

	}

	@PostConstruct
	public void init() {

	}

	public String viewWP(WorkPackage wp) {
		this.wp = wp;
		this.ogwp = wp;
		this.editwp = wp;
		this.editable = false;
		this.wpEditable = editable && this.wp.isEditable() && this.wp.isLeaf();
		this.empPool = getAllEmpPool(this.wp.getProNo());
		return "WorkPackageDetail.xhtml?faces-redirect=true";
	}

	public WorkPackage getWp() {
		return wp;
	}

	public void setWp(WorkPackage wp) {
		this.wp = wp;
	}

	public void save() {
		System.out.println("Im edititng this" + this.editwp);
		if (this.editwp.isCharged()) {
			this.editwp.setEditable(false);
			updateParentWP(editwp, this.database.getParentWP(editwp));
		}
		if (this.database.updateWP(editwp)) {
			FacesMessage msg = new FacesMessage("Work Package Updated");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("Invalid Input");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

//		if (this.database.updateWP(wp)) {
//			wp = this.database.getWPByID(wp.getWorkPackagePk());
//			ogwp = wp;
//		} else {
//			wp = ogwp;
//		}

	}

	public void updateParentWP(WorkPackage wp, WorkPackage parent) {
		if (parent != null) {
			parent.addHoursFromChild(wp);
			this.database.updateWP(parent);
			updateParentWP(wp, this.database.getParentWP(parent));

		}

	}

	public void toggleEditable() {
		editwp = wp;
		this.editable = !this.editable;
		System.out.println("Edit toggle");
	}

	public boolean isEditable() {
		editable = this.wp.getState().equals(Status.OPEN);
		return editable;
	}

	public void setEditable(boolean editable) {

		this.editable = editable;
	}

	public boolean isWpEditable() {
		this.wpEditable = editable && this.wp.isEditable() && this.wp.isLeaf();
		return wpEditable;
	}

	public void setWpEditable(boolean wpEditable) {
		this.wpEditable = wpEditable;
	}

	public List<Employee> getAllEmpPool(int proNo) {
		List<Employee> pool = this.database.getEmployeeForProject(proNo);
		return pool;
	}

	public List<Employee> getEmpPool() {
		return empPool;
	}

	public void setEmpPool(List<Employee> empPool) {
		this.empPool = empPool;
	}

	public WorkPackage getEditwp() {
		return editwp;
	}

	public void setEditwp(WorkPackage editwp) {
		this.editwp = editwp;
	}

	public Status[] getStatus() {
		return Status.values();
	}

}
