package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import model.Employee;
import model.PLevel;
import model.WorkPackage;
import validator.ProjectValidator;
import validator.WorkPackageValidator;

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

	private List<Employee> availEmp;

	private List<Employee> empList;

	private WorkPackage editwp;

	private List<PLevel> pLevels;

	private Employee assignEmp = new Employee();

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
		this.empList = this.database.getEmpListByWP(wp);
		this.availEmp = this.getAvaliable();
		this.pLevels = this.database.getPLevels();
		return "WorkPackageDetail.xhtml?faces-redirect=true";
	}

	public WorkPackage getWp() {
		return wp;
	}

	public void setWp(WorkPackage wp) {
		this.wp = wp;
	}

	public List<Employee> getAvaliable() {
		List<Employee> result = new ArrayList<Employee>();
		for (Employee emp : empPool) {
			if (empList.indexOf(emp) == -1) {
				result.add(emp);
			}
		}
		return result;
	}

	public void save() {

		if (this.editwp.isCharged()) {
			this.editwp.setEditable(false);
			updateParentWP(editwp, this.database.getParentWP(editwp));
			this.database.deleteWorkPackage(wp);
			this.database.persistWP(editwp);
		}
		System.out.println("Is this true" + this.database.updateWP(editwp));
		if (this.database.updateWP(editwp)) {
			FacesMessage msg = new FacesMessage("Work Package Updated");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			editable = false;
			wp = editwp;
		}

	}

	public void updateParentWP(WorkPackage wp, WorkPackage parent) {
		if (parent != null) {
			parent.addHoursFromChild(wp);
			this.database.updateWP(parent);
			updateParentWP(wp, this.database.getParentWP(parent));

		}

	}

	public float getBudgetByPLevel(int hr, String pLevel) {

		List<PLevel> result = this.database.getPLevelByLevel(pLevel);
		if (result.size() == 0) {
			return 0;
		}
		PLevel closest = result.get(0);
		for (PLevel pl : result) {
			Date current = pl.getpLevelPK().getStartDate();
			if (current.before(wp.getStartDate()) && current.after(closest.getpLevelPK().getStartDate())) {
				closest = pl;
			}
		}
		return closest.getWage() * hr;
	}

	public void toggleEditable() {
		editwp = wp;
		this.editable = !this.editable;

	}

	public boolean isEditable() {

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

	public Employee getEmployeeByNo(int id) {
		return this.database.getEmployeeById(id);
	}

	public List<Employee> getAvailEmp() {
		return availEmp;
	}

	public void setAvailEmp(List<Employee> availEmp) {
		this.availEmp = availEmp;
	}

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	public Employee getAssignEmp() {
		return assignEmp;
	}

	public void setAssignEmp(Employee assignEmp) {
		this.assignEmp = assignEmp;
	}

	public void assign() {
		this.assignEmp = this.database.getEmployeeById(this.assignEmp.getEmpNumber());
		this.database.addEmpToWp(assignEmp, this.wp);
		this.assignEmp = new Employee();

		this.empList = this.database.getEmpListByWP(wp);
		this.availEmp = this.getAvaliable();
		PrimeFaces.current().executeScript("PF('addEmpDialog').hide();");

	}

	public void deleteEmp(Employee emp) {
		if (this.database.deleteEmpFromWp(emp, wp)) {
			FacesMessage msg = new FacesMessage("Employee Removed");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			this.empList = this.database.getEmpListByWP(wp);
			this.availEmp = this.getAvaliable();
		}
	}

	public boolean canDeleteEmp() {
		return WorkPackageValidator.canDelete(database, this.wp)
				&& ProjectValidator.canDelete(database, this.database.findByProjectNo(this.wp.getProNo()));
	}

	public float getTotalBudget() {
		float result = 0;
		result += getBudgetByPLevel(wp.getPmEstP1(), "P1");
		result += getBudgetByPLevel(wp.getPmEstP2(), "P2");
		result += getBudgetByPLevel(wp.getPmEstP3(), "P3");
		result += getBudgetByPLevel(wp.getPmEstP4(), "P4");
		result += getBudgetByPLevel(wp.getPmEstP5(), "P5");
		result += getBudgetByPLevel(wp.getPmEstP6(), "P6");
		result += getBudgetByPLevel(wp.getPmEstSS(), "SS");
		result += getBudgetByPLevel(wp.getPmEstDS(), "DS");
		result += getBudgetByPLevel(wp.getPmEstJS(), "JS");

		result += getBudgetByPLevel(wp.getReEstP1(), "P1");
		result += getBudgetByPLevel(wp.getReEstP2(), "P2");
		result += getBudgetByPLevel(wp.getReEstP3(), "P3");
		result += getBudgetByPLevel(wp.getReEstP4(), "P4");
		result += getBudgetByPLevel(wp.getReEstP5(), "P5");
		result += getBudgetByPLevel(wp.getReEstP6(), "P6");
		result += getBudgetByPLevel(wp.getReEstSS(), "SS");
		result += getBudgetByPLevel(wp.getReEstDS(), "DS");
		result += getBudgetByPLevel(wp.getReEstJS(), "JS");

		return result;
	}

}
