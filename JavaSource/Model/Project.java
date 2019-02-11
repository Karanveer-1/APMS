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

	public static final String[] STATE = { "Open", "Closed", "Archived" };

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
	 * Project current state
	 */
	@Column(name = "State", nullable = false)
	private int state;

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

	@Column(name = "Comment")
	private String comment;

	public Project() {
		this.state = 0;
	}

	public Project(int projectId, Employee projectManager, int state, ArrayList<WorkPackage> workPackages,
			Date startDate, Date endDate) {
		super();
		this.projectID = projectId;
		this.projectManager = projectManager;
		this.state = state;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Return state with an argument
	 * 
	 * @param state
	 * @return String indicates the state of the projet
	 */
	public String getState(int state) {
		return STATE[state];
	}

}