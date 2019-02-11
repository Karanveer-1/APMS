package Model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class WorkPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Composite key
	 */
	@Id
	@Column(name = "WPID", nullable = false)
	private String WPID;

	/**
	 * Composite key with project
	 */
	@EmbeddedId
	private Project project;

	/**
	 * Responsible Engineer who is in charge of the work package
	 */
	@OneToOne
	private Employee WPManager;

	/**
	 * Contains a list of lower level work packages
	 */
	@OneToMany
	private ArrayList<WorkPackage> WPList;

	/**
	 * Total days of works
	 */
	private int budget;

	
	public WorkPackage() {

	}

	public WorkPackage(String wPID, Project project, Employee wPManager, ArrayList<WorkPackage> wPList, int budget) {
		super();
		WPID = wPID;
		this.project = project;
		WPManager = wPManager;
		WPList = wPList;
		this.budget = budget;
	}

	public String getWPID() {
		return WPID;
	}

	public void setWPID(String wPID) {
		WPID = wPID;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Employee getWPManager() {
		return WPManager;
	}

	public void setWPManager(Employee wPManager) {
		WPManager = wPManager;
	}

	public ArrayList<WorkPackage> getWPList() {
		return WPList;
	}

	public void setWPList(ArrayList<WorkPackage> wPList) {
		WPList = wPList;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

}
