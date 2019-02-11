package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Embeddable
@Table(name = "Project")
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Project status
	 * 
	 * @author mike
	 *
	 */
	enum Status {
		OPEN, CLOSED, ARCHIVED;
	}

	/**
	 * Project ID as Primary Key
	 */
	@GeneratedValue
	@Id
	@Column(name = "ProjectID", nullable = false)
	private int projectID;

	/**
	 * Project Name
	 */
	@Column(name = "ProjectName", nullable = false)
	private String name;

	/**
	 * Project Description
	 */
	@Column(name = "ProjectDescription", nullable = false)
	private String description;

	/**
	 * Project manager, must be unique
	 */
	@OneToOne
	@Column(name = "ProjectManager", nullable = false)
	private Employee projectManager;

	/**
	 * Project current status
	 */
	@Column(name = "Status", nullable = false)
	private int status;

	/**
	 * List of work packages in a project
	 */
	@OneToMany
	@Column(name = "WorkPackages", nullable = false)
	private ArrayList<WorkPackage> workPackages;

	/**
	 * Project start date
	 */
	@Column(name = "StartDate", nullable = false)
	private Date startDate;

	/**
	 * Project end date
	 */
	@Column(name = "endDate", nullable = false)
	private Date endDate;

	public Project() {

	}

	public Project(int projectId, Employee projectManager, int status, ArrayList<WorkPackage> workPackages,
			Date startDate, Date endDate) {
		super();
		this.projectID = projectId;
		this.projectManager = projectManager;
		this.status = status;
		this.workPackages = workPackages;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getProjectId() {
		return projectID;
	}

	public void setProjectId(int projectId) {
		this.projectID = projectId;
	}

	public Employee getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(Employee projectManager) {
		this.projectManager = projectManager;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ArrayList<WorkPackage> getWorkPackages() {
		return workPackages;
	}

	public void setWorkPackages(ArrayList<WorkPackage> workPackages) {
		this.workPackages = workPackages;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}